package com.rivalrebels;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.rivalrebels.common.tileentity.*;

public class CommonProxy {
    public void registerRenderStuff(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TileNuke.class, new ResourceLocation(ModInfo.modid, "tile_nuke"));
        GameRegistry.registerTileEntity(TileLoader.class, new ResourceLocation(ModInfo.modid, "tile_loader"));
        GameRegistry.registerTileEntity(TileNukeCrate.class, new ResourceLocation(ModInfo.modid, "tile_nuke_crate"));
        GameRegistry.registerTileEntity(TileEntityPlasmaExplosion.class, new ResourceLocation(ModInfo.modid, "plasma_explosion"));
        GameRegistry.registerTileEntity(TileEntitySteel.class, new ResourceLocation(ModInfo.modid, "steel"));
        GameRegistry.registerTileEntity(MineTileEntity.class, new ResourceLocation(ModInfo.modid, "mine"));
        GameRegistry.registerTileEntity(TileMario.class, new ResourceLocation(ModInfo.modid, "mario"));
        GameRegistry.registerTileEntity(TileQuickSand.class, new ResourceLocation(ModInfo.modid, "quicksand"));
        GameRegistry.registerTileEntity(ReactorTileEntity.class, new ResourceLocation(ModInfo.modid, "reactor"));
        GameRegistry.registerTileEntity(TileEntityTimedBomb.class, new ResourceLocation(ModInfo.modid, "timed_bomb"));
    }
    public void registerModel(Item item, int meta, String variant) {}
    public void registerCustomModelName(Item item, String name, int meta, String variant){}
    public void closeGui(){}
    public void flamethrowerGui(int i){}
    public void teslaGui(int degree) {}
}
