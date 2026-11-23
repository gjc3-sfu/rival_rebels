package com.rivalrebels.common.entity;

import com.rivalrebels.common.init.RRSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityB2Spirit extends Entity {

    // Property fields
    public int health;
    private int ticksSinceStart = 0;
    private int timeLeft = -1;


    // Target coordinates
    private double tx = 1.0;
    private double ty = 1.0;
    private double tz = 1.0;

    // Behaviour fields
    public boolean carpet = false; // Whether to spawn multiple on this pass
    public static int staticEntityIndex = 10;
    public int entityIndex = 10;
    public static boolean randchance = true;
    public boolean dropOnlyOne = false;
    public static boolean trash = true;
    public static boolean will_leave = true;
    public AI_mode mode = AI_mode.TO_TARGET;
    Entity rhodes = null;

    // Obsessed with enums
    private static enum AI_mode{
        TO_TARGET,
        U_TURN_LEFT,
        U_TURN_RIGHT
    }

    public EntityB2Spirit(World world) {
        super(world);
        this.setSize(30.0F, 4.0F); // wide hitbox
        this.ignoreFrustumCheck = true;
        //this.getEntityBoundingBox().(-10.0, -3.0, -10.0, 10.0, 4.0, 10.0);
        this.health = 50;//RivalRebels.b2spirithealth;
        if (!this.world.isRemote) {
            this.startBombRun(this.tx - this.posX, this.tz - this.posZ);
        }
    }

    public EntityB2Spirit(World world, double x, double y, double z, double player_x, double player_y, double player_z, boolean c, boolean da) {
        this(world);
        this.carpet = c;
        this.tx = x;
        this.ty = y;
        this.tz = z;
        this.dropOnlyOne = !da;
        if (this.carpet) {
            this.entityIndex = 10;
            if (staticEntityIndex <= 23) {
                this.entityIndex = staticEntityIndex;
            }
        } else {
            this.entityIndex = 24;
            if (staticEntityIndex > 23) {
                this.entityIndex = staticEntityIndex;
            }
        }

        if (!this.world.isRemote) {
            this.startBombRun(this.tz - player_z, player_x - this.tx);
        }
    }


    public void onUpdate() {
        super.onUpdate();
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        if (Math.random() > 0.8F) {
            // Ambient sound
            this.playSound(RRSounds.b2_ambient, 4.5F, 1.3F);
            
        }

        /* 
        if (this.rhodeswing != null) {
            this.motionX = this.rhodeswing.posX - this.posX;
            this.motionY = this.rhodeswing.posY - this.posY;
            this.motionZ = this.rhodeswing.posZ - this.posZ;
            double t = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.motionX /= t;
            this.motionY /= t;
            this.motionZ /= t;
            this.rotationYaw = this.rhodeswing.rotationYaw;
            this.rotationPitch = (float)Math.min(t, 90.0);
            if (t < 25.0 || this.ticksExisted > 100) {
                this.rhodeswing.b2energy = 8000;
                this.rhodeswing.freeze = false;
                this.setDead();
            }
        }
        */

        if (!this.world.isRemote) {
            double distfromtarget = Math.sqrt(
                (this.tx - this.posX) * (this.tx - this.posX) + (this.tz - this.posZ) * (this.tz - this.posZ)
            );
            this.ticksSinceStart++;
            if (this.ticksSinceStart >= 60 && this.mode == AI_mode.TO_TARGET && distfromtarget < 200) {
                if (this.carpet || (this.dropOnlyOne ? this.ticksSinceStart == 80 : this.ticksSinceStart % 40 == 0)) {
                    this.dropBomb();
                }


                // If you went past the target, and your current direction puts your further away from the target, turn around


                // What if the plane isn't close to the target in the first place?

                // Turn around do
                double hypothetical_x = this.posX + this.motionX;
                double hypothetical_z = this.posZ + this.motionZ;
                double hypothetical_dist = Math.sqrt(
                    (this.tx - hypothetical_x) * (this.tx - hypothetical_x) + (this.tz - hypothetical_z) * (this.tz - hypothetical_z)
                );

                boolean is_getting_further = hypothetical_dist > distfromtarget;


                if (distfromtarget > 80.0 && is_getting_further) {
                    this.mode = this.world.rand.nextBoolean() ? AI_mode.U_TURN_LEFT : AI_mode.U_TURN_RIGHT;
                    if (trash) {
                        this.carpet = true;
                        //this.entityIndex = this.world.rand.nextInt(ItemRoda.rodaindex); I don't like the entity index system I'll fix it later
                    }

                    if (will_leave) {
                        if (this.ticksSinceStart > 1000 && this.world.rand.nextInt(4) == 1) {
                            this.motionY = 2.0;
                        }

                        if (!trash && this.dropOnlyOne) {
                            this.motionY = 2.0;
                        }
                    }
                }
            }

            if (this.mode != AI_mode.TO_TARGET) {
                if (this.mode == AI_mode.U_TURN_LEFT) {
                    this.rotationYaw += 10.0F;
                } else if (this.mode == AI_mode.U_TURN_RIGHT) {
                    this.rotationYaw -= 10.0F;
                }

                this.motionX = MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI);
                this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI);







                // Fly straight criteria
                if (distfromtarget < 80.0) {
                    this.mode = AI_mode.TO_TARGET;
                }
            }

            // Intersecting entities process
            for (Entity intersecting_entity : this.world
                .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0))) {
                if (intersecting_entity instanceof EntityRocket) {
                ((EntityRocket)intersecting_entity).explode(null);
                }

                if (intersecting_entity instanceof EntityPlasmoid) {
                ((EntityPlasmoid)intersecting_entity).explode();
                }

                if (intersecting_entity instanceof EntityLaserBurst) {
                ((EntityLaserBurst)intersecting_entity).setDead();
                this.attackEntityFrom(DamageSource.GENERIC, 6.0F);
                }
            }

            this.timeLeft--;
            if (this.timeLeft == 0) {
                this.motionY = 2.0;
            }

            // Leave condition
            if (this.posY > 256.0) {
                this.setDead();
            }
        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;

        this.setPosition(this.posX, this.posY, this.posZ);
    }


    public void dropBomb() {
        // just spawn tnt idk

        Entity tnt_entity = new EntityTNTPrimed(
            world, 
            this.posX + Math.random() * 4.0 - 2.0, 
            this.posY - 3.5, 
            this.posZ + Math.random() * 4.0 - 2.0, 
            null
        );

        // maybe impulse the tnt downwards 1.5F

        this.world.spawnEntity(tnt_entity);

        /* 
        ItemRoda.spawn(
            this.entityIndex,
            this.worldObj,
            this.posX + Math.random() * 4.0 - 2.0,
            this.posY - 3.5,
            this.posZ + Math.random() * 4.0 - 2.0,
            this.motionX * 0.1F,
            -1.0,
            this.motionZ * 0.1F,
            1.0,
            0.0
        );
        */
    }


    @Override 
    public boolean isInRangeToRenderDist(double par1) {
        return true;
    }

    public void startBombRun(double x, double z) {
        /* 
        if (this.rhodes != null) {
            this.tx = this.rhodes.posX;
            this.ty = this.rhodes.posY;
            this.tz = this.rhodes.posZ;
            x = -this.rhodes.motionX;
            z = -this.rhodes.motionZ;
        }
        */

        double lengthSquared = x * x + z * z;
        if (lengthSquared == 0.0) {
            lengthSquared = 1;
        }

        double dist = 1.0 / Math.sqrt(lengthSquared);
        x *= dist;
        z *= dist;
        this.motionX = -x;
        this.motionZ = -z;
        this.setPosition(this.tx + x * 80.0, this.ty + 60.0, this.tz + z * 80.0);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
    }

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
            double dx = this.tx - this.posX;
            double dz = this.tz - this.posZ;
            this.startBombRun(dx, dz);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float par2) {
        super.attackEntityFrom(damageSource, par2);
        if (!this.isDead && !this.world.isRemote) {
            this.health = (int)(this.health - par2);
            if (this.health <= 0) {
                this.setDead();
                this.world.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 6.0F, true);
                this.world.spawnEntity(new EntityB2SpiritFrag(this.world, this, EntityB2SpiritFrag.FragType.LEFT));
                this.world.spawnEntity(new EntityB2SpiritFrag(this.world, this, EntityB2SpiritFrag.FragType.RIGHT));
                EntityZombie pz = new EntityZombie(this.world);
                pz.setPosition(this.posX, this.posY, this.posZ);
                this.world.spawnEntity(pz);
                //RivalRebelsSoundPlayer.playSound(this, 0, 0, 30.0F, 1.0F); 30.0F seems very loud, I'll reduce it a bit
                this.playSound(RRSounds.b2_explode, 10.0F, 1.0F);
            }
        }

        return true;
    }

    @Override
    protected void entityInit() {
    }
}
