package com.rivalrebels.common.blocks.autobuild;

import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMineTrap extends BlockAutoBuild {

    public BlockMineTrap(String name, Material material) {
        super(name, material);
    }

    @Override
    public void build(World world, BlockPos pos) {
        build(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void build(World world, int x, int y, int z) {
        super.build(world, x, y, z);
        if (!world.isRemote) {
            this.placeBlockCarefully(world, x, y, z, Blocks.AIR);
            int r = 2;

            for (int z1 = -r; z1 <= r; z1++) {
                for (int x1 = -r; x1 <= r; x1++) {
                this.placeBlockCarefully(world, x + x1, y - 1, z + z1, RRBlocks.mine);
                }
            }
        }
    }
    
}
