package com.rivalrebels.common.tileentity;

import com.rivalrebels.common.blocks.trap.TimedBomb;
import com.rivalrebels.common.config.RRConfigx;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTimedBomb extends TileEntity implements ITickable {
    private static final String FUSE_TICKS_ELAPSED_TAG = "FuseTicksElapsed";
    private static final int BLINK_HALF_PERIOD_TICKS = 8;

    private int fuseTicksElapsed = -1;
    private boolean defusing;
    private boolean beingBrokenByPlayer;
    private boolean detonating;

    @Override
    public void update() {
        if (world == null) {
            return;
        }

        IBlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof TimedBomb)) {
            return;
        }

        if (fuseTicksElapsed < 0) {
            fuseTicksElapsed = 0;
        }

        boolean lit = (fuseTicksElapsed / BLINK_HALF_PERIOD_TICKS) % 2 == 0;
        if (state.getValue(TimedBomb.LIT) != lit) {
            world.setBlockState(pos, state.withProperty(TimedBomb.LIT, lit), 2);
            if (!world.isRemote) {
                world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS,
                        1.0F, lit ? 1.0F : 0.7F);
            }
        }

        fuseTicksElapsed++;
        if (fuseTicksElapsed % BLINK_HALF_PERIOD_TICKS == 0) {
            markDirty();
        }

        if (fuseTicksElapsed >= RRConfigx.timed_bomb_fuse_seconds * 20) {
            ((TimedBomb) state.getBlock()).explode_fast(world, pos);
        }
    }

    public boolean isDefusing() {
        return defusing;
    }

    public void setDefusing(boolean defusing) {
        this.defusing = defusing;
    }

    public boolean isBeingBrokenByPlayer() {
        return beingBrokenByPlayer;
    }

    public void setBeingBrokenByPlayer(boolean beingBrokenByPlayer) {
        this.beingBrokenByPlayer = beingBrokenByPlayer;
    }

    public boolean isDetonating() {
        return detonating;
    }

    public void setDetonating(boolean detonating) {
        this.detonating = detonating;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(FUSE_TICKS_ELAPSED_TAG, fuseTicksElapsed);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fuseTicksElapsed = compound.hasKey(FUSE_TICKS_ELAPSED_TAG)
                ? compound.getInteger(FUSE_TICKS_ELAPSED_TAG)
                : -1;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
