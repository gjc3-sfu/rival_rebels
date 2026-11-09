package com.rivalrebels.common.entity;

import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityFlameBallHighTemp extends EntityFlameBall {
    public boolean	gonnadie;

    public EntityFlameBallHighTemp(World world)
    {
        super(world);
    }

    public EntityFlameBallHighTemp(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityFlameBallHighTemp(World world, EntityPlayer player, float speed)
    {
        super(world, player, speed);
    }

    public EntityFlameBallHighTemp(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();

        if (!hasLeftOwner && owner != null) {
            if (!getEntityBoundingBox().intersects(owner.getEntityBoundingBox())) {
                hasLeftOwner = true;
            }
        }

        ticksExisted++;
        sequence++;
        if (sequence > 15/* > RivalRebels.flamethrowerDecay */) setDead();
        if (ticksExisted > 5 && Math.random() > 0.5) gonnadie = true;

        if (gonnadie && sequence < 15) sequence++;

        Vec3d start = new Vec3d(posX, posY, posZ);
        Vec3d end = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
        RayTraceResult mop = world.rayTraceBlocks(start, end);
        start = new Vec3d(posX, posY, posZ);
        end = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

        if (mop != null) end = new Vec3d(mop.hitVec.x, mop.hitVec.y, mop.hitVec.z);

        Entity hitEntity = null;
        List<Entity> nearby_entities = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().contract(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double closest_hit_distance = 0.0D;
        Iterator<Entity> entity_iterator = nearby_entities.iterator();

        if (!world.isRemote)
        {
            while (entity_iterator.hasNext())
            {
                Entity current_entity = (Entity) entity_iterator.next();

                if (current_entity.canBeCollidedWith())
                {
                    float collision_border_size = 0.3F;
                    AxisAlignedBB expanded_bounding_box = current_entity.getEntityBoundingBox().expand(collision_border_size, collision_border_size, collision_border_size);
                    RayTraceResult entity_intercept = expanded_bounding_box.calculateIntercept(start, end);

                    if (entity_intercept != null)
                    {
                        double hit_distance = start.distanceTo(entity_intercept.hitVec);

                        if (hit_distance < closest_hit_distance || closest_hit_distance == 0.0D)
                        {
                            hitEntity = current_entity;
                            closest_hit_distance = hit_distance;
                        }
                    }
                }
            }
        }

        if (hitEntity != null)
        {
            mop = new RayTraceResult(hitEntity);
        }

        if (mop != null && ticksExisted >= 6)
        {
            posX -= 0.5f;
            posY -= 0.5f;
            posZ -= 0.5f;
            fire();
            posX++;
            fire();
            posX--;
            posX--;
            fire();
            posX++;
            posY++;
            fire();
            posY--;
            posY--;
            fire();
            posY++;
            posZ++;
            fire();
            posZ--;
            posZ--;
            fire();
            posZ++;
            posX += 0.5f;
            posY += 0.5f;
            posZ += 0.5f;
            setDead();
            if (mop.entityHit != null && (hasLeftOwner || mop.entityHit != owner) && !mop.entityHit.isImmuneToFire())
            {
                mop.entityHit.setFire(3);
                RivalRebelsDamageSource damage = new RivalRebelsDamageSource(RivalRebelsDamageSource.RR_damage_sources.INCINERATION, this, owner);
                mop.entityHit.attackEntityFrom(damage, 12);
                if (mop.entityHit != null && mop.entityHit instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) mop.entityHit;
                    ItemStack[] armorSlots = player.inventory.armorInventory.toArray(new ItemStack[0]);
                    int i = world.rand.nextInt(4);
                    if (armorSlots[i] != null && !world.isRemote)
                    {
                        armorSlots[i].damageItem(8, player);
                    }
                }
            }
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;

        rotation += motionr;
        motionr *= 1.06f;

        if (isInWater()) setDead();
        float airFriction = 0.9F;
        if (gonnadie)
        {
            motionY += 0.05;
            airFriction = 0.7f;
        }
        motionX *= airFriction;
        motionY *= airFriction;
        motionZ *= airFriction;
        setPosition(posX, posY, posZ);
    }

    private void fire() {
        if (!world.isRemote)
        {
            Block id = world.getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ)).getBlock();
            IBlockState state = world.getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ));
            // Instamelts:
            if (id == Blocks.AIR || id == Blocks.SNOW || id == Blocks.SNOW_LAYER || id == Blocks.ICE) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.LEAVES || id == Blocks.LEAVES2) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.GRASS || id == Blocks.MYCELIUM) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.DIRT.getDefaultState());
            else if (id == Blocks.DIRT) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.WOOL) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.WEB) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.TRIPWIRE) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.LAVA) world.setBlockToAir(new BlockPos((int) posX, (int) posY, (int) posZ));

            // Tissue
            else if (id == Blocks.RED_MUSHROOM_BLOCK && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.BROWN_MUSHROOM_BLOCK && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.SPONGE && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.MELON_BLOCK && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.PUMPKIN && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.LIT_PUMPKIN && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());

            // Wood
            else if (id == Blocks.CRAFTING_TABLE && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.NOTEBLOCK && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.JUKEBOX && world.rand.nextInt(20) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.BED && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.OAK_DOOR && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.SPRUCE_DOOR && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.BIRCH_DOOR && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.JUNGLE_DOOR && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.ACACIA_DOOR && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());
            else if (id == Blocks.DARK_OAK_DOOR && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.FIRE.getDefaultState());

            // Redstone components
            else if (id == Blocks.OBSERVER && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());

            // Sand and glass
            else if (id == Blocks.SAND && world.rand.nextInt(60) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.GLASS.getDefaultState());
            else if (id == Blocks.GLASS && world.rand.nextInt(120) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.OBSIDIAN.getDefaultState());
            else if (id == Blocks.STAINED_GLASS && world.rand.nextInt(120) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.OBSIDIAN.getDefaultState());

            else if (id == Blocks.OBSIDIAN && world.rand.nextInt(90) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.ENCHANTING_TABLE && world.rand.nextInt(180) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.ENDER_CHEST && world.rand.nextInt(180) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());

            // Stone
            else if ((id == Blocks.STONE || id == Blocks.COBBLESTONE || id == Blocks.MOSSY_COBBLESTONE) && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if ((id == Blocks.STONEBRICK) && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if ((id == Blocks.MONSTER_EGG) && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.STONE_STAIRS && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.STONE_BRICK_STAIRS && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.COBBLESTONE_WALL && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.STONE_SLAB && world.rand.nextInt(30) == 0) {
                if ((state.getValue(BlockStoneSlab.VARIANT) != BlockStoneSlab.EnumType.NETHERBRICK) && (state.getValue(BlockStoneSlab.VARIANT) != BlockStoneSlab.EnumType.QUARTZ)) {
                    world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
                }
            } else if (id == Blocks.STONE_SLAB2 && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.DOUBLE_STONE_SLAB && world.rand.nextInt(30) == 0) {
                if ((state.getValue(BlockStoneSlab.VARIANT) != BlockStoneSlab.EnumType.NETHERBRICK) && (state.getValue(BlockStoneSlab.VARIANT) != BlockStoneSlab.EnumType.QUARTZ)) {
                    world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
                }
            } else if (id == Blocks.DOUBLE_STONE_SLAB2 && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.IRON_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.REDSTONE_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.LIT_REDSTONE_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.COAL_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.GOLD_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.LAPIS_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.EMERALD_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.DIAMOND_ORE && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.BRICK_BLOCK && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.BRICK_STAIRS && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());

            //Glazed terracotta
            else if (id == Blocks.RED_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.ORANGE_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.YELLOW_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.LIME_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.GREEN_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.CYAN_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.BLUE_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.PURPLE_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.MAGENTA_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.PINK_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.BROWN_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.WHITE_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.SILVER_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.GRAY_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.BLACK_GLAZED_TERRACOTTA && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());


            else if (id == Blocks.GRAVEL && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if ((id == Blocks.SANDSTONE || id == Blocks.RED_SANDSTONE) && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if ((id == Blocks.SANDSTONE_STAIRS || id == Blocks.RED_SANDSTONE_STAIRS) && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.CLAY && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.STAINED_HARDENED_CLAY.getDefaultState());
            else if (id == Blocks.STAINED_HARDENED_CLAY && world.rand.nextInt(30) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.ANVIL && world.rand.nextInt(50) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.IRON_BLOCK && world.rand.nextInt(50) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.GOLD_BLOCK && world.rand.nextInt(50) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.LAVA.getDefaultState());
            else if (id == Blocks.BEDROCK && world.rand.nextInt(500) == 0) world.setBlockState(new BlockPos((int) posX, (int) posY, (int) posZ), Blocks.OBSIDIAN.getDefaultState());
            

            else if (id == RRBlocks.flare) RRBlocks.flare.onBlockDestroyedByPlayer(world, new BlockPos((int) posX, (int) posY, (int) posZ), RRBlocks.flare.getDefaultState());
            //else if (id == RivalRebels.timedbomb) RivalRebels.timedbomb.onBlockDestroyedByPlayer(worldObj, (int) posX, (int) posY, (int) posZ, 0);
            else if (id == RRBlocks.plastic_explosives) RRBlocks.plastic_explosives.onBlockDestroyedByPlayer(world, new BlockPos((int) posX, (int) posY, (int) posZ), RRBlocks.plastic_explosives.getDefaultState());
            else if (id == RRBlocks.mine) RRBlocks.plastic_explosives.onBlockDestroyedByPlayer(world, new BlockPos((int) posX, (int) posY, (int) posZ), RRBlocks.plastic_explosives.getDefaultState());
            else if (id == Blocks.TNT)
            {
                world.setBlockToAir(new BlockPos((int) posX, (int) posY, (int) posZ));
                world.createExplosion(null, posX, posY, posZ, 4, false);
            }
            /*else if (id == RivalRebels.conduit) worldObj.setBlock((int) posX, (int) posY, (int) posZ, Blocks.fire);
            else if (id == RivalRebels.reactive && worldObj.rand.nextInt(20) == 0) worldObj.setBlock((int) posX, (int) posY, (int) posZ, Blocks.lava);
            else if ((id == RivalRebels.camo1 || id == RivalRebels.camo2 || id == RivalRebels.camo3) && worldObj.rand.nextInt(40) == 0) worldObj.setBlock((int) posX, (int) posY, (int) posZ, RivalRebels.steel);
            else if (id == RivalRebels.steel && worldObj.rand.nextInt(40) == 0) worldObj.setBlock((int) posX, (int) posY, (int) posZ, Blocks.fire);*/
        }
    }
}
