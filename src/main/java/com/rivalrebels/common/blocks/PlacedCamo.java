package com.rivalrebels.common.blocks;

import java.util.Random;

import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class PlacedCamo extends RRBlock {

    public PlacedCamo(String name, Material material) {
        super(name, material);
        //TODO Auto-generated constructor stub
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(RRBlocks.smart_camo);
    }
    
}
