package com.rivalrebels.common.entity;

import java.util.List;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.oldstuff.Vec3;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.core.RRDamageSource;
import com.rivalrebels.common.explosion.RivalRebelsExplosion;
import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.init.RivalRebelsDamageSource;
import com.rivalrebels.common.init.RivalRebelsDamageSource.RR_damage_sources;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityRocket extends Entity {

    private EntityLivingBase owner;
    public boolean fins;
    public int rotation = 45;
    public float slide = 0.0F; // no idea what this is
    private boolean inwaterprevtick = false;

    public EntityRocket(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
    }

    public EntityRocket(World par1World, EntityPlayer player) {
        super(par1World);
        this.owner = player;
        this.fins = false;
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(
            player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch
        );
        this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.0;
        this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 0.5F, 0.1F);
    }

    public EntityRocket(World par1World, double x, double y, double z) {
        super(par1World);
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
    }

    public void setAnglesMotion(double mx, double my, double mz) {
        this.motionX = mx;
        this.motionY = my;
        this.motionZ = mz;
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(mx, mz) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(my, MathHelper.sqrt(mx * mx + mz * mz)) * 180.0 / Math.PI);
    }

    public void setThrowableHeading(double mx, double my, double mz, float speed, float randomness) {
        float f2 = MathHelper.sqrt(mx * mx + my * my + mz * mz);
        mx /= f2;
        my /= f2;
        mz /= f2;
        mx += this.rand.nextGaussian() * 0.0075 * randomness;
        my += this.rand.nextGaussian() * 0.0075 * randomness;
        mz += this.rand.nextGaussian() * 0.0075 * randomness;
        mx *= speed;
        my *= speed;
        mz *= speed;
        this.setAnglesMotion(mx, my, mz);
    }


    public void explode(RayTraceResult mop) {
        if (mop != null && mop.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)mop.entityHit;
            NonNullList<ItemStack> armorSlots = player.inventory.armorInventory;
            int armorSlotIndex = this.world.rand.nextInt(4);
            if (!armorSlots.get(armorSlotIndex).isEmpty()) {
                armorSlots.get(armorSlotIndex).damageItem(48, (EntityLivingBase)mop.entityHit);
            }

        }

        if (mop != null && mop.entityHit == null) {
            BlockPos hit_block = new BlockPos(mop.hitVec.x, mop.hitVec.y, mop.hitVec.z);
            Block block = this.world.getBlockState(hit_block).getBlock();
            if (block == Blocks.GLASS || block == Blocks.GLASS_PANE || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE) {
                this.world.setBlockToAir(hit_block);
                world.playSound(null, mop.hitVec.x, mop.hitVec.y, mop.hitVec.z, RRSounds.glass_break, SoundCategory.AMBIENT, 5.0F, 0.3f);
                return;
            }
        }

        

        double soundX = mop != null ? mop.hitVec.x : this.posX;
        double soundY = mop != null ? mop.hitVec.y : this.posY;
        double soundZ = mop != null ? mop.hitVec.z : this.posZ;
        if (inwaterprevtick) {
            world.playSound(null, soundX, soundY, soundZ, RRSounds.m202_rocket_water_explode, SoundCategory.AMBIENT, 5.0F, 0.3f);
        } else {
            world.playSound(null, soundX, soundY, soundZ, RRSounds.m202_rocket_explosion, SoundCategory.AMBIENT, 5.0F, 0.3f);
        }


        EntityDamageSourceIndirect damage = new EntityDamageSourceIndirect("explosion", this, owner);
        new RivalRebelsExplosion(
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            RRConfigx.m202_explosion_size,
            false,
            false,
            damage
        );
        this.setDead();
    }


    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted == 0) {
            this.rotation = this.world.rand.nextInt(360);
            this.slide = this.world.rand.nextInt(21) - 10;

            for (int i = 0; i < 10; i++) {
                this.world
                .spawnParticle(
                    EnumParticleTypes.EXPLOSION_NORMAL,
                    this.posX - this.motionX * 2.0,
                    this.posY - this.motionY * 2.0,
                    this.posZ - this.motionZ * 2.0,
                    -this.motionX + (this.world.rand.nextFloat() - 0.5F) * 0.1F,
                    -this.motionY + (this.world.rand.nextFloat() - 0.5) * 0.1F,
                    -this.motionZ + (this.world.rand.nextFloat() - 0.5F) * 0.1F
                );
            }
        }

        this.rotation = this.rotation + (int)this.slide;
        this.slide = (float)(this.slide * 0.9);
        if (this.ticksExisted >= RRConfigx.m202_rocket_decay) {
            this.explode(null);
        }

        
        if (this.world.isRemote && this.ticksExisted >= 5 && !this.inWater && this.ticksExisted <= 100) {
            this.world
                .spawnEntity(
                new EntityPropulsionFX(
                    this.world,
                    this.posX,
                    this.posY,
                    this.posZ,
                    -this.motionX * 0.5,
                    -this.motionY * 0.5 - 0.1,
                    -this.motionZ * 0.5
                )
                );
        }
        

        Vec3d rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult mop = this.world.rayTraceBlocks(rayStart, rayEnd, false, true, false);
        if (!this.world.isRemote) {
            rayStart = new Vec3d(this.posX, this.posY, this.posZ);
            if (mop != null) {
                rayEnd = new Vec3d(mop.hitVec.x, mop.hitVec.y, mop.hitVec.z);
            } else {
                rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            }

            List<Entity> list = this.world
                .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
            double d0 = Double.MAX_VALUE;

            for (int i = 0; i < list.size(); i++) {
                Entity entity = (Entity)list.get(i);
                if (entity.canBeCollidedWith() && this.ticksExisted >= 7 && entity != this.owner) {
                RayTraceResult mop1 = entity.getEntityBoundingBox().grow(0.5, 0.5, 0.5).calculateIntercept(rayStart, rayEnd);
                if (mop1 != null) {
                    double d1 = rayStart.squareDistanceTo(mop1.hitVec);
                    if (d1 < d0) {
                        mop = new RayTraceResult(entity, mop1.hitVec);
                        d0 = d1;
                    }
                }
                }
            }
        }

        if (mop != null) {
            this.explode(mop);
        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        float var16 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var16) * 180.0 / Math.PI);

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float drag = 1.1F;
        if (this.ticksExisted > 25) {
            drag = 0.9999F;
        }

        if (this.isInWater()) {
            for (int var7 = 0; var7 < 4; var7++) {
                this.world
                .spawnParticle(
                    EnumParticleTypes.WATER_BUBBLE,
                    this.posX - this.motionX * 0.25,
                    this.posY - this.motionY * 0.25,
                    this.posZ - this.motionZ * 0.25,
                    this.motionX,
                    this.motionY,
                    this.motionZ
                );
            }

            if (!this.inwaterprevtick) {
                world.playSound(null, this.posX, this.posY, this.posZ, RRSounds.m202_rocket_water_splash, SoundCategory.AMBIENT, 0.5f, 0.5f);
            }

            drag = 0.8F;
            this.inwaterprevtick = true;
        }

        this.motionX *= drag;
        this.motionY *= drag;
        this.motionZ *= drag;
        if (this.ticksExisted == 3) {
            this.fins = true;
            this.rotationPitch = (float)(this.rotationPitch + 22.5);
        }

        this.setPosition(this.posX, this.posY, this.posZ);
        this.ticksExisted++;
    }





    public int getBrightnessForRender(float par1) {
        return 1000;
    }

    public float getBrightness(float par1) {
        return 1000.0F;
    }


    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }
    
}
