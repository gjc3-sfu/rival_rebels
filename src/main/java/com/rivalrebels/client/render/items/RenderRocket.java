package com.rivalrebels.client.render.items;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.client.model.ModelRocket;
import com.rivalrebels.common.entity.EntityRocket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRocket extends Render<EntityRocket> {

    ModelRocket roc_model = new ModelRocket();

    public static ResourceLocation texture = new ResourceLocation(ModInfo.modid, "textures/models/rocket.png");

    public RenderRocket(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderRocket(EntityRocket rocket, double par2, double par4, double par6, float par8, float par9) {
        GL11.glEnable(2896);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(rocket.rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rocket.rotationPitch - 90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(rocket.rotation, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.roc_model.render(rocket.fins);
        GL11.glPopMatrix();
    }

    public void doRender(EntityRocket par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderRocket((EntityRocket)par1Entity, par2, par4, par6, par8, par9);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRocket entity) {
        return null;
    }
}
