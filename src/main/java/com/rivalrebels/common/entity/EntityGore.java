package com.rivalrebels.common.entity;

import net.minecraft.entity.Entity;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityGore extends Entity {

    public enum Part_Type {
        HEAD,
        TORSO,
        ARM,
        LEG;
    }

    public enum Model_Type {

        PLAYER(2,2),
        ZOMBIE(2, 2),
        PIGZOMBIE(2, 2),
        SKELETON(2, 2),
        ENDERMAN(2, 2),
        CREEPER(0, 4),
        SLIME(0, 0),
        MAGMACUBE(0, 0),
        SPIDER(0, 8),
        CAVESPIDER(0, 8),
        GHAST(0, 9),
        DEFAULT;

        final int arm_count;
        final int leg_count;

        private Model_Type(int arm_count, int leg_count) {
            this.arm_count = arm_count;
            this.leg_count = leg_count;
        }

        private Model_Type() {
            this.arm_count = -1;
            this.leg_count = -1;
        }

        public static Model_Type fromEntity(Entity entity) {
            if (entity instanceof EntityPlayer) return PLAYER;
            if (entity instanceof EntityPigZombie) return PIGZOMBIE;
            if (entity instanceof EntityZombie) return ZOMBIE;
            if (entity instanceof EntitySkeleton) return SKELETON;
            if (entity instanceof EntityEnderman) return ENDERMAN;
            if (entity instanceof EntityCreeper) return CREEPER;
            if (entity instanceof EntityMagmaCube) return MAGMACUBE;
            if (entity instanceof EntitySlime) return SLIME;
            if (entity instanceof EntityCaveSpider) return CAVESPIDER;
            if (entity instanceof EntitySpider) return SPIDER;
            if (entity instanceof EntityGhast) return GHAST;

            return DEFAULT;
        }


    }

    public enum Model_Typex {
        HUMANOID,
        SKELETON,
        ENDERMAN,
        CREEPER,
        SLIME,
        MAGMACUBE,
        SPIDER,
        CAVESPIDER,
        GHAST,
        DEFAULT;
    }

    private String username;
    public Part_Type limb_type;
    public Model_Type mob_type;
    private Entity gibbed_entity;
    // Physics fields
    private boolean is_sliding = false;
    private int slide_count;
    private int bounces;
    private int pitch_lock; // controls rolling
    float rotYaw = 0.0F; // Angular displacement
    float rotPitch = 0.0F; // Angular displacement
    float motionyaw = 0.0F; // Angular momentum
    float motionpitch = 0.0F; // Angular momentum
    private float float_offset; // how high off the ground the gore hovers

    private boolean green_blood = false;
    public double size = 0.0;



    public EntityGore(World world) {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    public EntityGore(World par1World, double x, double y, double z, double mx, double my, double mz, Part_Type type, Model_Type mob) {
        super(par1World);
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.setAnglesMotion(mx, my, mz);
        this.limb_type = type;
        this.mob_type = mob;
    }

    public EntityGore(World par1World, Entity toBeGibbed, Part_Type limb_Type, Model_Type Mob) {
        super(par1World);
    }
    /* 
    public EntityGore(World par1World, Entity toBeGibbed, Part_Type limb_Type, Model_Type Mob) {
        super(par1World);
        this.setSize(0.25F, 0.25F);
        this.gibbed_entity = toBeGibbed;
        this.slide_count = 0;
        this.bounces = -1;
        this.pitch_lock = 0;
        this.limb_type = limb_Type;
        this.green_blood = false;
        this.mob_type = Mob;

        switch (Mob) {
            case CAVESPIDER:
                this.setOctoped(1);
                this.greenblood = true;
                break;
            case CREEPER:
                this.setQuadruped();
                this.greenblood = true;
                break;
            case DEFAULT:
                this.size = this.gibbed_entity.getEntityBoundingBox().getAverageEdgeLength();
                if (this.size > 2.0 || this.size < 1.0) {
                    this.green_blood = true;
                }
                this.setDefault();
                break;
            case ENDERMAN:
                this.setBiped(2);
                break;
            case GHAST:
                this.setOctoped(2);
                break;
            case MAGMACUBE:
                this.setNoped();
                break;
            case PIGZOMBIE:
                this.setBiped(0);
                break;
            case PLAYER:
                this.username = ((EntityPlayer)this.gibbed_entity).getCommandSenderEntity().getName();
                this.setBiped(0);
                break;
            case SKELETON:
                this.setBiped(1);
                this.green_blood = true;
                break;
            case SLIME:
                this.setNoped();
                this.greenblood = true;
                break;
            case SPIDER:
                this.setOctoped(0);
                break;
            case ZOMBIE:
                this.setBiped(0)
                break;
            default:
                int cat = 4/0;
                break;
        }

        this.motionyaw = (float)((Math.random() - 0.5) * 135.0);
        this.motionpitch = (float)((Math.random() - 0.5) * 135.0);
        this.setLocationAndAngles(toBeGibbed.posX + this.x, toBeGibbed.posY + this.y, toBeGibbed.posZ + this.z, this.rotYaw, this.rotPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setThrowableHeading(0.3F);
    }
    */

    public void setAnglesMotion(double mx, double my, double mz) {
        this.motionX = mx;
        this.motionY = my;
        this.motionZ = mz;
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(mx, mz) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(my, MathHelper.sqrt(mx * mx + mz * mz)) * 180.0 / Math.PI);
    }


    public void setThrowableHeading(float par7) {
        this.motionX = this.rand.nextGaussian() * par7;
        this.motionY = Math.abs(this.rand.nextGaussian() * par7);
        this.motionZ = this.rand.nextGaussian() * par7;
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
