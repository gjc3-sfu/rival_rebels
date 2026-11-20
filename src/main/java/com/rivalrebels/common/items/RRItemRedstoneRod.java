package com.rivalrebels.common.items;

public class RRItemRedstoneRod extends RRItemRod {

    public static final int MAX_DURABILITY = 256;
    private static final int MAX_POWER = 250000;

    public RRItemRedstoneRod(String name) {
        super(name, MAX_DURABILITY);
    }

    @Override 
    public int get_max_power() {
        return MAX_POWER;
    }

}
