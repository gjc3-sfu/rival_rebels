package com.rivalrebels.common.items.weapons;

import java.util.HashSet;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.init.RRItems;
import com.rivalrebels.common.items.RRItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.util.text.translation.I18n;

public class M202 extends RRItem {

    boolean message = true;
    int shrink;

    public M202(String name) {
        super(name);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 144;
    }

    private boolean consumeRocket(EntityPlayer player, int amount) {
        for (ItemStack stack : player.inventory.mainInventory) {
            if (!stack.isEmpty() && stack.getItem() == RRItems.rocket) {
                int consumed = Math.min(amount, stack.getCount());
                stack.shrink(consumed);
                amount -= consumed;

                if (amount <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override 
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.capabilities.isCreativeMode || player.inventory.hasItemStack(new ItemStack(RRItems.rocket)) || RRConfigx.infinite_ammo)
        {
            player.setActiveHand(hand);

            /*if (stack.isItemEnchanted() && !world.isRemote)
            {
                par2World.spawnEntityInWorld(new EntityFlameBallGreen(par2World, par3EntityPlayer, (float) (Math.random() + 1.0f)));
            }*/
        }else if (!world.isRemote)
        {
            player.sendStatusMessage(new TextComponentString("§cOut of ammunition".substring(1)), true);
        }
        if (message && world.isRemote)
        {
            player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.Orders")+" "+I18n.translateToLocal("RivalRebels.message.use")+" [R]."), false);
            message = false;
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }


}
