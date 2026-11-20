package com.rivalrebels.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RRSlot extends Slot {

    private final Class<? extends Item> allowed_item;
    public boolean locked = false;

    public RRSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Class<? extends Item> allowed_item) {
        super(inventoryIn, index, xPosition, yPosition);
        this.allowed_item = allowed_item;
    }

    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return !this.locked;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (this.locked) {
            return false;
        }

        return !stack.isEmpty() && allowed_item.isInstance(stack.getItem());
    }
    
}
