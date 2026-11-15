package com.rivalrebels.common.tileentity;

import com.rivalrebels.common.init.RRBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class TileMario extends TileEntity {
    public IBlockState getBlock(){
        IBlockState block = findBlock(new HashSet<BlockPos>());
        return block == null ? Blocks.GRAVEL.getDefaultState() : block;
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
        IBlockState[] n = new IBlockState[6];
        for (int i = 0; i < neighborPos.length; i++) {
            n[i] = world.getBlockState(neighborPos[i]);
        }

        int popularity1 = 0;
        int popularity2 = 0;
        IBlockState mode = Blocks.GRAVEL.getDefaultState();
        IBlockState array_item = null;
        for (int i = 0; i < 6; i++)
        {
            array_item = n[i];
            if (array_item == null || !array_item.isOpaqueCube() || array_item == RRBlocks.mine.getDefaultState() || array_item == RRBlocks.mario.getDefaultState() /*|| array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand*/) continue;
            for (IBlockState iBlockState : n) {
                if (array_item == iBlockState) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
            popularity1 = 0;
        }
        if (popularity2 > 0) return mode;

        for (int i = 0; i < n.length; i++) {
            if (n[i].getBlock() != RRBlocks.mario) continue;
            TileEntity neighbor = world.getTileEntity(neighborPos[i]);
            if (neighbor instanceof TileMario) {
                IBlockState neighborBlock = ((TileMario) neighbor).findBlock(visited);
                if (neighborBlock != null) return neighborBlock;
            }
        }
        return null;
    }
}
