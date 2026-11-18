package com.rivalrebels.common.tileentity;

import com.rivalrebels.common.init.RRBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class MineTileEntity extends TileEntity {
    private static final int[] SIDE_NEIGHBORS = {0, 1, 4, 5};
    private static final int[] VERTICAL_NEIGHBORS = {2, 3};

    public IBlockState getBlockState(){
        IBlockState block = findBlock(new HashSet<BlockPos>());
        return block == null ? Blocks.GRASS.getDefaultState() : block;
    }

    private IBlockState findBlock(Set<BlockPos> visited) {
        if (pos == null || world == null || !visited.add(pos)) return null;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos[] neighborPos = new BlockPos[] {
                new BlockPos(x + 1, y, z),
                new BlockPos(x - 1, y, z),
                new BlockPos(x, y + 1, z),
                new BlockPos(x, y - 1, z),
                new BlockPos(x, y, z + 1),
                new BlockPos(x, y, z - 1)
        };
        IBlockState[] n = new IBlockState[neighborPos.length];
        for (int i = 0; i < neighborPos.length; i++) {
            n[i] = world.getBlockState(neighborPos[i]);
        }

        IBlockState block = findAdjacentBlock(n, SIDE_NEIGHBORS);
        if (block != null) return block;

        block = findAdjacentMine(neighborPos, n, visited, SIDE_NEIGHBORS);
        if (block != null) return block;

        block = findAdjacentBlock(n, VERTICAL_NEIGHBORS);
        if (block != null) return block;

        return findAdjacentMine(neighborPos, n, visited, VERTICAL_NEIGHBORS);
    }

    private IBlockState findAdjacentBlock(IBlockState[] neighbors, int[] indices) {
        int popularity1 = 0;
        int popularity2 = 0;
        IBlockState mode = null;
        for (int index : indices) {
            IBlockState array_item = neighbors[index];
            if (array_item == null || !array_item.isOpaqueCube() || isMimicBlock(array_item)) continue;
            for (int neighborIndex : indices) {
                if (array_item == neighbors[neighborIndex]) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
            popularity1 = 0;
        }
        return popularity2 > 0 ? mode : null;

    }

    private IBlockState findAdjacentMine(BlockPos[] neighborPos, IBlockState[] neighbors, Set<BlockPos> visited, int[] indices) {
        for (int index : indices) {
            if (neighbors[index].getBlock() != RRBlocks.mine) continue;
            TileEntity neighbor = world.getTileEntity(neighborPos[index]);
            if (neighbor instanceof MineTileEntity) {
                IBlockState neighborBlock = ((MineTileEntity) neighbor).findBlock(visited);
                if (neighborBlock != null) return neighborBlock;
            }
        }
        return null;
    }

    private boolean isMimicBlock(IBlockState state) {
        return state.getBlock() == RRBlocks.mine
                || state.getBlock() == RRBlocks.mario
                || state.getBlock() == RRBlocks.quicksand;
    }
}
