package com.rivalrebels.common.tileentity;

import com.rivalrebels.common.items.RRItemCore;
import com.rivalrebels.common.items.RRItemRod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ReactorTileEntity extends TileEntity implements IInventory {

    public ItemStack fuel = ItemStack.EMPTY;
    public ItemStack core = ItemStack.EMPTY;
    public boolean on = false;
    public double slide = 90.0;



    @Override
    public String getName() {
        return "Tokamak";
    }

    @Override
    public boolean hasCustomName() {
        return false; // maybe in the future write it to nbt
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return (this.core == null || this.core.isEmpty())
                && (this.fuel == null || this.fuel.isEmpty());
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index == 0) {
            return this.fuel == null ? ItemStack.EMPTY : this.fuel;
        }
        return index == 1 && this.core != null ? this.core : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < 0 || index >= this.getSizeInventory() || count <= 0) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = index == 0 ? this.fuel : this.core;
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack removed = stack.splitStack(count);
        if (stack.isEmpty()) {
            if (index == 0) {
                this.fuel = ItemStack.EMPTY;
            } else {
                this.core = ItemStack.EMPTY;
            }
        }
        this.markDirty();
        return removed;
    }


    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index < 0 || index >= this.getSizeInventory()) {
            return ItemStack.EMPTY;
        }

        ItemStack removed = this.getStackInSlot(index);
        if (removed.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (index == 0) {
            this.fuel = ItemStack.EMPTY;
        } else {
            this.core = ItemStack.EMPTY;
        }
        this.markDirty();
        return removed;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= this.getSizeInventory()) {
            return;
        }

        ItemStack stored = stack == null ? ItemStack.EMPTY : stack;
        if (!stored.isEmpty() && stored.getCount() > this.getInventoryStackLimit()) {
            stored.setCount(this.getInventoryStackLimit());
        }

        if (index == 0) {
            this.fuel = stored;
        } else {
            this.core = stored;
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null || stack.isEmpty() || index < 0 || index >= this.getSizeInventory()) {
            return false;
        }

        if (index == 0) {
            return stack.getItem() instanceof RRItemRod
                    && (this.fuel == null || this.fuel.isEmpty() || !this.on);
        }
        return stack.getItem() instanceof RRItemCore && !this.on;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.fuel = ItemStack.EMPTY;
        this.core = ItemStack.EMPTY;
        this.markDirty();
    }
    
}
