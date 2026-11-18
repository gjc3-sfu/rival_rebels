package com.rivalrebels.common.items;

import net.minecraft.util.math.MathHelper;

public class RRItemCore extends RRItem {

    final public float EFFICIENCY;
    final public int DENOMINATOR;

    public RRItemCore(String name) {
        super(name);
        this.maxStackSize = 1;
        this.EFFICIENCY = 0.25f;
        this.DENOMINATOR = 4;
    }

    public RRItemCore(String name, float efficiency, int denominator) {
        super(name);
        this.maxStackSize = 1;
        this.EFFICIENCY = efficiency;
        this.DENOMINATOR = denominator;
    }



}
