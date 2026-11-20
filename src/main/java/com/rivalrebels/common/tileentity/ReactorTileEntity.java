package com.rivalrebels.common.tileentity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.blocks.RRBlock;
import com.rivalrebels.common.explosion.RivalRebelsExplosion;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.init.RivalRebelsDamageSource;
import com.rivalrebels.common.items.ItemRodNuclear;
import com.rivalrebels.common.items.RRItemCore;
import com.rivalrebels.common.items.RRItemRedstoneRod;
import com.rivalrebels.common.items.RRItemRod;
import com.rivalrebels.common.packet.PacketDispatcher;
import com.rivalrebels.common.packet.TextPacket;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class ReactorTileEntity extends TileEntity implements IInventory, ITickable {

    public ItemStack fuel = ItemStack.EMPTY;
    public ItemStack core = ItemStack.EMPTY;

    // Laptop animation variables
    public double laptop_lid_slide = 90.0;
    private double laptop_lid_phase = Math.PI;

    // Sound timer
    private int sound_counter = 0;

    // no idea what these are for
    public TileEntityList machines = new TileEntityList();
    private boolean prevOn = false;

    public boolean on = false; // Whether the reactor is switched on
    private int melt_tick = -1; // How long until a melting down reactor completely destroys itself, -1 means not melting down

    private int overrun_timer = -1; // Idle running counter, if it finishes with not redstone rod, start melting down
    private boolean last_rod_was_redstone = false;
    private boolean eject = false; // Has a ejection been ordered

    private boolean reactive_scan = false; // Has the reactor done it's initial scan for reactive blocks to power
    private static final int MAX_SCAN_BLOCKS_PER_TICK = 2000;

    public float current_tick_consumption = 0;
    public float core_power_left;

    public void toggleOn() {
        if (this.core != null && !this.core.isEmpty()
                && this.fuel != null && !this.fuel.isEmpty()) {
            this.on = !this.on;
            this.overrun_timer = -1;
            this.markDirty();
            IBlockState state = this.world.getBlockState(this.getPos());
            this.world.notifyBlockUpdate(this.getPos(), state, state, 3);
        }
    }

    public void ejectCore() {
        this.eject = true;
        this.markDirty();
        IBlockState state = this.world.getBlockState(this.getPos());
        this.world.notifyBlockUpdate(this.getPos(), state, state, 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.on = compound.getBoolean("on");
        this.eject = compound.getBoolean("eject");
        this.fuel = compound.hasKey("fuel")
            ? new ItemStack(compound.getCompoundTag("fuel"))
            : ItemStack.EMPTY;
        this.core = compound.hasKey("core")
            ? new ItemStack(compound.getCompoundTag("core"))
            : ItemStack.EMPTY;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("on", this.on);
        compound.setBoolean("eject", this.eject);
        if (this.fuel != null && !this.fuel.isEmpty()) {
            compound.setTag("fuel", this.fuel.writeToNBT(new NBTTagCompound()));
        }
        if (this.core != null && !this.core.isEmpty()) {
            compound.setTag("core", this.core.writeToNBT(new NBTTagCompound()));
        }
        return compound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }


    private class BlockScan {

        private int index = 0;

        boolean scan(World world, BlockPos reactorPos) {

            int scanCount = 0;

            while (index < 65 * 65 * 65
                    && scanCount < MAX_SCAN_BLOCKS_PER_TICK) {

                int current = index++;

                int x = current / (65 * 65) - 32;
                int y = (current / 65) % 65 - 32;
                int z = current % 65 - 32;

                if (x*x + y*y + z*z > 1024) {
                    continue;
                }

                BlockPos pos = reactorPos.add(x, y, z);

                Block block = world.getBlockState(pos).getBlock();

                if (block == RRBlocks.reactive) {
                    //reactive_cooldowns.put(pos, new BlockCooldown());
                }

                scanCount++;
            }

            return index >= 65 * 65 * 65;
        }
    }

    // Power management of reactives because in the port reactives are no longer tile entities:
    private static class BlockTracker {
        // Activation tick
        int cooldown;
        ReactorTileEntity owner;

        BlockTracker() {
            cooldown = 0;
        }
    }

    // Cooldown trackers for reactives so they aren't powered all the time
    private BlockScan running_scan = new BlockScan();
    //private static HashMap<BlockPos, BlockCooldown> reactive_cooldowns; //Global
    private static int f;
    

    @Override
    public void update() {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        // Handle the variables for laptop model rendering
        if (side == Side.CLIENT) {
            this.laptop_lid_slide = (Math.cos(this.laptop_lid_phase) + 1.0) * 45.0;
            List<EntityPlayer> players = this.world.playerEntities;
            Iterator<EntityPlayer> iter = players.iterator();
            boolean player_close_flag = false;

            while (iter.hasNext()) {
                EntityPlayer player = (EntityPlayer)iter.next();
                if (player.getDistanceSq(this.getPos().getX() + 0.5F, this.getPos().getY() + 0.5F, this.getPos().getZ() + 0.5F) <= 9.0 && !player.isInvisible()) {
                    player_close_flag = true;
                    break;
                }
            }

            if (player_close_flag) {
                if (this.laptop_lid_slide < 89.995) {
                    this.laptop_lid_phase += 0.05;
                }
            } else if (this.laptop_lid_slide > 0.004) {
                this.laptop_lid_phase -= 0.05;
            }
        }

        this.current_tick_consumption = 0.0F;

        // If there is no core, turn the reactor off
        if (this.core.isEmpty()) {
            this.on = false;
            this.overrun_timer = -1;
            this.melt_tick = -1;
        }

        // If a jettson eject has been ordered
        this.handle_ejection();

        this.handle_meltdown_state();

        this.prevOn = this.on;

        this.handle_no_fuel();

        this.handle_power();

        this.eject = false;
        this.prevOn = this.on;
        /* 
        if (System.currentTimeMillis() - this.lastPacket > 1000L) {
            this.lastPacket = System.currentTimeMillis();
            PacketDispatcher.packetsys // this is needed to repair all machines
            .sendToAll(
                new ReactorUpdatePacket(
                    this.xCoord,
                    this.yCoord,
                    this.zCoord,
                    (float)this.consumed,
                    (float)this.lasttickconsumed,
                    this.melt,
                    this.eject,
                    this.on,
                    this.machines
                )
            );
        }
        */
    }

    private void set_begin_meltdown() { // Starts a meltdown if not already melting down
        if (this.melt_tick == -1) {
            this.melt_tick = 300;
        }
    }


    public boolean is_melting_down() {
        return this.melt_tick != -1;
    }

    private void handle_no_fuel() {
        if (this.fuel.isEmpty() || this.fuel == null) {

            if (this.overrun_timer == -1) {
                this.overrun_timer = 100;
            } else if (overrun_timer != 0) {
                this.overrun_timer--;
            }

            if (this.overrun_timer == 0) {
                if (this.last_rod_was_redstone) {
                    // Safe shutdown
                    this.on = false;
                    this.overrun_timer = -1;
                } else {
                    this.set_begin_meltdown();
                    this.overrun_timer = -1;
                    
                }
            }
        } else {
            this.overrun_timer = -1; // Reset depletion to meltdown timer
        }
    }

    private void handle_meltdown_state() {


        if (this.melt_tick != -1) {

            this.machines.clear();



            if (!this.core.isEmpty()) {


                if (world.isRemote) {
                    for (int i = 0; i < 4; i++) {
                        this.world
                        .spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            this.getPos().getX() + 0.5,
                            this.getPos().getY() + 0.5,
                            this.getPos().getZ() + 0.5,
                            Math.random() - 0.5,
                            Math.random() / 2.0,
                            Math.random() - 0.5
                        );
                    }
                }


                if (this.melt_tick % 20 == 0) {
                    world.playSound(null, this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5, RRSounds.reactor_warning, SoundCategory.AMBIENT, 1f, 1);
                }

                

                this.on = true;
                this.melt_tick--;
                if (this.melt_tick == 0) {
                    this.meltDown(10);
                } else if (this.melt_tick == 299) {
                    //PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.meltdown"));
                }



            } else {
                this.melt_tick = -1;
                this.on = false;
            }
        }

    }

    private void handle_ejection() {
        if (this.eject && !this.core.isEmpty()) {
            this.overrun_timer = -1;
            this.world
            .spawnEntity(new EntityItem(this.world, this.getPos().getX() + 0.5, this.getPos().getY() + 1, this.getPos().getZ() + 0.5, this.core));
            this.fuel = null; // Destroy fuel
            this.core = null;
            this.melt_tick = -1;
            this.on = false;
        }
    }

    private void handle_power() {

        if (this.on && !this.core.isEmpty() && !this.fuel.isEmpty() && this.core.getItem() instanceof RRItemCore && this.fuel.getItem() instanceof RRItemRod) {
            if (!this.prevOn && this.on) {
                // Startup sound
                world.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), RRSounds.reactor_startup, SoundCategory.AMBIENT, 1f, 1);
            } else {
                // humming
                this.sound_counter++;
                if (this.on && this.sound_counter % 39 == 0) {
                    world.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), RRSounds.reactor_hum, SoundCategory.AMBIENT, 0.9f, 0.77f);
                    this.sound_counter = 0;
                }
            }

            RRItemCore core = (RRItemCore)this.core.getItem();
            RRItemRod running_rod = (RRItemRod)this.fuel.getItem();
            if (this.fuel.getTagCompound() == null) {
                this.fuel.setTagCompound(new NBTTagCompound());
            }


            float already_consumed = this.fuel.getTagCompound().getInteger("fuelConsumed");
            this.core_power_left = running_rod.get_max_power() - already_consumed;
            float available_power = core_power_left / core.DENOMINATOR;

            float available_power_start = available_power;



            //#### start of the power loop ####

            //reactive Hashmap building
            if (!reactive_scan) {
                reactive_scan = running_scan.scan(world, this.getPos());
            }

            // Power reactives:



            for (TileEntity tile_ent : this.world.loadedTileEntityList) {
                if (tile_ent != null && tile_ent instanceof IPowerable) {
                    IPowerable tile_powerable = (IPowerable)tile_ent;
                    if (this.world.getTileEntity(tile_ent.getPos()) == null) {
                        double dist = tile_ent.getDistanceSq(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
                        if (dist < 1024.0) {
                            // Assign this reactor to the powerable tile
                            tile_powerable.set_reactor_pos(tile_ent.getPos());
                            tile_powerable.set_reactor_distance((float)Math.sqrt(dist));

                            //this.machines.add(tile_powerable);
                        }
                    }

                    if (tile_powerable.get_reactor_pos() == this.getPos()) {

                        //this.machines.add(tile_powerable);
                        //tile_powerable.powerGiven = power; // used for rendering do later

                        int missing_power = tile_powerable.get_max_power_storage() - tile_powerable.get_power_contained();

                        if (available_power > missing_power) {
                            available_power -= missing_power;
                            tile_powerable.add_power(missing_power);
                        } else {
                            tile_powerable.add_power(available_power);
                            available_power = 0.0F;
                        }

                        //tile_powerable.powerGiven -= power;

                    }


                }
            }

            current_tick_consumption = available_power_start - available_power;

            float new_consumed = already_consumed + current_tick_consumption/core.EFFICIENCY;
            if (this.fuel.getTagCompound().hasKey("fuelConsumed")) {
                int save_value = (Math.round(new_consumed) == 1) ? 0 : Math.round(new_consumed);
                this.fuel.getTagCompound().setInteger("fuelConsumed", save_value);

                // Nuclear rod melting down logic
                double fuelPercentage = new_consumed / available_power_start;
                if (running_rod instanceof ItemRodNuclear) {
                    double f2 = fuelPercentage * fuelPercentage;
                    double f4 = f2 * f2;
                    double f8 = f4 * f4;
                    if (this.world.rand.nextFloat() < f8) {
                        this.set_begin_meltdown();
                    }
                }
            } else {
                this.fuel.getTagCompound().setInteger("fuelConsumed", 0);
            }

            if (this.fuel.getTagCompound().getInteger("fuelConsumed") >= available_power_start) {
                this.last_rod_was_redstone = running_rod instanceof RRItemRedstoneRod;
                this.overrun_timer = 300;
                this.fuel = null;
            }
        } else {
            this.machines.clear();
        }

    }


    // Blow the reactor up via meltdown
    public void meltDown(int radius) {
        BlockPos detonation_pos = new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        this.world.setBlockState(detonation_pos, RRBlocks.plasma_explosion.getDefaultState()); // replace with meltdown block later
        RivalRebelsDamageSource damage = new RivalRebelsDamageSource(RivalRebelsDamageSource.RR_damage_sources.PLASMA, null, null);
        new RivalRebelsExplosion(this.world, this.pos.getX(), this.pos.getY() - 2, this.pos.getZ(), 4, false, false, damage);
    }

    @Override
    public String getName() {
        return "Tokamak";
    }

    @Override
    public boolean hasCustomName() {
        return false; // maybe in the future write it to nbt or tile entity
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return (this.core == null || this.core.isEmpty())
                && (this.fuel == null || this.fuel.isEmpty());
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index == 0) {
            return this.fuel == null ? ItemStack.EMPTY : this.fuel;
        }
        return index == 1 && this.core != null ? this.core : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < 0 || index >= this.getSizeInventory() || count <= 0) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = index == 0 ? this.fuel : this.core;
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack removed = stack.splitStack(count);
        if (stack.isEmpty()) {
            if (index == 0) {
                this.fuel = ItemStack.EMPTY;
            } else {
                this.core = ItemStack.EMPTY;
            }
        }
        this.markDirty();
        return removed;
    }


    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index < 0 || index >= this.getSizeInventory()) {
            return ItemStack.EMPTY;
        }

        ItemStack removed = this.getStackInSlot(index);
        if (removed.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (index == 0) {
            this.fuel = ItemStack.EMPTY;
        } else {
            this.core = ItemStack.EMPTY;
        }
        this.markDirty();
        return removed;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= this.getSizeInventory()) {
            return;
        }

        ItemStack stored = stack == null ? ItemStack.EMPTY : stack;
        if (!stored.isEmpty() && stored.getCount() > this.getInventoryStackLimit()) {
            stored.setCount(this.getInventoryStackLimit());
        }

        if (index == 0) {
            this.fuel = stored;
        } else {
            this.core = stored;
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null || stack.isEmpty() || index < 0 || index >= this.getSizeInventory()) {
            return false;
        }

        if (index == 0) {
            return stack.getItem() instanceof RRItemRod
                    && (this.fuel == null || this.fuel.isEmpty() || !this.on);
        }
        return stack.getItem() instanceof RRItemCore && !this.on;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.fuel = ItemStack.EMPTY;
        this.core = ItemStack.EMPTY;
        this.markDirty();
    }
    
}
