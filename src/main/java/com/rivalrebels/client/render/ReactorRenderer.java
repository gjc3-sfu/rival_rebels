package com.rivalrebels.client.render;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.model.LaptopModel;
import com.rivalrebels.client.model.ReactorModel;
import com.rivalrebels.client.objloaders.ModelFromObj;
import com.rivalrebels.client.oldstuff.AdvancedModelLoader;
import com.rivalrebels.client.oldstuff.IModelCustom;
import com.rivalrebels.common.tileentity.ReactorTileEntity;
import com.rivalrebels.common.tileentity.TileEntityMachineBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ReactorRenderer extends TileEntitySpecialRenderer<ReactorTileEntity> {

    public static ResourceLocation laptop_texture = new ResourceLocation(ModInfo.modid, "textures/models/laptop.png");
    public static ResourceLocation screen_texture = new ResourceLocation(ModInfo.modid, "textures/models/reactor_screen.png");
    public static ResourceLocation reactor_texture = new ResourceLocation(ModInfo.modid, "textures/models/reactor.png");
    public static ResourceLocation electrode_texture = new ResourceLocation(ModInfo.modid, "textures/models/electrode.png");

    public static ResourceLocation electrodemodelLoc = new ResourceLocation(ModInfo.modid, "models/obj/electrode.obj");
    public IModelCustom electrodemodell = AdvancedModelLoader.loadModel(electrodemodelLoc);
    public ReactorModel reactormodell = new ReactorModel();

    private LaptopModel laptopmodell = new LaptopModel();
    
    public ReactorRenderer() {
        /* 
        try {
            this.mo = ModelFromObj.readObjFile("a.obj");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public void renderAModelAt(ReactorTileEntity tile, double d, double d1, double d2, float f) {
        GL11.glEnable(2896);
        int var9 = tile.getBlockMetadata();
        short var11 = 0;
        if (var9 == 2) {
            var11 = 180;
        }

        if (var9 == 3) {
            var11 = 0;
        }

        if (var9 == 4) {
            var11 = -90;
        }

        if (var9 == 5) {
            var11 = 90;
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.1875F, (float)d2 + 0.5F);
        GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(laptop_texture);
        laptopmodell.renderModel((float)(-tile.slide));
        Minecraft.getMinecraft().renderEngine.bindTexture(screen_texture);
        laptopmodell.renderScreen((float)(-tile.slide));
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.5F, (float)d2 + 0.5F);
        GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(reactor_texture);
        reactormodell.renderModel();
        Minecraft.getMinecraft().renderEngine.bindTexture(electrode_texture);
        GL11.glTranslatef(0.0F, 2.0F, -0.125F);
        GL11.glScalef(0.2F, 0.2F, 0.2F);
        electrodemodell.renderAll();
        GL11.glPopMatrix();

        /* 
        for (int i = 0; i < tile.machines.getSize(); i++) {
            TileEntityMachineBase temb = (TileEntityMachineBase)tile.machines.get(i);
            if (temb.powerGiven > 0.0F) {
                float radius = temb.powerGiven * temb.powerGiven / 40000.0F;
                radius = (float)(radius + 0.03);
                int steps = 2;
                if (radius > 0.05) {
                steps++;
                }

                if (radius > 0.1) {
                steps++;
                }

                if (radius > 0.15) {
                steps++;
                }

                if (radius > 0.25) {
                radius = 0.25F;
                }

                RenderLibrary.instance
                .renderModel(
                    (float)d + 0.5F,
                    (float)d1 + 2.5F,
                    (float)d2 + 0.5F,
                    temb.xCoord - tile.xCoord,
                    temb.yCoord - tile.yCoord - 2.5F,
                    temb.zCoord - tile.zCoord,
                    0.5F,
                    radius,
                    steps,
                    temb.edist / 2.0F,
                    0.1F,
                    0.45F,
                    0.45F,
                    0.5F,
                    0.5F
                );
            }
        }
        */
    }

    @Override
    public boolean isGlobalRenderer(ReactorTileEntity tile) {
        return true;
    }

    @Override
    public void render(ReactorTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        this.renderAModelAt(tile, x, y, z, partialTicks);
    }



}
