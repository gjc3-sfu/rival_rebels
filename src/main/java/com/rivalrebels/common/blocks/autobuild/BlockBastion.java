package com.rivalrebels.common.blocks.autobuild;

import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBastion extends BlockAutoBuild {

    public BlockBastion(String name, Material material) {
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
            int r = 2;
            int h = 6;

            for (int y1 = 0; y1 <= h; y1++) {
                for (int x1 = -r; x1 <= r; x1++) {
                for (int z1 = -r; z1 <= r; z1++) {
                    if ((Math.abs(x1) != r && Math.abs(z1) != r || y1 == 3 && (Math.abs(x1) == 0 || Math.abs(z1) == 0)) && y1 != 0 && y1 != h) {
                        this.placeBlockCarefully(world, x + x1, y + y1, z + z1, Blocks.AIR, 2);
                    } else {
                        this.placeBlockCarefully(world, x + x1, y + y1, z + z1, RRBlocks.reactive);
                    }
                }
                }

                if (y1 != 3) {
                this.placeBlockCarefully(world, x + r + 1, y + y1, z, RRBlocks.reactive);
                this.placeBlockCarefully(world, x - r - 1, y + y1, z, RRBlocks.reactive);
                this.placeBlockCarefully(world, x, y + y1, z + r + 1, RRBlocks.reactive);
                this.placeBlockCarefully(world, x, y + y1, z - r - 1, RRBlocks.reactive);
                } else {
                for (int z1 = -r; z1 <= r; z1++) {
                    this.placeBlockCarefully(world, x, y + y1, z + z1, RRBlocks.conduit);
                }

                for (int x1 = -r; x1 <= r; x1++) {
                    this.placeBlockCarefully(world, x + x1, y + y1, z, RRBlocks.conduit);
                }
                }
            }
        }
    }
    
}
