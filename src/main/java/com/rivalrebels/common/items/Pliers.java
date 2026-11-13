package com.rivalrebels.common.items;

import java.util.HashMap;
import java.util.UUID;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.blocks.autobuild.BlockAutoBuild;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.packet.PacketDispatcher;
import com.rivalrebels.common.packet.TextPacket;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Pliers extends RRItem{
    private static final HashMap<UUID, WorkSession> activeWorkSessions = new HashMap<UUID, WorkSession>();

    private static class WorkSession {
        int x, y, z;
        int progress;

        WorkSession(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.progress = 0;
        }
        WorkSession(BlockPos pos) {
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            this.progress = 0;
        }
    }


    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        player.swingArm(hand);
        
        if (!world.isRemote) {
            // Handle Hot Potato setup (instant action)
            /* 
            if (world.getBlock(x, y, z) == RivalRebels.jump && player.capabilities.isCreativeMode) {
                CommandHotPotato.x = x;
                CommandHotPotato.y = y + 400;
                CommandHotPotato.z = z;
                CommandHotPotato.world = world;
                PacketDispatcher.packetsys.sendTo(new TextPacket("Hot Potato drop point set. Use /rrhotpotato to start a round."), (EntityPlayerMP)player);
                return false;
            }
            */

            UUID playerId = player.getUniqueID();
            WorkSession session = activeWorkSessions.get(playerId);

            // Check if the player is continuing on the exact same block
            if (session != null && session.x == pos.getX() && session.y == pos.getY() && session.z == pos.getZ()) {
                session.progress++;
            } else {
                // Switched blocks or started fresh, create a new session
                session = new WorkSession(pos);
                activeWorkSessions.put(playerId, session);
            }

            Block blockType = world.getBlockState(pos).getBlock();
            int clicks_required = 25; // Default for bombs

            if (blockType instanceof BlockAutoBuild) {
                clicks_required = ((BlockAutoBuild) blockType).clicks;
            } else if (blockType == RRBlocks.supply_crate && world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getBlock() == RRBlocks.supply_crate) {
                clicks_required = 15;
            }

            // Handle block actions & progress feedback
            if (blockType == RRBlocks.plastic_explosives) {
                //PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Defuse " + (session.progress * 100 / clicks_required) + "§7'/."), (EntityPlayerMP)player);
                if (session.progress >= clicks_required) {
                EntityItem defused_bomb_entity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(RRBlocks.plastic_explosives, 1));
                world.spawnEntity(defused_bomb_entity);
                world.setBlockToAir(pos);
                activeWorkSessions.remove(playerId); // Clean up session on completion
                return EnumActionResult.SUCCESS;
                }
            } 
            else if (blockType == RRBlocks.timed_bomb) {
                //PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Defuse " + (session.progress * 100 / clicks_required) + "§7'/."), (EntityPlayerMP)player);
                if (session.progress >= clicks_required) {
                    EntityItem defused_bomb_entity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(RRBlocks.timed_bomb, 1));
                    world.spawnEntity(defused_bomb_entity);
                    world.setBlockToAir(pos);
                    //world.setBlock(x, y + 1, z, Blocks.air); this is to remove the tick, which won't be in my port
                    activeWorkSessions.remove(playerId);
                    return EnumActionResult.SUCCESS;
                }
            } 
            else if (blockType instanceof BlockAutoBuild) {
                //PacketDispatcher.packetsys.sendTo(
                //    new TextPacket("RivalRebels.Status RivalRebels.building " + (session.progress * 100 / clicks_required) + "§7'/."), (EntityPlayerMP)player
                //);
                if (session.progress >= clicks_required) {
                    world.setBlockToAir(pos);
                    ((BlockAutoBuild) blockType).build(world, pos);
                    activeWorkSessions.remove(playerId);
                    return EnumActionResult.SUCCESS;
                }
            } 
            else if (blockType == RRBlocks.supply_crate && world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getBlock() == RRBlocks.supply_crate) {
                //PacketDispatcher.packetsys.sendTo(
                //new TextPacket("RivalRebels.Status RivalRebels.building ToKaMaK " + (session.progress * 100 / clicks_required) + "§7'/."), (EntityPlayerMP)player
                //);
                if (session.progress >= clicks_required) {
                    world.setBlockToAir(pos);
                    BlockPos below = new BlockPos(pos.getX(), pos.getY()-1, pos.getZ());
                    world.setBlockState(below, RRBlocks.reactor.getDefaultState());
                    activeWorkSessions.remove(playerId);
                    return EnumActionResult.SUCCESS;
                }
            } 
            else {
                // Clicked an invalid block, clear session
                // maybe decide to be less harsh later
                activeWorkSessions.remove(playerId);
                return EnumActionResult.PASS;
            }
        }

        return EnumActionResult.PASS;
    }




    public Pliers(String name) {
        super(name);
        setContainerItem(this);
        setMaxStackSize(1);
    }
}
