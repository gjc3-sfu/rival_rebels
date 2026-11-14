package com.rivalrebels.common.entity;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.config.BlockTransformation;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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
        if (!world.isRemote) {
            Block block = world.getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ)).getBlock();
            IBlockState state = world.getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ));
            BlockPos burn_pos = new BlockPos(
                (int) posX,
                (int) posY,
                (int) posZ
            );

            for (BlockTransformation transformation : RRConfigx.blockTransformations) {

                if (block == Blocks.STONE_SLAB || block == Blocks.DOUBLE_STONE_SLAB) {
                    if (((state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.NETHERBRICK) || (state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.QUARTZ))) {
                        break;
                    }
                }

                if (block == transformation.inputBlock) {
                    if (transformation.heatResistance != 0) {
                        if (world.rand.nextInt(transformation.heatResistance) == 0) {

                            if (block instanceof BlockContainer) {
                                world.destroyBlock(burn_pos, true);
                            }
                            
                            world.setBlockState(
                                burn_pos,
                                transformation.outputBlock.getDefaultState()
                            );
                        }
                    } else {

                        if (block instanceof BlockContainer) {
                            world.destroyBlock(burn_pos, true);
                        }

                        world.setBlockState(
                            burn_pos,
                            transformation.outputBlock.getDefaultState()
                        );
                    }
                    break;
                }

            }

            if (block == RRBlocks.flare) RRBlocks.flare.onBlockDestroyedByPlayer(world, burn_pos, RRBlocks.flare.getDefaultState());
            //else if (id == RivalRebels.timedbomb) RivalRebels.timedbomb.onBlockDestroyedByPlayer(worldObj, (int) posX, (int) posY, (int) posZ, 0);
            else if (block == RRBlocks.plastic_explosives) RRBlocks.plastic_explosives.onBlockDestroyedByPlayer(world, burn_pos, RRBlocks.plastic_explosives.getDefaultState());
            else if (block == RRBlocks.mine) RRBlocks.plastic_explosives.onBlockDestroyedByPlayer(world, burn_pos, RRBlocks.plastic_explosives.getDefaultState());
            else if (block == Blocks.TNT)
            {
                world.setBlockToAir(new BlockPos((int) posX, (int) posY, (int) posZ));
                world.createExplosion(null, posX, posY, posZ, 4, false);
            }
            else if (block == RRBlocks.conduit) world.setBlockState(burn_pos, Blocks.FIRE.getDefaultState());
            else if (block == RRBlocks.reactive && world.rand.nextInt(20) == 0) world.setBlockState(burn_pos, Blocks.LAVA.getDefaultState());
            else if ((block == RRBlocks.green_camo || block == RRBlocks.grey_camo || block == RRBlocks.brown_camo) && world.rand.nextInt(40) == 0) world.setBlockState(burn_pos, RRBlocks.steel.getDefaultState());
            else if (block == RRBlocks.steel && world.rand.nextInt(40) == 0) world.setBlockState(burn_pos, Blocks.FIRE.getDefaultState());
        }
    }
}
