package com.rivalrebels.common.blocks;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ConduitBlock extends RRBlock {

    public static final PropertyInteger ALTERNATE = PropertyInteger.create("alternate", 1, 9);

    public ConduitBlock(String name, Material material) {
        super(name, material);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(ALTERNATE, 1));
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.BLOCK;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(
                ALTERNATE, world.rand.nextInt(9) + 1);
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ALTERNATE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(
                ALTERNATE, MathHelper.clamp(meta, 1, 9));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ALTERNATE);
    }

    
}
