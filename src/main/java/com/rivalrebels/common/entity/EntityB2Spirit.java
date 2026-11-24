package com.rivalrebels.common.entity;

import java.util.function.Supplier;

import com.rivalrebels.common.init.RRSounds;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityB2Spirit extends Entity implements IEntityAdditionalSpawnData {

    // Property fields
    private int health;
    private int tick_age = 0;
    private int timeLeft = -1;


    // Target coordinates
    private double tx = 1.0;
    private double ty = 1.0;
    private double tz = 1.0;

    // AI Behaviour fields
    public boolean carpet = false; // Whether to spawn multiple on this pass
    public static int staticEntityIndex = 10;
    public int entityIndex = 10;
    public static boolean randchance = true; // This seems useless
    public boolean dropOnlyOne = false;
    public static boolean trash = true; // Whether to litter the zone after the first pass
    public static boolean will_leave = true; // Will eventually leave the area
    private boolean leaving = false; // Whether the B2 is currently leaving
    private AI_mode mode = AI_mode.TO_TARGET;
    private Load_amount load_mode;

    private boolean controller; // Whether this plane was called by a controller (no need to store in nbt)


    private EntityLivingBase pilot;
    // Mount Behaviour fields
    
    

    // Obsessed with enums
    private static enum AI_mode{
        TO_TARGET,
        U_TURN_LEFT,
        U_TURN_RIGHT
    }

    // Obsessed with enums
    private static enum Load_amount {
        SINGLE,
        FEW,
        CARPET
    }

    public EntityB2Spirit(World world) {
        super(world);
        this.setSize(30.0F, 4.0F); // wide hitbox
        this.ignoreFrustumCheck = true;
        //this.getEntityBoundingBox().(-10.0, -3.0, -10.0, 10.0, 4.0, 10.0);
        this.health = 50;//RivalRebels.b2spirithealth;
    }

    // Called by controller summon
    public EntityB2Spirit(World world, double x, double y, double z, double player_x, double player_y, double player_z, boolean carpet, boolean drop_multiple, boolean controller) {
        this(world);
        this.carpet = carpet;
        this.tx = x;
        this.ty = y;
        this.tz = z;
        this.dropOnlyOne = !drop_multiple;
        this.controller = controller;
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
            this.setInitialPosition_from_player(player_x, player_z);
        }
        
    }

    private double[] normalize2d(double dx, double dz) {
        double lengthSquared = dx * dx + dz * dz;
        if (lengthSquared <= 0.0000001) {
            return new double[] {0.0, 1.0};
        }

        double length = Math.sqrt(lengthSquared);
        return new double[] {dx / length, dz / length};
    }

    private void setInitialHeading(double from_x, double from_y) {
        double dx = this.tx - from_x;
        double dz = this.tz - from_y;
        double[] heading = this.normalize2d(dx, dz);
        motionX = heading[0];
        motionZ = heading[1];
        rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        this.prevRotationYaw = this.rotationYaw;
    }

    private void setInitialPosition_from_player(double player_x, double player_y) {

        double dx = this.tx - player_x;
        double dz = this.tz - player_y;

        double[] outward = this.normalize2d(dx, dz);
        this.setPosition(
            this.tx + outward[0] * 80.0,
            this.ty + 60.0,
            this.tz + outward[1] * 80.0
        );
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

        if (!this.world.isRemote) {


            double distfromtarget = Math.sqrt(
                (this.tx - this.posX) * (this.tx - this.posX) + (this.tz - this.posZ) * (this.tz - this.posZ)
            );
            this.tick_age++;


            /*  if I want readjusting steering
            if (this.mode == AI_mode.TO_TARGET) {
                double dx = this.tx - this.posX;
                double dz = this.tz - this.posZ;
                double[] heading = this.normalize2d(dx, dz);
                this.motionX = heading[0];
                this.motionZ = heading[1];
                this.prevRotationYaw = this.rotationYaw;
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            }
            */

            // Decide whether to drop bomb(s)
            if (this.tick_age >= 60 && this.mode == AI_mode.TO_TARGET && distfromtarget < 200) {
                // Decide whether it's a carpet, single, or few

                /*
                switch (this.load_mode) {
                    case CARPET:
                        this.dropBomb(null);
                        break;
                    case FEW:
                        if (this.tick_age % 40 == 0) this.dropBomb(null);
                        break;
                    case SINGLE:
                        if (this.tick_age == 80) this.dropBomb(null);
                        break;
                    default:
                        break;
                    
                }
                */
                if (this.carpet || (this.dropOnlyOne ? this.tick_age == 80 : this.tick_age % 40 == 0)) {
                    this.dropBomb(null);
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
                    // Decide whether to start dropping trash after the first turn
                    if (trash) {
                        this.carpet = true;
                        //this.entityIndex = this.world.rand.nextInt(ItemRoda.rodaindex); I don't like the entity index system I'll fix it later
                    }

                    // Decide whether to leave after this bombing run
                    if (will_leave) {
                        if (this.tick_age > 1000 && this.world.rand.nextInt(4) == 1) {
                            this.leaving = true;
                        }

                        // Always leave if only one drop was ordered and trashing isn't on
                        if (!trash && this.dropOnlyOne) {
                            this.leaving = true;
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


            // hard leaving condition
            if (timeLeft != -1) {
                this.timeLeft--;
            }
            if (this.timeLeft == 0) {
                this.leaving = true;
            }

            
            // Leave Logic:

            if (this.leaving) {
                this.motionY = 2.0;
            }
            if (this.posY > 256.0 && this.leaving) {
                this.setDead(); // escaped
            }


        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;

        this.setPosition(this.posX, this.posY, this.posZ);
    }


    private void dropBomb(Supplier<? extends Entity> payload) {
        // just spawn tnt idk
        Entity entity;

        if (payload != null) {
            entity = payload.get();
        } else {
            entity = new EntityTNTPrimed(
                world, 
                this.posX + Math.random() * 4.0 - 2.0, 
                this.posY - 3.5, 
                this.posZ + Math.random() * 4.0 - 2.0, 
                null
            );
            // In case I want to preserve igniter information
        }
        
        entity.setPosition(
            this.posX + Math.random() * 4.0 - 2.0,
            this.posY - 3.5,
            this.posZ + Math.random() * 4.0 - 2.0
        );

        // maybe impulse the tnt downwards 1.5F

        this.world.spawnEntity(entity);


    }


    @Override 
    public boolean isInRangeToRenderDist(double par1) {
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("drop", this.entityIndex);
        nbt.setFloat("tx", (float)this.tx);
        nbt.setFloat("ty", (float)this.ty);
        nbt.setFloat("tz", (float)this.tz);
        nbt.setInteger("age", this.tick_age);
        nbt.setInteger("health", this.health);
        nbt.setInteger("duration", this.timeLeft);
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.entityIndex = nbt.getInteger("drop");
        //this.carpet = this.entityIndex < ItemRoda.rodaindex;
        if (nbt.hasKey("tx")) {
            this.tx = nbt.getFloat("tx");
        }
        if (nbt.hasKey("ty")) {
            this.ty = nbt.getFloat("ty");
        }
        if (nbt.hasKey("tz")) {
            this.tz = nbt.getFloat("tz");
        }
        if (nbt.hasKey("age")) {
            this.tick_age = nbt.getInteger("age");
        }
        if (nbt.hasKey("health")) {
            this.health = nbt.getInteger("health");
        }
        if (nbt.hasKey("duration")) {
            this.timeLeft = nbt.getInteger("duration");
        }
        
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        super.attackEntityFrom(damageSource, amount);
        if (!this.isDead && !this.world.isRemote) {
            this.health = (int)(this.health - amount);
            if (this.health <= 0) {
                this.setDead();
                this.world.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 6.0F, true);
                this.world.spawnEntity(new EntityB2SpiritFrag(this.world, this, EntityB2SpiritFrag.FragType.LEFT));
                this.world.spawnEntity(new EntityB2SpiritFrag(this.world, this, EntityB2SpiritFrag.FragType.RIGHT));
                EntityZombie pz = new EntityZombie(this.world);
                pz.setPosition(this.posX, this.posY, this.posZ);
                pz.setCustomNameTag("Pilot");
                this.world.spawnEntity(pz);
                //RivalRebelsSoundPlayer.playSound(this, 0, 0, 30.0F, 1.0F); 30.0F seems very loud, I'll reduce it a bit
                this.playSound(RRSounds.b2_explode, 10.0F, 1.0F);
            }
        }

        return true;
    }

    @Override 
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }


    // This exists to solve this problem:
    // If I set the initial yaw on the server the client doesn't see the change
    // So the client incorrectly displays the play at 0 yaw for the initial leg of trip
    // But if I remove the remote check, since the tick_age only exists on the server,
    // The client's version is stuck at 0, so it runs this setheading method every tick
    // which causes further display issues for the movement after the initial leg of trip
    @Override
    public void writeSpawnData(ByteBuf buffer) {

        // Calculate heading before sending the spawn packet
        if (this.tick_age == 0) {
            this.setInitialHeading(this.posX, this.posZ);
        }

        buffer.writeFloat(this.rotationYaw);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {

        float yaw = buffer.readFloat();

        this.rotationYaw = yaw;
        this.prevRotationYaw = yaw;
    }

    @Override
    protected void entityInit() {
    }
}
