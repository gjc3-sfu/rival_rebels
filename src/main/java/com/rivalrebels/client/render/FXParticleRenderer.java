package com.rivalrebels.client.render;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.client.oldstuff.Tessellator;
import com.rivalrebels.common.entity.EntityPropulsionFX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class FXParticleRenderer extends Render<EntityPropulsionFX> {
    public static ResourceLocation texture = new ResourceLocation(ModInfo.modid, "textures/entity/fire_particle.png");

    public FXParticleRenderer(RenderManager renderManager) {
        super(renderManager);
    }
    @Override
    public void doRender(EntityPropulsionFX entity, double par2, double par4, double par6, float par8, float par9) {
        if (entity.ticksExisted > 1) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);

            Tessellator var10 = Tessellator.instance;
            this.renderEntity(var10);
            GL11.glPopMatrix();
        }
    }

    private void renderEntity(Tessellator par1Tessellator) {
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV(0.0F - var8, 0.0F - var9, 0.0, 0.0, 0.0);
        par1Tessellator.addVertexWithUV(var7 - var8, 0.0F - var9, 0.0, 1.0, 0.0);
        par1Tessellator.addVertexWithUV(var7 - var8, var7 - var9, 0.0, 1.0, 1.0);
        par1Tessellator.addVertexWithUV(0.0F - var8, var7 - var9, 0.0, 0.0, 1.0);
        par1Tessellator.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPropulsionFX entity) {
        // TODO Auto-generated method stub
        return null;
    }
}
