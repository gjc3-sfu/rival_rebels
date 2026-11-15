package com.rivalrebels.common.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGore extends Entity implements IEntityAdditionalSpawnData {

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
    protected boolean inGround;

    // Random offsets for gib spawning
    float spawnOffsetX = 0.0F;
    float spawnOffsetY = 0.0F;
    float spawnOffsetZ = 0.0F;

    private boolean green_blood = false;
    public double size = 0.0;
    public ResourceLocation playerSkin = null;



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
                this.green_blood = true;
                break;
            case CREEPER:
                this.setQuadruped();
                this.green_blood = true;
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
                this.green_blood = true;
                break;
            case SPIDER:
                this.setOctoped(0);
                break;
            case ZOMBIE:
                this.setBiped(0);
                break;
            default:
                break;
        }

        this.motionyaw = (float)((Math.random() - 0.5) * 135.0);
        this.motionpitch = (float)((Math.random() - 0.5) * 135.0);
        this.setLocationAndAngles(toBeGibbed.posX + this.spawnOffsetX, toBeGibbed.posY + this.spawnOffsetY, toBeGibbed.posZ + this.spawnOffsetZ, this.rotYaw, this.rotPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setThrowableHeading(0.3F);
    }

    private void setNoped() {
        if (this.limb_type == Part_Type.HEAD) {
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight();
            this.float_offset = 0.5F;
        }

        if (this.limb_type == Part_Type.TORSO) {
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight();
            this.float_offset = 0.25F;
        }

        this.rotYaw = this.gibbed_entity.rotationYaw;
    }

    private void setBiped(int thin) {
        if (this.limb_type == Part_Type.HEAD) {
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight() + 0.2F;
            this.float_offset = 0.25F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
            this.rotPitch = this.gibbed_entity.rotationPitch;
        }

        if (this.limb_type == Part_Type.TORSO) {
            if (thin == 2) {
                this.spawnOffsetY = 2.0F;
            } else {
                this.spawnOffsetY = 1.1F;
            }

            this.float_offset = 0.125F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
        }

        if (this.limb_type == Part_Type.ARM) {
            this.spawnOffsetX = (float)Math.random() - 0.5F;
            if (thin == 2) {
                this.spawnOffsetY = 1.6F;
            } else {
                this.spawnOffsetY = 1.1F;
            }

            this.spawnOffsetZ = (float)Math.random() - 0.5F;
            if (thin == 0) {
                this.float_offset = 0.125F;
            } else {
                this.float_offset = 0.0625F;
            }

            this.rotYaw = this.gibbed_entity.rotationYaw;
        }

        if (this.limb_type == Part_Type.LEG) {
            this.spawnOffsetX = (float)Math.random() - 0.5F;
            if (thin == 2) {
                this.spawnOffsetY = 0.95F;
            } else {
                this.spawnOffsetY = 0.35F;
            }

            this.spawnOffsetZ = (float)Math.random() - 0.5F;
            if (thin == 0) {
                this.float_offset = 0.125F;
            } else {
                this.float_offset = 0.0625F;
            }

            this.rotYaw = this.gibbed_entity.rotationYaw;
        }
    }

    private void setQuadruped() {
        if (this.limb_type == Part_Type.HEAD) {
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight();
            this.float_offset = 0.25F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
            this.rotPitch = this.gibbed_entity.rotationPitch;
        }

        if (this.limb_type == Part_Type.TORSO) {
            this.spawnOffsetY = 0.875F;
            this.float_offset = 0.125F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
        }

        if (this.limb_type == Part_Type.LEG) {
            this.spawnOffsetX = (float)(Math.random() - 0.5);
            this.spawnOffsetY = 0.25F;
            this.spawnOffsetZ = (float)(Math.random() - 0.5);
            this.float_offset = 0.125F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
        }
    }

    private void setOctoped(int mode) {
        if (mode != 2) {
            float scale = 1.0F;
            if (mode == 1) {
                scale = 0.666F;
            }

            if (this.limb_type == Part_Type.HEAD) {
                this.spawnOffsetY = this.gibbed_entity.getEyeHeight();
                this.float_offset = 0.25F * scale;
                this.rotYaw = this.gibbed_entity.rotationYaw;
                this.rotPitch = this.gibbed_entity.rotationPitch;
            }

            if (this.limb_type == Part_Type.TORSO) {
                this.spawnOffsetX = (float)(Math.random() - 0.5) * scale;
                this.spawnOffsetY = 0.25F * scale;
                this.spawnOffsetZ = (float)(Math.random() - 0.5) * scale;
                this.float_offset = 0.1F * scale;
                this.rotYaw = this.gibbed_entity.rotationYaw;
            }

            if (this.limb_type == Part_Type.LEG) {
                this.spawnOffsetX = (float)(Math.random() - 0.5) * scale;
                this.spawnOffsetY = 0.25F * scale;
                this.spawnOffsetZ = (float)(Math.random() - 0.5) * scale;
                this.float_offset = 0.0625F * scale;
                this.rotYaw = (float)(Math.random() * 360.0);
                this.rotPitch = 25.0F;
            }
        } else {
            if (this.limb_type == Part_Type.HEAD) {
                this.spawnOffsetY = this.gibbed_entity.getEyeHeight();
                this.float_offset = 1.5F;
                this.rotYaw = this.gibbed_entity.rotationYaw;
                this.rotPitch = this.gibbed_entity.rotationPitch;
            }

            if (this.limb_type == Part_Type.LEG) {
                this.spawnOffsetX = (float)(Math.random() - 0.5) * 4.0F;
                this.spawnOffsetZ = (float)(Math.random() - 0.5) * 4.0F;
                this.float_offset = 0.125F;
            }
        }
    }

    private void setDefault() {
        if (this.limb_type == Part_Type.HEAD) {
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight() + 0.2F;
            this.float_offset = 0.25F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
            this.rotPitch = this.gibbed_entity.rotationPitch;
        }

        if (this.limb_type == Part_Type.TORSO) {
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight() / 2.0F;
            this.float_offset = 0.125F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
        }

        if (this.limb_type == Part_Type.ARM) {
            this.spawnOffsetX = (float)((Math.random() - 0.5) * this.size);
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight() / 2.0F;
            this.spawnOffsetZ = (float)((Math.random() - 0.5) * this.size);
            this.float_offset = 0.0625F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
        }

        if (this.limb_type == Part_Type.LEG) {
            this.spawnOffsetX = (float)((Math.random() - 0.5) * this.size);
            this.spawnOffsetY = this.gibbed_entity.getEyeHeight() / 4.0F;
            this.spawnOffsetZ = (float)((Math.random() - 0.5) * this.size);
            this.float_offset = 0.0625F;
            this.rotYaw = this.gibbed_entity.rotationYaw;
        }
    }


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


    public void onUpdate() {
        if (this.ticksExisted == 0) {
            Side side = FMLCommonHandler.instance().getEffectiveSide();
            if (side == Side.SERVER) {
                //PacketDispatcher.packetsys.sendToAll(new EntityGorePacket(this));
            }
        }

        if (this.playerSkin == null && this.username != null && !this.username.isEmpty()
            && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && !this.username.equals("Steve")) {
            for (EntityPlayer player : this.world.playerEntities) {
                if (player.getCommandSenderEntity().equals(this.username)) {
                AbstractClientPlayer acp = (AbstractClientPlayer)player;
                this.playerSkin = acp.getLocationSkin();
                }
            }
        }

        this.ticksExisted++;
        super.onUpdate();
        if (this.inGround) {
            this.inGround = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
        }

        if (this.is_sliding) {
            this.slide_count++;
            if (this.slide_count == 140) {
                this.setDead();
            }
        }

        Vec3d rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(rayStart, rayEnd);
        rayStart = new Vec3d(this.posX, this.posY, this.posZ);
        rayEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingobjectposition != null) {
            this.is_sliding = true;
            this.posY = movingobjectposition.hitVec.y + this.float_offset;
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
        if (this.isInWater()) {
            f2 = 0.9F;
        } else if (!this.is_sliding) {
            if (this.world.isRemote /*&& RivalRebels.goreEnabled*/ && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                this.spawnBlood();
            }
        } else {
            f2 = 0.8F;
        }

        this.motionpitch = (float)((double)this.motionpitch * f2);
        this.motionyaw = (float)((double)this.motionyaw * f2);
        this.motionX *= f2;
        this.motionY *= f2;
        this.motionZ *= f2;
        this.motionY -= f3;
        if (this.is_sliding) {
            if (this.bounces == -1) {
                this.bounces = this.world.rand.nextInt(2) + 2;
            }

            if (this.bounces > 0) {
                this.bounces--;
                this.motionY = this.motionY * -(Math.random() * 0.5 + 0.35);
                this.is_sliding = false;
                this.slide_count = 0;
                this.pitch_lock = (int)(Math.ceil(this.rotationPitch / 90.0F) * 90.0);
            } else {
                this.motionY = 0.0;
            }

            this.motionpitch = 0.0F;
            this.rotationPitch = this.pitch_lock;
        }

        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @SideOnly(Side.CLIENT)
    private void spawnBlood() {
        for (int i = 0; i < 3; i++) {
            //RivalRebels.proxy.spawnGore(this.world, this, !this.green_blood);
        }
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        if (this.mob_type != null) {
            par1NBTTagCompound.setByte("Mob", (byte)this.mob_type.ordinal());
        }
        if (this.limb_type != null) {
            par1NBTTagCompound.setByte("Type", (byte)this.limb_type.ordinal());
        }
        par1NBTTagCompound.setDouble("Size", this.size);
        if (this.username != null) {
            par1NBTTagCompound.setString("Username", this.username);
        }
        par1NBTTagCompound.setByte("Green", (byte)(this.green_blood ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        int mob = par1NBTTagCompound.getByte("Mob") & 255;
        int limb = par1NBTTagCompound.getByte("Type") & 255;
        this.mob_type = par1NBTTagCompound.hasKey("Mob") && mob < Model_Type.values().length
            ? Model_Type.values()[mob] : null;
        this.limb_type = par1NBTTagCompound.hasKey("Type") && limb < Part_Type.values().length
            ? Part_Type.values()[limb] : null;
        this.size = par1NBTTagCompound.getDouble("Size");
        this.username = par1NBTTagCompound.hasKey("Username") ? par1NBTTagCompound.getString("Username") : null;
        this.green_blood = par1NBTTagCompound.getByte("Green") == 1;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeByte(this.mob_type == null ? -1 : this.mob_type.ordinal());
        buffer.writeByte(this.limb_type == null ? -1 : this.limb_type.ordinal());
        buffer.writeDouble(this.size);
        ByteBufUtils.writeUTF8String(buffer, this.username == null ? "" : this.username);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        int mob = buffer.readUnsignedByte();
        int limb = buffer.readUnsignedByte();
        this.mob_type = mob < Model_Type.values().length ? Model_Type.values()[mob] : null;
        this.limb_type = limb < Part_Type.values().length ? Part_Type.values()[limb] : null;
        this.size = buffer.readDouble();
        String username = ByteBufUtils.readUTF8String(buffer);
        this.username = username.isEmpty() ? null : username;
    }
    
}
