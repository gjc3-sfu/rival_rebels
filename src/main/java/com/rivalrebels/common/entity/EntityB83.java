package com.rivalrebels.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityB83 extends Entity {




    public EntityB83(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
    }

    public EntityB83(World world, double x, double y, double z, float yaw, float pitch) {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(x, y, z, yaw, pitch);
        this.motionX = -(
            -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
        );
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
    }


    public EntityB83(World world, double x, double y, double z, double mx, double my, double mz) {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        //this.setAnglesMotion(mx, my, mz);
    }





    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readEntityFromNBT'");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeEntityToNBT'");
    }
    
}
