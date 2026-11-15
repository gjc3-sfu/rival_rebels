
package com.rivalrebels.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RRItems;

public class BlockFlag extends RRBlock {

    public static final PropertyDirection FACING =
            PropertyDirection.create("facing",
                    EnumFacing.Plane.HORIZONTAL);

    private static final AxisAlignedBB NORTH_AABB =
            new AxisAlignedBB(0, 0, 0, 1, 1, 0.0625);

    private static final AxisAlignedBB SOUTH_AABB =
            new AxisAlignedBB(0, 0, 0.9375, 1, 1, 1);

    private static final AxisAlignedBB EAST_AABB =
            new AxisAlignedBB(0.9375, 0, 0, 1, 1, 1);

    private static final AxisAlignedBB WEST_AABB =
            new AxisAlignedBB(0, 0, 0, 0.0625, 1, 1);


    public BlockFlag(String name, Material mat) {
        super(name, mat);

        this.setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(FACING, EnumFacing.NORTH)
        );
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta) {
            case 1: return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
            case 2: return getDefaultState().withProperty(FACING, EnumFacing.WEST);
            case 4: return getDefaultState().withProperty(FACING, EnumFacing.NORTH);
            case 8: return getDefaultState().withProperty(FACING, EnumFacing.EAST);
            default: return getDefaultState();
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        switch (state.getValue(FACING)) {
            case SOUTH: return 1;
            case WEST: return 2;
            case NORTH: return 4;
            case EAST: return 8;
            default: return 0;
        }
    }

    @Override
    public IBlockState getStateForPlacement(
        World world,
        BlockPos pos,
        EnumFacing facing,
        float hitX,
        float hitY,
        float hitZ,
        int meta,
        EntityLivingBase placer) {

        switch (facing) {
            case UP:
            case DOWN:
                return null;

            case NORTH:
                return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
            case SOUTH:
                return getDefaultState().withProperty(FACING, EnumFacing.NORTH);
            case WEST:
                return getDefaultState().withProperty(FACING, EnumFacing.EAST);
            case EAST:
                return getDefaultState().withProperty(FACING, EnumFacing.WEST);
            default:
                return getDefaultState();
        }
    }   

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(
            IBlockState state,
            IBlockAccess world,
            BlockPos pos) {

        switch (state.getValue(FACING)) {
            case NORTH: return NORTH_AABB;
            case SOUTH: return SOUTH_AABB;
            case EAST: return EAST_AABB;
            case WEST: return WEST_AABB;
            default: return FULL_BLOCK_AABB;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(
            IBlockState state,
            IBlockAccess world,
            BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
            BlockPos supportPos = pos.offset(facing);
            IBlockState supportState = world.getBlockState(supportPos);

            if (supportState.getBlock().isFullBlock(supportState)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasValidSupport(World world, BlockPos pos, IBlockState state) {
        EnumFacing facing = state.getValue(FACING);

        BlockPos supportPos = pos.offset(facing);
        IBlockState supportState = world.getBlockState(supportPos);

        return supportState.getBlock().isFullBlock(supportState);
    }

    @Override
    public void neighborChanged(
            IBlockState state,
            World world,
            BlockPos pos,
            Block neighborBlock,
            BlockPos fromPos) {

        if (!world.isRemote && !hasValidSupport(world, pos, state)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public Item getItemDropped(
            IBlockState state,
            Random random,
            int fortune) {

        return this == RRBlocks.flag_troll
                ? RRItems.troll_helmet
                : Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
