package com.rivalrebels.common.blocks.autobuild;

import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTower extends BlockAutoBuild {
    public BlockTower(String name, Material material) {
        super(name, material);
    }

    @Override
    public void build(World world, BlockPos pos) {
        build(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void build(World world, int x, int y, int z) {
        super.build(world, x, y, z);

        for (int y1 = 0; y1 <= 16; y1++) {
            this.placeBlockCarefully(world, x + 1, y + y1, z, RRBlocks.steel);
            this.placeBlockCarefully(world, x - 1, y + y1, z, RRBlocks.steel);
            this.placeBlockCarefully(world, x, y + y1, z + 1, RRBlocks.steel);
            this.placeBlockCarefully(world, x, y + y1, z - 1, RRBlocks.steel);
            this.placeBlockCarefully(world, x - 1, y + y1, z + 1, RRBlocks.steel);
            this.placeBlockCarefully(world, x + 1, y + y1, z - 1, RRBlocks.steel);
            this.placeBlockCarefully(world, x + 1, y + y1, z + 1, RRBlocks.steel);
            this.placeBlockCarefully(world, x - 1, y + y1, z - 1, RRBlocks.steel);
            this.placeBlockCarefully(world, x, y + y1, z, Blocks.AIR);
            if (y1 <= 1) {
                this.placeBlockCarefully(world, x + 1, y + y1, z, Blocks.AIR);
                this.placeBlockCarefully(world, x - 1, y + y1, z, Blocks.AIR);
                this.placeBlockCarefully(world, x, y + y1, z + 1, Blocks.AIR);
                this.placeBlockCarefully(world, x, y + y1, z - 1, Blocks.AIR);
            } else if (y1 == 8) {
                this.placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.NETHERRACK);
                this.placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.NETHERRACK);
                this.placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.NETHERRACK);
                this.placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.NETHERRACK);
            } else if (y1 == 11) {
                this.placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.NETHERRACK);
                this.placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.NETHERRACK);
                this.placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.NETHERRACK);
                this.placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.NETHERRACK);
            } else if (y1 == 9) {
                this.placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.AIR);
                this.placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.AIR);
                this.placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.AIR);
                this.placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.AIR);
            } else if (y1 == 10) {
                this.placeBlockCarefully(world, x - 1, y + y1, z, Blocks.AIR);
                this.placeBlockCarefully(world, x + 1, y + y1, z, Blocks.AIR);
                this.placeBlockCarefully(world, x, y + y1, z + 1, Blocks.AIR);
                this.placeBlockCarefully(world, x, y + y1, z - 1, Blocks.AIR);
            } else if (y1 == 12) {
                this.placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.AIR);
                this.placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.AIR);
                this.placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.AIR);
                this.placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.AIR);
            } else if (y1 == 16) {
                this.placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.LOG);
                this.placeBlockCarefully(world, x - 1, y + y1 + 1, z + 1, Blocks.REDSTONE_TORCH);
                this.placeBlockCarefully(world, x + 1, y + y1 + 1, z - 1, Blocks.REDSTONE_TORCH);
                this.placeBlockCarefully(world, x + 1, y + y1 + 1, z + 1, Blocks.REDSTONE_TORCH);
                this.placeBlockCarefully(world, x - 1, y + y1 + 1, z - 1, Blocks.REDSTONE_TORCH);
            }
        }

        this.placeBlockCarefully(world, x, y - 1, z, RRBlocks.jumper);
    }
}
