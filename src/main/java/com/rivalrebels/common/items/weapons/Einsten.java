package com.rivalrebels.common.items.weapons;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.entity.EntityLaserBurst;
import com.rivalrebels.common.init.RRItems;
import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.items.RRItem;
import com.rivalrebels.common.items.RRItemHydrogen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class Einsten extends RRItem {

    private boolean sound_alternate = false;

    public Einsten(String name) {
        super(name);
    }
    
    @Override
    public int getItemEnchantability() {
        return 100;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        boolean has_redstone = player.inventory.mainInventory.stream().anyMatch(s -> !s.isEmpty() && s.getItem() == RRItems.redstone_rod);
        ItemStack stack = player.getHeldItem(hand);
        if (player.capabilities.isCreativeMode || has_redstone || RRConfigx.infinite_ammo)
        {
            player.setActiveHand(hand);


        } else if (!world.isRemote) {
            player.sendStatusMessage(new TextComponentString("§cOut of redstone rods".substring(1)), true);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    private boolean consumeRedstone(EntityPlayer player, int uses) {
        for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack stack = player.inventory.mainInventory.get(i);

            if (!stack.isEmpty() && stack.getItem() == RRItems.redstone_rod) {
                int durability_left = RRItemHydrogen.MAX_DURABILITY - stack.getMetadata();
                if (durability_left < uses) {
                    uses = uses - durability_left;


                    player.inventory.decrStackSize(i, 1);
                    player.inventory.addItemStackToInventory(new ItemStack(RRItems.empty_rod, 1));

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

    public void onUsingTick(ItemStack is, EntityPlayer player, int count) {
        if (count < 71980 && !player.world.isRemote) {
            consumeRedstone(player, 1);
            if (this.sound_alternate) {

                player.world.playSound(null, player.posX, player.posY, player.posZ, RRSounds.einsten_fire_A, SoundCategory.AMBIENT, 0.5F, 0.3F);

            } else {
                player.world.playSound(null, player.posX, player.posY, player.posZ, RRSounds.einsten_fire_B, SoundCategory.AMBIENT, 0.4F, 1.7F);
            }

            this.sound_alternate = !this.sound_alternate;
            player.world.spawnEntity(new EntityLaserBurst(player.world, player, is.getEnchantmentTagList() != null && is.getEnchantmentTagList().tagCount() > 0));
        } else if (player.world.isRemote) {
            is.setRepairCost(2000 - count + 1);
        }
    }
}
