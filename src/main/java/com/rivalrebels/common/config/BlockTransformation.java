package com.rivalrebels.common.config;

import net.minecraft.block.Block;

public class BlockTransformation {
    public final Block inputBlock;
    public final Block outputBlock;
    public final int probability; // How likely the block is transformed

    public BlockTransformation(Block input, Block output, int resistance) {
        this.inputBlock = input;
        this.outputBlock = output;
        this.probability = resistance;
    }
}
