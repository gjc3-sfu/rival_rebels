package com.rivalrebels.common.blocks;

import com.rivalrebels.RivalRebels;
import com.rivalrebels.common.tileentity.ReactorTileEntity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class ReactorBlock extends RRBlockContainer {

    public ReactorBlock(String name, Material material) {
        super(name, material);
        //TODO Auto-generated constructor stub
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ReactorTileEntity();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if(!playerIn.isSneaking()){
            FMLNetworkHandler.openGui(playerIn, RivalRebels.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    /*
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (!par1World.isRemote) {
            FMLNetworkHandler.openGui(par5EntityPlayer, RivalRebels.instance, 0, par1World, par2, par3, par4);
        }

        RivalRebelsSoundPlayer.playSound(par1World, 10, 3, par2, par3, par4);
        return true;
    }
    */
    
    
}
