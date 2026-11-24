package com.rivalrebels.common.blocks;

import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public abstract class RRBlockContainer extends BlockContainer {
    public RRBlockContainer(String name, Material material){
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        RRBlocks.blockListx.add(this);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

}
