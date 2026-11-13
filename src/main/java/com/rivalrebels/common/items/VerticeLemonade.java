package com.rivalrebels.common.items;

import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.init.RivalRebelsDamageSource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class VerticeLemonade extends RRItem{

    private final boolean EXPERIMENTAL;

    public VerticeLemonade(String name, boolean is_experimental) {
        super(name);
        this.EXPERIMENTAL = is_experimental;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        player.setActiveHand(handIn);


        float pitch = EXPERIMENTAL ? 0.6f : 1f;
        world.playSound(player, player.posX, player.posY, player.posZ, RRSounds.gulp, SoundCategory.AMBIENT, 1f, 1f);
        world.playSound(player, player.posX, player.posY, player.posZ, RRSounds.toyguy, SoundCategory.AMBIENT, 1, pitch);
        world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.AMBIENT, 1.0F, 1.0F);
        world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_HURT, SoundCategory.MASTER, 1.0F, 1.0F);


        if (!world.isRemote)
        {

            stack.shrink(1);
            this.consume(world, player, EXPERIMENTAL);
            if(handIn == EnumHand.OFF_HAND){
                player.sendStatusMessage(new TextComponentString("you have reached a secret message, that is all"), false);
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    private void consume(World world, EntityLivingBase entity, boolean experimental) {

        //float pitch = EXPERIMENTAL ? 0.6f : 1f;

        if (!EXPERIMENTAL) {

            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.pill.success")), false);
            }
           
            entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10, 20));
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                player.getFoodStats().addStats(10, 200);
            }
            entity.heal(10);

        } else {

            int rand = world.rand.nextInt(100);

            if (rand < 20) {
                // 20% - Unexpected results
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.experimental_pill.unexpected_results")), false);
                }
                entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1500, 0)); // nausea lvl 21 doesn't do anything special
                entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 1500, 0)); //' ' '
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1500, 6)); // cannot move without jumping
                entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1500, 20)); // basically cannot dig
                entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1500, 20)); // cannot do melee damage save for some extreme beefed up modded weapon
                // no healing

            } else if (rand < 30) {
                // 10% - Test subject perished
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.experimental_pill.perished")), false);
                }
                RivalRebelsDamageSource damage = new RivalRebelsDamageSource(RivalRebelsDamageSource.RR_damage_sources.CYANIDE, null, null);
                entity.attackEntityFrom(damage, 2000.0F);
            } else if (rand < 40) {
                // 10% - Uber-Soldat
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.experimental_pill.super")), false);
                }
                entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 450, 20)); // Invincibility
                entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 500, 20));
                entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 300, 2));
                entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 550, 2));
                entity.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 800, 0));
                entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 800, 0));

                entity.heal(20.0F);
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    player.getFoodStats().addStats(10, 200);
                }
            
            } else {
                // 60% - Experiment success
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.experimental_pill.success")), false);
                }
                entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10, 20));
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    player.getFoodStats().addStats(20, 200);
                }
                entity.heal(20);

            }







        }
        
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

}
