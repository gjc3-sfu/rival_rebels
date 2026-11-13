package com.rivalrebels.common.entity;

import java.util.List;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.oldstuff.Vec3;
import com.rivalrebels.common.entity.EntityGore.Model_Type;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RivalRebelsDamageSource;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityLaserBurst extends Entity {

    private Entity shooter;

    public EntityLaserBurst(World world, EntityPlayer player) {
        this(world, player, false);
        // check if this constructor matches original as I replaced it with a function overload
    }

    public EntityLaserBurst(World par1World, EntityPlayer player, boolean accurate) {
        super(par1World);
        this.shooter = player;
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
        this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.2F;
        this.posY -= 0.12;
        this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.2F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
        this.setAccurateHeading(this.motionX, this.motionY, this.motionZ, 4.0F * (float)Math.random() + 1.0F, accurate ? 0.001F : 0.075F);
    }

    public void setAccurateHeading(double directionX, double directionY, double directionZ, float par7, float par8) {
        float directionLength = MathHelper.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        directionX /= directionLength;
        directionY /= directionLength;
        directionZ /= directionLength;
        directionX += this.rand.nextGaussian() * par8;
        directionY += this.rand.nextGaussian() * par8;
        directionZ += this.rand.nextGaussian() * par8;
        directionX *= par7;
        directionY *= par7;
        directionZ *= par7;
        this.motionX = directionX;
        this.motionY = directionY;
        this.motionZ = directionZ;
        float var10 = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(directionX, directionZ) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(directionY, var10) * 180.0 / Math.PI);
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticksExisted++;
        if (this.ticksExisted > 60) {
            this.setDead();
        }

        Vec3d rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult MOP = this.world.rayTraceBlocks(rayStart, rayEnd);
        rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (MOP != null) {
            rayEnd = new Vec3d(MOP.hitVec.x, MOP.hitVec.y, MOP.hitVec.z);
        }

        if (!this.world.isRemote) {
            Entity var4 = null;
            List<Entity> var5 = this.world
                .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
            double var6 = 0.0;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith() && var9 != this.shooter) {
                float var10 = 0.3F;
                AxisAlignedBB var11 = var9.getEntityBoundingBox().grow(var10, var10, var10);
                RayTraceResult var12 = var11.calculateIntercept(rayStart, rayEnd);
                if (var12 != null) {
                    double var13 = rayStart.distanceTo(var12.hitVec);
                    if (var13 < var6 || var6 == 0.0) {
                        var4 = var9;
                        var6 = var13;
                    }
                }
                }
            }

            if (var4 != null) {
                MOP = new RayTraceResult(var4);
            }
        }

        if (MOP != null && !world.isRemote) { // remove !world.isRemote if it bugs on client
            if (MOP.entityHit == null) {
                BlockPos hit_pos = new BlockPos(MOP.hitVec.x, MOP.hitVec.y, MOP.hitVec.z);
                Block blockID = this.world.getBlockState(hit_pos).getBlock();
                if (blockID == Blocks.TNT && !this.world.isRemote) {
                    EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
                        this.world, MOP.hitVec.x + 0.5F, MOP.hitVec.y + 0.5F, MOP.hitVec.z + 0.5F, (EntityLivingBase)this.shooter
                    );
                    int new_fuse = this.world.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8;
                    entitytntprimed.setFuse(new_fuse);
                    this.world.spawnEntity(entitytntprimed);
                    this.world.setBlockToAir(hit_pos);
                }

                if (blockID == RRBlocks.plastic_explosives) {
                    //RRBlocks.plastic_explosives.explode(world, hit_pos);
                }

                /* 
                if (blockID == RivalRebels.timedbomb) {
                RivalRebels.timedbomb.onBlockDestroyedByExplosion(this.worldObj, MOP.blockX, MOP.blockY, MOP.blockZ, null);
                }
                */

                this.setDead();
            } else if (MOP.entityHit instanceof EntityPlayer && this.shooter != MOP.entityHit) {
                EntityPlayer player = (EntityPlayer)MOP.entityHit;
                NonNullList<ItemStack> armorSlots = player.inventory.armorInventory;
                int armorSlotIndex = this.world.rand.nextInt(4);
                if (!armorSlots.get(armorSlotIndex).isEmpty() && !this.world.isRemote) {
                    armorSlots.get(armorSlotIndex).damageItem(14, player);
                }

                RivalRebelsDamageSource damage = new RivalRebelsDamageSource(RivalRebelsDamageSource.RR_damage_sources.EVICERATION, this, shooter);
                player.attackEntityFrom(damage, 16.0F);
                if (player.getHealth() < 3.0F && player.isEntityAlive() && !player.capabilities.isCreativeMode) {
                    player.attackEntityFrom(damage, 2000000.0F); // execute
                    player.deathTime = 0;

                    this.spawn_gore(player);
                }

                this.setDead();
            } else if (MOP.entityHit instanceof EntityLivingBase
                && !(MOP.entityHit instanceof EntityAnimal)
                && !(MOP.entityHit instanceof EntityBat)
                && !(MOP.entityHit instanceof EntityVillager)
                && !(MOP.entityHit instanceof EntitySquid)) {
                EntityLivingBase entity = (EntityLivingBase)MOP.entityHit;
                RivalRebelsDamageSource damage = new RivalRebelsDamageSource(RivalRebelsDamageSource.RR_damage_sources.EVICERATION, this, shooter);
                entity.attackEntityFrom(damage, 6.0F);
                if (entity.getHealth() < 3.0F) {

                    entity.setDead(); // True damage execute
                    //RivalRebelsSoundPlayer.playSound(this, 2, 1, 4.0F);

                    this.spawn_gore(entity);

                    this.setDead();
                } /*else if (MOP.entityHit instanceof EntityRhodesHead
                || MOP.entityHit instanceof EntityRhodesLeftLowerArm
                || MOP.entityHit instanceof EntityRhodesLeftLowerLeg
                || MOP.entityHit instanceof EntityRhodesLeftUpperArm
                || MOP.entityHit instanceof EntityRhodesLeftUpperLeg
                || MOP.entityHit instanceof EntityRhodesRightLowerArm
                || MOP.entityHit instanceof EntityRhodesRightLowerLeg
                || MOP.entityHit instanceof EntityRhodesRightUpperArm
                || MOP.entityHit instanceof EntityRhodesRightUpperLeg
                || MOP.entityHit instanceof EntityRhodesTorso) {
                MOP.entityHit.attackEntityFrom(RivalRebelsDamageSource.laserburst, 6.0F);*/
            }
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
        this.setPosition(this.posX, this.posY, this.posZ);
    }


    private void spawn_gore(EntityLivingBase entity) {
        EntityGore.Model_Type model = EntityGore.Model_Type.fromEntity(entity);

        // Handle default-sized models
        if (model == EntityGore.Model_Type.DEFAULT) {
            int count = (int)(entity.getEntityBoundingBox().getAverageEdgeLength() * 2.0);

            model = EntityGore.Model_Type.DEFAULT;
            
            this.world.spawnEntity(new EntityGore(
                this.world, entity, EntityGore.Part_Type.HEAD, model
            ));

            this.world.spawnEntity(new EntityGore(
                this.world, entity, EntityGore.Part_Type.TORSO, model
            ));

            for (int i = 0; i < count; i++) {
                this.world.spawnEntity(new EntityGore(
                    this.world, entity, EntityGore.Part_Type.ARM, model
                ));
                this.world.spawnEntity(new EntityGore(
                    this.world, entity, EntityGore.Part_Type.LEG, model
                ));
            }

            return;
        }

        // Spawn head and torso
        this.world.spawnEntity(new EntityGore(
            this.world, entity, EntityGore.Part_Type.HEAD, model
        ));

        this.world.spawnEntity(new EntityGore(
            this.world, entity, EntityGore.Part_Type.TORSO, model
        ));

        // Spawn arms
        for (int i = 0; i < model.arm_count; i++) {
            this.world.spawnEntity(new EntityGore(
                this.world, entity, EntityGore.Part_Type.ARM, model
            ));
        }

        // Spawn legs
        for (int i = 0; i < model.leg_count; i++) {
            this.world.spawnEntity(new EntityGore(
                this.world, entity, EntityGore.Part_Type.LEG, model
            ));
        }
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
