package com.rivalrebels.common.container;

import com.rivalrebels.common.tileentity.ReactorTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ReactorContainer extends Container {

    public Slot fuel;
    public Slot core;

    public ReactorTileEntity reactor;
    

    public ReactorContainer(IInventory playerInventory, IInventory par2iInventory) {
        this.reactor = (ReactorTileEntity)par2iInventory;
        this.fuel = new Slot(reactor, 0, 58, 139);
        this.core = new Slot(reactor, 1, 103, 139);
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

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }
    
}
