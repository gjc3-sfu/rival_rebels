package com.rivalrebels.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LaptopBlock extends RRBlockContainer {

	public LaptopBlock(String name, Material material) {
		super(name, material);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createNewTileEntity'");
	}
    
}
