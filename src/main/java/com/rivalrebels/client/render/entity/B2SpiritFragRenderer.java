package com.rivalrebels.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.objloaders.ModelFromObj;
import com.rivalrebels.client.oldstuff.AdvancedModelLoader;
import com.rivalrebels.client.oldstuff.IModelCustom;
import com.rivalrebels.common.entity.EntityB2SpiritFrag;
import com.rivalrebels.common.entity.EntityB2SpiritFrag.FragType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class B2SpiritFragRenderer extends Render<EntityB2SpiritFrag> {
    public static ResourceLocation FragLeftmodelLoc = new ResourceLocation(ModInfo.modid, "models/obj/b2_spirit_fragment_left.obj");
    public static ResourceLocation FragRightmodelLoc = new ResourceLocation(ModInfo.modid, "models/obj/b2_spirit_fragment_right.obj");
    public IModelCustom FragLeftmodell = AdvancedModelLoader.loadModel(FragLeftmodelLoc);
    public IModelCustom FragRightmodell = AdvancedModelLoader.loadModel(FragRightmodelLoc);

    ModelFromObj fragment_left;
    ModelFromObj fragment_right;
    private static final ResourceLocation b2_texture = new ResourceLocation(ModInfo.modid, "textures/models/b2_spirit.png");




    public B2SpiritFragRenderer(RenderManager renderManager) {
        super(renderManager);
        try {
            //this.fragment_left = ModelFromObj.readObjFile("f.obj");
            //this.fragment_left.scale(3.0F, 3.0F, 3.0F);
            //this.fragment_right = ModelFromObj.readObjFile("g.obj");
            //this.fragment_right.scale(3.0F, 3.0F, 3.0F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderB2Frag(EntityB2SpiritFrag b2spirit, double x, double y, double z, float par8, float par9) {
        GL11.glEnable(2896);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(b2spirit.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(b2spirit.rotationPitch, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(b2_texture);
        GL11.glDisable(2884);
        GL11.glScalef(3.0F, 3.0F, 3.0F);
        if (b2spirit.getType() == FragType.LEFT) {
            //this.fragment_left.render();
            
            FragLeftmodell.renderAll();
        }

        if (b2spirit.getType() == FragType.RIGHT) {
            //this.fragment_right.render();
            FragRightmodell.renderAll();
        }

        GL11.glPopMatrix();
    }

    @Override 
    public void doRender(EntityB2SpiritFrag par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderB2Frag((EntityB2SpiritFrag)par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityB2SpiritFrag entity) {
        return null;
    }
}