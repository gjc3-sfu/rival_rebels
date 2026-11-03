package com.rivalrebels.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class EntityB2Spirit/* extends Entity*/ {

   private int ticksSinceStart = 0;
   private int timeLeft = -1;
   private double tx = 0.0;
   private double ty = 0.0;
   private double tz = 0.0;
   public int health;
   public boolean carpet = false;
   //public EntityRhodes rhodeswing = null;
   public static int staticEntityIndex = 10;
   public int entityIndex = 10;
   public boolean dropAnything = true;
   public static boolean randchance = true;
   public boolean dropOnlyOne = false;
   public static boolean trash = true;
   public static boolean leave = true;
   public int mode = 0;
   Entity rhodes = null;





    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("drop", this.entityIndex);
        nbt.setFloat("tx", (float)this.tx);
        nbt.setFloat("ty", (float)this.ty);
        nbt.setFloat("tz", (float)this.tz);
        nbt.setInteger("age", this.ticksSinceStart);
        nbt.setInteger("health", this.health);
        nbt.setInteger("duration", this.timeLeft);
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.entityIndex = nbt.getInteger("drop");
        //this.carpet = this.entityIndex < ItemRoda.rodaindex;
        this.tx = nbt.getFloat("tx");
        this.ty = nbt.getFloat("ty");
        this.tz = nbt.getFloat("tz");
        this.ticksSinceStart = nbt.getInteger("age");
        this.health = nbt.getInteger("health");
        this.timeLeft = nbt.getInteger("duration");
        if (this.ticksSinceStart == 0) {
        //    double dx = this.tx - this.posX;
        //    double dy = this.ty - this.posY;
        //    this.startBombRun(dx, dy);
        }
    }
}
