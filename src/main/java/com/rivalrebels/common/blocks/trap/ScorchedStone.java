package com.rivalrebels.common.blocks.trap;

import com.rivalrebels.common.blocks.RRBlock;
import com.rivalrebels.common.init.RivalRebelsDamageSource;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScorchedStone extends RRBlock {

    public static final PropertyInteger SCORCH = PropertyInteger.create("scorch", 0, 15);
    //public static final PropertyDirection FACING =
            //PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public ScorchedStone(String name) {
        super(name, Material.ROCK);
            
        this.setDefaultState(
            this.blockState.getBaseState()
                .withProperty(SCORCH, 7)
        );
    }

    public ScorchedStone(String name, Material mat) {
        super(name, mat);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entityIn) {
        int scorch = world.getBlockState(pos).getValue(SCORCH);
        DamageSource damage = new RivalRebelsDamageSource(RivalRebelsDamageSource.RR_damage_sources.RADIATION, null, null);
        
        float amount =
            (16 - scorch) / 2
            + world.rand.nextInt(3) - 1;
            
        entityIn.attackEntityFrom(damage, amount);


        super.onEntityWalk(world, pos, entityIn);
    }

    @Override
    public boolean onBlockActivated(
            World world,
            BlockPos pos,
            IBlockState state,
            EntityPlayer player,
            EnumHand hand,
            EnumFacing facing,
            float hitX,
            float hitY,
            float hitZ) {

        if (player.capabilities.isCreativeMode) {
            if (!world.isRemote) {
                int scorch = state.getValue(SCORCH);
                int nextScorch = (scorch + 1) % 16;

                world.setBlockState(
                    pos,
                    state.withProperty(SCORCH, nextScorch),
                    3
                );
            }

            return true;
        }

        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SCORCH);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SCORCH, meta & 15);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SCORCH);
    }
    
}
