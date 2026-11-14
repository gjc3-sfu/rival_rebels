package com.rivalrebels.common.blocks.autobuild;

import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.core.BlackList;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RRSounds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockAutoBuild extends BlockFalling {

    public BlockAutoBuild(String name, Material material){
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        RRBlocks.blockListx.add(this);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    public int clicks = 15;

    public void build(World world, int x, int y, int z) {
        // Play build sound
        BlockPos pos = new BlockPos(x, y, z);
        world.playSound(null, pos, RRSounds.autobuild_deploy, SoundCategory.AMBIENT, 10f, 1f);
    }

    public void build(World world, BlockPos pos) {
        // Play build sound
        world.playSound(null, pos, RRSounds.autobuild_deploy, SoundCategory.AMBIENT, 10f, 1f);
    }

    protected void placeBlockCarefully(World world, int x, int y, int z, Block block) {
        BlockPos place_pos = new BlockPos(x, y, z);
        if (!RRConfigx.autobuild_unmutable.contains(world.getBlockState(place_pos).getBlock())) {
            world.setBlockState(place_pos, block.getDefaultState());
        }
    }

    protected void placeBlockCarefully(World world, int x, int y, int z, Block block, int update_flags) {
        BlockPos place_pos = new BlockPos(x, y, z);
        if (!RRConfigx.autobuild_unmutable.contains(world.getBlockState(place_pos).getBlock())) {
            world.setBlockState(place_pos, block.getDefaultState(), update_flags);
        }
    }

    @Override 
    public void onEndFalling(World world, BlockPos pos, IBlockState fallingState, IBlockState hitState) {
        if (!world.isRemote) {
            this.build(world, pos);
        }
    }
}
