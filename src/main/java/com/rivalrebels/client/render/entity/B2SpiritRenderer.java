package com.rivalrebels.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.client.objloaders.ModelFromObj;
import com.rivalrebels.client.oldstuff.AdvancedModelLoader;
import com.rivalrebels.client.oldstuff.IModelCustom;
import com.rivalrebels.common.entity.EntityB2Spirit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class B2SpiritRenderer extends Render<EntityB2Spirit> {

    public static ResourceLocation b2_modelLoc = new ResourceLocation(ModInfo.modid, "models/obj/b2_spirit.obj");
    public IModelCustom b2_model = AdvancedModelLoader.loadModel(b2_modelLoc);

    ModelFromObj b2;
    public static ModelFromObj shuttle;
    public static ModelFromObj tupolev;

    private static final ResourceLocation b2_texture = new ResourceLocation(ModInfo.modid, "textures/models/b2_spirit.png");

    public B2SpiritRenderer(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.0F; // no shadow
        try {
            //this.b2 = ModelFromObj.readObjFile("d.obj");
            //this.b2.scale(3.0F, 3.0F, 3.0F);


            //tupolev = ModelFromObj.readObjFile("tupolev.obj");
            //shuttle = ModelFromObj.readObjFile("shuttle.obj");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderB2Spirit(EntityB2Spirit b2spirit, double x, double y, double z, float par8, float par9) {
        GL11.glEnable(2896);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(b2spirit.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(b2spirit.rotationPitch, 1.0F, 0.0F, 0.0F);
        GL11.glDisable(2884);
        GL11.glScalef(3.0F, 3.0F, 3.0F);
        

        /* 
        if (RivalRebels.bombertype.equals("sh")) {
            GL11.glScalef(3.0F, 3.0F, 3.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
            shuttle.render();
        } else if (RivalRebels.bombertype.equals("tu")) {
            Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.ettupolev);
            tupolev.render();
        } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
            this.b2.render();
        }
        */
        Minecraft.getMinecraft().renderEngine.bindTexture(b2_texture);
        //this.b2.render();
        this.b2_model.renderAll();

        GL11.glPopMatrix();
    }

    public void doRender(EntityB2Spirit par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderB2Spirit((EntityB2Spirit)par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityB2Spirit entity) {
        return null;
    }
}