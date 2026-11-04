package com.rivalrebels.common.items;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.entity.EntityPlasmoid;
import com.rivalrebels.common.init.RRConfigOptions;
import com.rivalrebels.common.init.RRItems;
import com.rivalrebels.common.init.RRSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.HashSet;

public class PlasmaCannonItem extends ItemTool implements IHasModel {
    public PlasmaCannonItem(String name) {
        super(ToolMaterial.DIAMOND, new HashSet<>());
        setUnlocalizedName(name);
        setRegistryName(name);
        maxStackSize = 1;
        attackSpeed = 0.52F;
        RivalRebels.model_items.add(this);
        RivalRebels.other_items.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        boolean has_hydrogen = player.inventory.mainInventory.stream().anyMatch(s -> !s.isEmpty() && s.getItem() == RRItems.hydro_rod);
        if (!player.capabilities.isCreativeMode && !has_hydrogen && !RRConfigOptions.infinite_ammo) {
            if (!world.isRemote) {
                player.sendStatusMessage(new TextComponentString("§cOut of Hydrogen".substring(1)), true);
            }
        } else {
            player.setActiveHand(hand);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 64;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override 
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
        World world = entity.world;

        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (!player.capabilities.isCreativeMode && !RRConfigOptions.infinite_ammo) {

                if (!consumeHydrogen(player, 1)) {
                    player.stopActiveHand();
                    return;
                }
            }

        }
    }


    private boolean consumeHydrogen(EntityPlayer player, int uses) {
        
        for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack stack = player.inventory.mainInventory.get(i);

            if (!stack.isEmpty() && stack.getItem() == RRItems.hydro_rod) {
                int durability_left = RRItemHydrogen.MAX_DURABILITY - stack.getMetadata();
                if (durability_left < uses) {
                    uses = uses - durability_left;


                    player.inventory.decrStackSize(i, 1);
                    player.inventory.addItemStackToInventory(new ItemStack(RRItems.rod, 1));

                } else {
                    stack.setItemDamage(stack.getMetadata() + uses);
                    uses = 0;
                }
            }

            if (uses <= 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase player, int timeLeft) {
        if (!player.world.isRemote)
        {
            float charge_power = (getMaxItemUseDuration(stack) - timeLeft) / 20.0F;
            charge_power = (charge_power * charge_power + charge_power * 2) * 0.3333f;
            if (charge_power > 1.0F) charge_power = 1.0F;
            charge_power+=0.2f;
            worldIn.playSound(null, player.posX, player.posY, player.posZ, RRSounds.plasma_fire, SoundCategory.AMBIENT, 1, 1);
            Entity entity = new EntityPlasmoid(player.world, (EntityPlayer) player, charge_power+0.5f, false);
            player.world.spawnEntity(entity);
        }
    }

    @Override
    public String getModelName() {
        return ModInfo.modid + ":plasma_cannon";
    }

}
