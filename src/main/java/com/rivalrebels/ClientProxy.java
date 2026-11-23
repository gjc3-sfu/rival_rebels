package com.rivalrebels;

import com.rivalrebels.client.guiLoader.GuiFlamethrower;
import com.rivalrebels.client.guiLoader.GuiTesla;
import com.rivalrebels.client.render.*;
import com.rivalrebels.client.render.entity.B2SpiritFragRenderer;
import com.rivalrebels.client.render.entity.B2SpiritRenderer;
import com.rivalrebels.client.render.entity.RenderCuchillo;
import com.rivalrebels.client.render.entity.RenderDebris;
import com.rivalrebels.client.render.entity.RenderElectricityLink;
import com.rivalrebels.client.render.entity.RenderFlameBallHighTemp;
import com.rivalrebels.client.render.entity.RenderFlameBallLowTemp;
import com.rivalrebels.client.render.entity.RenderFlameBallMediumTemp;
import com.rivalrebels.client.render.items.RenderEinsten;
import com.rivalrebels.client.render.items.RenderFlamethrower;
import com.rivalrebels.client.render.items.RenderRocket;
import com.rivalrebels.common.entity.*;
import com.rivalrebels.common.init.RRItems;
import com.rivalrebels.common.tileentity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
    @Override
    public void registerRenderStuff(FMLPreInitializationEvent event) {
        super.registerRenderStuff(event);
        OBJLoader.INSTANCE.addDomain(ModInfo.modid);
        RRItems.battery.setTileEntityItemStackRenderer(new RenderBattery());
        RRItems.nuclear_rod.setTileEntityItemStackRenderer(new RenderNuclearRod());
        RRItems.empty_rod.setTileEntityItemStackRenderer(new RenderRod());
        RRItems.redstone_rod.setTileEntityItemStackRenderer(new RenderRedstoneRod());
        RRItems.hydro_rod.setTileEntityItemStackRenderer(new RenderHydrogenRod());
        RRItems.i_loader.setTileEntityItemStackRenderer(new RenderItemLoader());
        RRItems.plasma_cannon.setTileEntityItemStackRenderer(new RenderPlasmaCannon());
        RRItems.rocket.setTileEntityItemStackRenderer(new RenderRocketItem());
        RRItems.fuel.setTileEntityItemStackRenderer(new RenderFuel());
        RRItems.flamethrower.setTileEntityItemStackRenderer(new RenderFlamethrower());
        RRItems.einsten.setTileEntityItemStackRenderer(new RenderEinsten());
        RRItems.tesla.setTileEntityItemStackRenderer(new RenderTesla());
        RRItems.m202.setTileEntityItemStackRenderer(new RenderM202());


        ClientRegistry.bindTileEntitySpecialRenderer(TileNuke.class, new RenderNuke());

        ClientRegistry.bindTileEntitySpecialRenderer(TileLoader.class, new LoaderRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileNukeCrate.class, new NukeCrateRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlasmaExplosion.class, new PlasmaExplosionRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySteel.class, new SteelRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(MineTileEntity.class, new MineRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileMario.class, new RenderMario());
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuickSand.class, new RenderQuicksand());

        ClientRegistry.bindTileEntitySpecialRenderer(ReactorTileEntity.class, new ReactorRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityCuchillo.class, RenderCuchillo::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPlasmoid.class, RenderPlasmoid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDebris.class, RenderDebris::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlameBallLowTemp.class, RenderFlameBallLowTemp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlameBallMediumTemp.class, RenderFlameBallMediumTemp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlameBallHighTemp.class, RenderFlameBallHighTemp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGasGrenade.class, RenderGasGrenade::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricityLink.class, RenderElectricityLink::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricityRaytrace.class, RenderElectricityRaytrace::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, RenderRocket::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPropulsionFX.class, FXParticleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLaserBurst.class, RenderLaserBurst::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGore.class, RenderGore::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityB2Spirit.class, B2SpiritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityB2SpiritFrag.class, B2SpiritFragRenderer::new);
    }
    public void registerModel(Item item, int meta, String variant){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    @Override
    public void registerCustomModelName(Item item, String name, int meta, String variant) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, variant));
    }

    @Override
    public void closeGui() {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    @Override
    public void flamethrowerGui(int i) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiFlamethrower(i));
    }

    @Override
    public void teslaGui(int i) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiTesla(i));
    }
}
