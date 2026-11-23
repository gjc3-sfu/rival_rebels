package com.rivalrebels.common.entity;

import java.io.IOException;

import com.rivalrebels.client.oldstuff.Vec3;
import com.rivalrebels.common.explosion.RivalRebelsExplosion;
import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.init.RivalRebelsDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityB2SpiritFrag extends Entity {

    private static final DataParameter<Byte> FRAG_TYPE =
        EntityDataManager.<Byte>createKey(EntityB2SpiritFrag.class, DataSerializers.BYTE);
    private boolean is_sliding;
    private int health = 40;

    // Physics
    float motionyaw = 0.0F;
    float motionpitch = 0.0F;
    private int ticks_in_ground;
    private int ticks_in_air;
    protected boolean in_ground;


    // Obsessed with enums
    public static enum FragType {
        LEFT,
        RIGHT
    }

    public EntityB2SpiritFrag(World world) {
        super(world);
        this.setEntityBoundingBox(new AxisAlignedBB(-2.5, -2.5, -2.5, 2.5,  2.5,  2.5));
        this.setSize(7.5F, 7.5F);
        this.ignoreFrustumCheck = true; // Render entity even if bounding box not on screen
        this.dataManager.set(FRAG_TYPE, (byte)(world.rand.nextBoolean() ? 0 : 1));
    }


    public EntityB2SpiritFrag(World par1World, Entity entity, FragType type) {
        super(par1World);
        this.setEntityBoundingBox(new AxisAlignedBB(-2.5, -2.5, -2.5, 2.5,  2.5,  2.5));
        this.setSize(7.5F, 7.5F);
        this.ignoreFrustumCheck = true;
        this.is_sliding = false;
        this.dataManager.set(FRAG_TYPE, (byte)(type == FragType.LEFT ? 0 : 1));
        this.motionyaw = (float)((Math.random() - 0.5) * 35.0);
        this.motionpitch = (float)((Math.random() - 0.5) * 25.0);
        this.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        double ox = this.posX;
        double oz = this.posZ;
        if (type == FragType.RIGHT) {
            this.posX = this.posX - MathHelper.cos(-this.rotationYaw / 180.0F * (float) Math.PI) * 7.5F;
            this.posZ = this.posZ - MathHelper.sin(-this.rotationYaw / 180.0F * (float) Math.PI) * 7.5F;
        } else if (type == FragType.LEFT) {
            this.posX = this.posX - MathHelper.cos((-this.rotationYaw + 180.0F) / 180.0F * (float) Math.PI) * 7.5F;
            this.posZ = this.posZ - MathHelper.sin((-this.rotationYaw + 180.0F) / 180.0F * (float) Math.PI) * 7.5F;
        }

        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = entity.motionX;
        this.motionY = entity.motionY;
        this.motionZ = entity.motionZ;
        this.motionX = this.motionX + (-ox + this.posX) * 0.1;
        this.motionZ = this.motionZ + (-oz + this.posZ) * 0.1;
        this.setFire(10);
    }



    //@Override
    //public AxisAlignedBB getCollisionBox(Entity entity) {
    //    return entity.getCollisionBoundingBox();
    //}

    //@Override
    //public AxisAlignedBB getEntityBoundingBox() {
    //    return super.getEntityBoundingBox();
    //}


    @Override
    public boolean canBeCollidedWith() {
        return true;
    }


    @Override
    public void onUpdate() {
        /* 
        if (this.ticksInAir == 0) {
            Side side = FMLCommonHandler.instance().getEffectiveSide();
            if (side == Side.SERVER) {
                for (EntityPlayer player : this.worldObj.playerEntities) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream(9);
                DataOutputStream outputStream = new DataOutputStream(bos);

                try {
                    outputStream.writeInt(17);
                    outputStream.writeInt(this.getEntityId());
                    outputStream.writeByte(this.type);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException var14) {
                    }
                }
                }
            }
        }
    */

        this.ticks_in_air++;
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.in_ground) {
            this.ticks_in_ground++;
            if (this.ticks_in_ground == 1200) {
                this.setDead();
            }

            this.in_ground = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            this.ticks_in_ground = 0;
            this.ticks_in_air = 0;
        }

        Vec3d rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(rayStart, rayEnd);
        rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingobjectposition != null) {
            this.is_sliding = true;
            this.posY = movingobjectposition.hitVec.y;
        }

        if (!this.world.isRemote) {
            for (Entity var9 : this.world
                .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0))) {
                if (var9 instanceof EntityRocket) {
                ((EntityRocket)var9).explode(null);
                }

                if (var9 instanceof EntityPlasmoid) {
                ((EntityPlasmoid)var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                ((EntityLaserBurst)var9).setDead();
                this.attackEntityFrom(DamageSource.GENERIC, 6.0F);
                }
            }
        }

        this.rotationPitch = this.rotationPitch + this.motionpitch;
        this.rotationYaw = this.rotationYaw + this.motionyaw;
        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.5F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.5F;
        float f2 = 0.99F;
        float f3 = 0.05F;
        if (this.is_sliding) {
            this.motionpitch = 0.0F;
            this.motionyaw = 0.0F;
            this.motionY = 0.0;
            f2 = 0.7F;
            f3 = 0.0F;
        }

        this.motionpitch = (float)((double)this.motionpitch * f2);
        this.motionyaw = (float)((double)this.motionyaw * f2);
        this.motionX *= f2;
        this.motionY *= f2;
        this.motionZ *= f2;
        this.motionY -= f3;
        this.setPosition(this.posX, this.posY, this.posZ);
    }


    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if (!this.isDead) {
            this.health = (int)(this.health - amount);
            if (this.health <= 0) {
                this.playSound(RRSounds.b2_explode, 10.0F, amount); //30.0 F seems awfully loud I'll reduce it
                DamageSource damage = new DamageSource("explosion").setExplosion();
                this.setDead();
                // make this standard explosion source
                new RivalRebelsExplosion(this.world, this.posX, this.posY, this.posZ, 6, true, true, damage);
                
            }
        }

        return true;
    }







    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'readEntityFromNBT'");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'writeEntityToNBT'");
    }






    @Override
    protected void entityInit() {
        this.dataManager.register(FRAG_TYPE, (byte)0);
    }

    public FragType getType() {
        return this.dataManager.get(FRAG_TYPE) == 0 ? FragType.LEFT : FragType.RIGHT;
    }

}
