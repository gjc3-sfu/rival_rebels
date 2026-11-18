package com.rivalrebels.common.blocks.trap;

import com.rivalrebels.common.blocks.RRBlock;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.explosion.RivalRebelsExplosion;
import com.rivalrebels.common.init.RivalRebelsDamageSource;
import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.tileentity.TileEntityTimedBomb;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TimedBomb extends RRBlock {
    public static final PropertyBool LIT = PropertyBool.create("lit");

    public TimedBomb(String name, Material material) {
        super(name, material);
        setDefaultState(blockState.getBaseState().withProperty(LIT, false));
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTimedBomb();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = world.getTileEntity(pos);
        boolean shouldExplode = !(tileEntity instanceof TileEntityTimedBomb)
                || !((TileEntityTimedBomb) tileEntity).isDefusing()
                && !((TileEntityTimedBomb) tileEntity).isBeingBrokenByPlayer()
                && !((TileEntityTimedBomb) tileEntity).isDetonating();

        super.breakBlock(world, pos, state);

        if (shouldExplode) {
            explode_server(world, pos);
        }
    }

    public void explode_server(World world, BlockPos pos) {
        explode_fast(world, pos);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
            boolean willHarvest) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityTimedBomb) {
            ((TileEntityTimedBomb) tileEntity).setBeingBrokenByPlayer(true);
        }

        boolean removed = super.removedByPlayer(state, world, pos, player, willHarvest);
        if (!removed && tileEntity instanceof TileEntityTimedBomb) {
            ((TileEntityTimedBomb) tileEntity).setBeingBrokenByPlayer(false);
        }
        return removed;
    }

    public boolean defuse(World world, BlockPos pos) {
        if (world.isRemote) {
            return false;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityTimedBomb)) {
            return false;
        }

        ((TileEntityTimedBomb) tileEntity).setDefusing(true);
        world.setBlockToAir(pos);
        return true;
    }

    public void explode_fast(World world, BlockPos pos) {
        if (world.isRemote) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityTimedBomb) {
            TileEntityTimedBomb timedBomb = (TileEntityTimedBomb) tileEntity;
            if (timedBomb.isDetonating()) {
                return;
            }
            timedBomb.setDetonating(true);
        }

        explode(world, pos);
    }

    private void explode(World world, BlockPos pos) {
        if (world.isRemote) {
            return;
        }
        world.setBlockToAir(pos);
        world.playSound(null, pos, RRSounds.generic_explosion, SoundCategory.AMBIENT, 1.0F, 0.3F);

        DamageSource damage = new DamageSource(
            "explosion").setExplosion();
        new RivalRebelsExplosion(
                world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                RRConfigx.timed_bomb_explosion_size,
                false, true, damage);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
        //TODO: decide if we want to make creative players able to destroy these without exploding
        explode_fast(world, pos);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        explode_fast(world, pos);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LIT, (meta & 1) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LIT) ? 1 : 0;
    }
}
