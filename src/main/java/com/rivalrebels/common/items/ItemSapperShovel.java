package com.rivalrebels.common.items;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.rivalrebels.common.init.RRBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemSapperShovel extends RRToolItem {

    public ItemSapperShovel(String name) {
        super(name, 1.0F, -3.0F, ToolMaterial.IRON, createEffectiveBlocks());
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    private static Set<Block> createEffectiveBlocks() {
        return new HashSet<>(Arrays.asList(
                Blocks.STONE,
                Blocks.GRASS,
                Blocks.DIRT,
                Blocks.COBBLESTONE,
                Blocks.SAND,
                Blocks.GRAVEL,
                Blocks.SNOW,
                Blocks.SNOW_LAYER,
                Blocks.CLAY,
                Blocks.FARMLAND,
                Blocks.SOUL_SAND,
                Blocks.MYCELIUM,
                Blocks.CHEST,
                Blocks.REDSTONE_ORE,
                Blocks.IRON_ORE,
                Blocks.GOLD_ORE,
                Blocks.LAPIS_ORE,
                Blocks.DIAMOND_ORE,
                Blocks.EMERALD_ORE,
                Blocks.REDSTONE_BLOCK,
                Blocks.IRON_BLOCK,
                Blocks.GOLD_BLOCK,
                Blocks.LAPIS_BLOCK,
                Blocks.DIAMOND_BLOCK,
                Blocks.EMERALD_BLOCK,
                RRBlocks.reactive,
                RRBlocks.conduit,
                RRBlocks.tower_crate,
                RRBlocks.steel,
                RRBlocks.green_camo,
                RRBlocks.brown_camo,
                RRBlocks.grey_camo,
                RRBlocks.jumper,
                RRBlocks.mine,
                RRBlocks.mine_trap,
                RRBlocks.quicksand,
                RRBlocks.quicksand_trap,
                RRBlocks.mario,
                RRBlocks.mario_trap,
                RRBlocks.loader,
                RRBlocks.reactor));
    }
}
