package com.rivalrebels.common.container;

import com.rivalrebels.common.items.RRItemCore;
import com.rivalrebels.common.items.RRItemRod;
import com.rivalrebels.common.tileentity.ReactorTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ReactorContainer extends Container {

    public RRSlot fuel;
    public RRSlot core;

    public ReactorTileEntity reactor;
    

    public ReactorContainer(IInventory playerInventory, IInventory par2iInventory) {
        this.reactor = (ReactorTileEntity)par2iInventory;
        this.fuel = new RRSlot(reactor, 0, 58, 139, RRItemRod.class);
        this.core = new RRSlot(reactor, 1, 103, 139, RRItemCore.class);

        this.addSlotToContainer(this.fuel);
        this.addSlotToContainer(this.core);
        this.bindPlayerInventory(playerInventory);
    }

    @Override 
    public boolean canInteractWith(EntityPlayer player) {
        return reactor.isUsableByPlayer(player);
    }

    protected void bindPlayerInventory(IInventory inventoryPlayer) {
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 172));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack remaining = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack sourceStack = slot.getStack();
            remaining = sourceStack.copy();

            if (index < 2) {
                if (!mergeItemStack(sourceStack, 2, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!mergeItemStack(sourceStack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (sourceStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return remaining;
    }
    
}
