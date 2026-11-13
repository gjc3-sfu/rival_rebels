package com.rivalrebels.common.blocks.autobuild;

import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockBunker extends BlockAutoBuild {

    public BlockBunker(String name, Material material) {
        super(name, material);
    }

    @Override
    public void build(World world, int x, int y, int z) {
        super.build(world, x, y, z);
        if (!world.isRemote) {
            int r = RRConfigx.bunker_radius;

            for (int x1 = -r; x1 <= r; x1++) {
                for (int y1 = 0; y1 <= 5; y1++) {
                    for (int z1 = -r; z1 <= r; z1++) {
                        this.placeBlockCarefully(world, x + x1, y + y1, z + z1, Blocks.AIR);
                    }
                }
            }

            for (int a = -r; a <= r; a++) {
                for (int c = -r; c <= r; c++) {
                this.placeBlockCarefully(world, x + a, y - 1, z + c, RRBlocks.smart_camo);
                if (a < -(r - 1) || c < -(r - 1) || a > r - 1 || c > r - 1) {
                    this.placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smart_camo);
                }
                }
            }

            y += 3;
            r -= 2;

            for (int a = -(r - 1); a <= r - 1; a++) {
                for (int c = -(r - 1); c <= r - 1; c++) {
                if (a == -(r - 1) && c == -(r - 1) || a == -(r - 1) && c == r - 1 || a == r - 1 && c == -(r - 1) || a == r - 1 && c == r - 1) {
                    this.placeBlockCarefully(world, x + a, y - 3, z + c, RRBlocks.smart_camo);
                    this.placeBlockCarefully(world, x + a, y - 2, z + c, RRBlocks.smart_camo);
                    this.placeBlockCarefully(world, x + a, y - 1, z + c, RRBlocks.smart_camo);
                }

                if (a < -(r - 2) || c < -(r - 2) || a > r - 2 || c > r - 2) {
                    this.placeBlockCarefully(world, x + a, y - 3, z + c, RRBlocks.smart_camo);
                    this.placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smart_camo);
                    this.placeBlockCarefully(world, x + a, y + 1, z + c, RRBlocks.smart_camo);
                }
                }
            }

            for (int a = -r; a <= r; a++) {
                for (int c = -r; c <= r; c++) {
                if (a < -(r - 1) || c < -(r - 1) || a > r - 1 || c > r - 1) {
                    this.placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smart_camo);
                    this.placeBlockCarefully(world, x + a, y - 3, z + c, RRBlocks.smart_camo);
                }
                }
            }

            r -= 2;

            for (int a = -r; a <= r; a++) {
                for (int c = -r; c <= r; c++) {
                this.placeBlockCarefully(world, x + a, y + 1, z + c, RRBlocks.smart_camo);
                this.placeBlockCarefully(world, x + a, y + 2, z + c, RRBlocks.smart_camo);
                }
            }

            y -= 3;
            r += 4;

            for (int a = -(r - 1); a <= r - 1; a++) {
                for (int c = -(r - 1); c <= r - 1; c++) {
                if (a < -(r - 2) || c < -(r - 2) || a > r - 2 || c > r - 2) {
                    this.placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smart_camo);
                    this.placeBlockCarefully(world, x + a, y + 1, z + c, RRBlocks.smart_camo);
                }
                }
            }

            int light_embeded_into_ceil_offset = 1;
            this.placeBlockCarefully(world, x - r + 5, y + 3 + light_embeded_into_ceil_offset, z, Blocks.GLOWSTONE);
            this.placeBlockCarefully(world, x + r - 5, y + 3 + light_embeded_into_ceil_offset, z, Blocks.GLOWSTONE);
            this.placeBlockCarefully(world, x, y + 3 + light_embeded_into_ceil_offset, z + r - 5, Blocks.GLOWSTONE);
            this.placeBlockCarefully(world, x, y + 3 + light_embeded_into_ceil_offset, z - r + 5, Blocks.GLOWSTONE);
        }
    }
    
}
