package com.rivalrebels.common.entity;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPropulsionFX extends EntityThrowable {
    private int ticksInAir;

    public EntityPropulsionFX(World world) {
        super(world);
        this.ticksInAir = 0;
        this.setSize(0.1F, 0.1F);
    }

    public EntityPropulsionFX(World world, double x, double y, double z) {
        super(world);
        y += -0.2;
        this.ticksInAir = 0;
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
    }

    public EntityPropulsionFX(World world, double x, double y, double z, double mX, double mY, double mZ) {
        super(world);
        this.ticksInAir = 0;
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.setVelocity(mX, mY, mZ);
    }

    public void setVelocity(double mX, double mY, double mZ) {
        this.motionX = mX;
        this.motionY = mY;
        this.motionZ = mZ;
    }

    public int getBrightnessForRender(float par1) {
        return 1000;
    }

    public float getBrightness(float par1) {
        return 1000.0F;
    }

    public boolean isInRangeToRenderDist(double par1) {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticksInAir++;
        if (this.ticksInAir >= 5 && this.world.rand.nextInt(2) == 1 || this.inWater) {
            this.setDead();
        }

        this.posY += 0.005;
        this.posX = this.posX + (this.world.rand.nextDouble() - 0.5) * 0.07;
        this.posY = this.posY + (this.world.rand.nextDouble() - 0.5) * 0.07;
        this.posZ = this.posZ + (this.world.rand.nextDouble() - 0.5) * 0.07;
        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public void readEntityFromNBT(NBTTagCompound var1) {
    }

    public void writeEntityToNBT(NBTTagCompound var1) {
    }

    protected float getGravityVelocity() {
        return 0.0F;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        // Nothing
    }

}

