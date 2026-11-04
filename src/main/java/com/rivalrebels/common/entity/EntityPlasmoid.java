package com.rivalrebels.common.entity;

import com.rivalrebels.common.core.BlackList;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RRConfigOptions;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityPlasmoid extends Entity {
    private EntityPlayer thrower;
    public int				rotation	= 45;
    public int				size		= 3;
    public float			slide		= 0;
    boolean gravity = false;

    @SuppressWarnings(value = "unused")
    public EntityPlasmoid(World world) {
        super(world);
        setSize(0.5F, 0.5F);
        size = 3;
    }

    @SuppressWarnings(value = "unused")
    public EntityPlasmoid(World world, double pos_x, double pos_y, double pos_z) {
        super(world);
        setSize(0.5F, 0.5F);
        setPosition(pos_x, pos_y, pos_z);
        setRenderYawOffset(0.0F);
        size = 3;
    }

    public EntityPlasmoid(World world, EntityPlayer thrower, EntityLiving target, float speed, float inaccuracy) {
        super(world);
        this.thrower = thrower;

        posY = thrower.posY + thrower.getEyeHeight() - 0.10000000149011612D;
        double deltaX = target.posX - thrower.posX;
        double deltaY = target.posY + target.getEyeHeight() - 0.699999988079071D - posY;
        double deltaZ = target.posZ - thrower.posZ;
        double horizontalDistance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        if (horizontalDistance >= 1.0E-7D) {
            float yaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F;
            float pitch = (float) (-(Math.atan2(deltaY, horizontalDistance) * 180.0D / Math.PI));
            double normalizedX = deltaX / horizontalDistance;
            double normalizedZ = deltaZ / horizontalDistance;
            setLocationAndAngles(thrower.posX + normalizedX, posY, thrower.posZ + normalizedZ, yaw, pitch);
            setRenderYawOffset(0.0F);
            float verticalLead = (float) horizontalDistance * 0.2F;
            setAccurateHeading(deltaX, deltaY + verticalLead, deltaZ, speed, inaccuracy);
        }
        size = 3;
    }

    public EntityPlasmoid(World world, EntityPlayer thrower, float speed, boolean drop) {
        super(world);
        speed *= (gravity ? 3 : 1);
        gravity = drop;
        this.thrower = thrower;
        setSize(0.5F, 0.5F);
        setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
        posY -= 0.0D;
        posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
        setPosition(posX, posY, posZ);
        setRenderYawOffset(0.0F);
        motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
        motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
        motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
        setAccurateHeading(motionX, motionY, motionZ, speed * 1.5F, 1.0F);
    }

    public EntityPlasmoid(World world, double originX, double originY, double originZ, double directionX, double directionY, double directionZ, float speed) {
        super(world);
        setSize(0.5F, 0.5F);
        double motionScale = speed / MathHelper.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        setPosition(originX + directionX * motionScale, originY + directionY * motionScale, originZ + directionZ * motionScale);
        setRenderYawOffset(0.0F);
        size = 3;
        motionX = directionX;
        motionY = directionY;
        motionZ = directionZ;
        float horizontalMotion = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(directionX, directionZ) * 180.0D / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(directionY, horizontalMotion) * 180.0D / Math.PI);
    }

    public void setAccurateHeading(double directionX, double directionY, double directionZ, float speed, float inaccuracy) {
        float directionLength = MathHelper.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        directionX /= directionLength;
        directionY /= directionLength;
        directionZ /= directionLength;
        directionX *= speed;
        directionY *= speed;
        directionZ *= speed;
        motionX = directionX;
        motionY = directionY;
        motionZ = directionZ;
        float horizontalMotion = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(directionX, directionZ) * 180.0D / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(directionY, horizontalMotion) * 180.0D / Math.PI);
    }

    @Override
    public int getBrightnessForRender() {
        return 1000;
    }

    @Override
    public float getBrightness() {
        return 1000F;
    }

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return true;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        System.out.println("Ylevel: "+ posY);
        if (ticksExisted == 0) {
            rotation = world.rand.nextInt(360);
            slide = world.rand.nextInt(21) - 10;
        }
        if (gravity) {
            motionY -= 0.05f;
        }
        ++ticksExisted;
        rotation += (int) slide;
        slide *= 0.9;
        if (ticksExisted >= RRConfigOptions.plasmoid_decay * (gravity ? 3 : 1)) explode();

        Vec3d startVec = new Vec3d(posX, posY, posZ);
        Vec3d endVec = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
        RayTraceResult mop = world.rayTraceBlocks(startVec, endVec, false, true, false);
        startVec = new Vec3d(posX, posY, posZ);

        if (mop != null) endVec = new Vec3d(mop.hitVec.x, mop.hitVec.y, mop.hitVec.z);

        else endVec = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

        List<Entity> nearbyEntities = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().contract(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double closestHitDistance = Double.MAX_VALUE;
        for (Entity entity : nearbyEntities) {
            if (entity.canBeCollidedWith() && (entity != thrower || ticksExisted >= 5)) {
                RayTraceResult mop1 = entity.getEntityBoundingBox().expand(0.5f, 0.5f, 0.5f).calculateIntercept(startVec, endVec);
                if (mop1 != null) {
                    double hitDistance = startVec.squareDistanceTo(mop1.hitVec);
                    if (hitDistance < closestHitDistance) {
                        mop = new RayTraceResult(entity, mop1.hitVec);
                        closestHitDistance = hitDistance;
                    }
                }
            }
        }
        if (mop != null) explode();

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float horizontalMotion = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

        for (rotationPitch = (float) (Math.atan2(motionY, horizontalMotion) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
        }

        while (rotationPitch - prevRotationPitch >= 180.0F)
            prevRotationPitch += 360.0F;

        while (rotationYaw - prevRotationYaw < -180.0F)
            prevRotationYaw -= 360.0F;

        while (rotationYaw - prevRotationYaw >= 180.0F)
            prevRotationYaw += 360.0F;

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        setPosition(posX, posY, posZ);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

    }

    protected void explode() {
        if (!world.isRemote) {
            setDead();
            int i = -1;

            world.setBlockState(new BlockPos(posX, posY, posZ), RRBlocks.plasma_explosion.getDefaultState());
        }
    }

    // trying to fix the bug 
    protected void explodex() {
        if (!world.isRemote) {
            setDead();

            Block block = Blocks.AIR;
            int distance = -1;

            for (; (block.getMaterial(null).blocksMovement()
                    || BlackList.plasmaExplosion(block)) && distance < 4; distance++) {

                BlockPos blockPos = new BlockPos(
                        posX - motionX * distance,
                        posY - motionY * distance,
                        posZ - motionZ * distance
                );

                block = world.getBlockState(blockPos).getBlock();
            }

            BlockPos explosionPos = new BlockPos(
                    posX - motionX * distance,
                    posY - motionY * distance,
                    posZ - motionZ * distance
            );

            world.setBlockState(
                    explosionPos,
                    RRBlocks.plasma_explosion.getDefaultState()
            );
        }
    }

    @Override
    protected void entityInit() {
    }
}