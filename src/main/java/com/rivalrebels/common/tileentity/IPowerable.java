package com.rivalrebels.common.tileentity;

import net.minecraft.util.math.BlockPos;

public interface IPowerable {
    int get_max_power_storage();
    int get_power_contained();
    
    void set_power_contained(float new_power);
    void add_power(float added_power);


    void set_reactor_distance(float distance);


    void set_reactor_pos(BlockPos pos);
    BlockPos get_reactor_pos();

}
