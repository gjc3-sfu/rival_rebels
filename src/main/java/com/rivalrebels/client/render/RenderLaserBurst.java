package com.rivalrebels.client.render;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.oldstuff.Tessellator;
import com.rivalrebels.common.entity.EntityLaserBurst;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLaserBurst extends Render<EntityLaserBurst> {
    public RenderLaserBurst(RenderManager renderManager) {
        super(renderManager);
    }

    static float red = 1.0F;
    static float green = 0.0F;
    static float blue = 0.0F;

    public void renderLaserBurst(EntityLaserBurst ell, double x, double y, double z, float yaw, float pitch) {
        float radius = 0.12F;
        int distance = 4;
        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 1);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(ell.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-ell.rotationPitch, 1.0F, 0.0F, 0.0F);

        for (float o = 0.0F; o <= radius; o += radius / 8.0F) {
            float color = 1.0F - o * 8.333F;
            if (color < 0.0F) {
                color = 0.0F;
            }

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(red, color, color, 1.0F);
            tessellator.addVertex(0.0F + o, 0.0F - o, 0.0);
            tessellator.addVertex(0.0F + o, 0.0F + o, 0.0);
            tessellator.addVertex(0.0F + o, 0.0F + o, 0 + distance);
            tessellator.addVertex(0.0F + o, 0.0F - o, 0 + distance);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(red, color, color, 1.0F);
            tessellator.addVertex(0.0F - o, 0.0F - o, 0.0);
            tessellator.addVertex(0.0F + o, 0.0F - o, 0.0);
            tessellator.addVertex(0.0F + o, 0.0F - o, 0 + distance);
            tessellator.addVertex(0.0F - o, 0.0F - o, 0 + distance);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(red, color, color, 1.0F);
            tessellator.addVertex(0.0F - o, 0.0F + o, 0.0);
            tessellator.addVertex(0.0F - o, 0.0F - o, 0.0);
            tessellator.addVertex(0.0F - o, 0.0F - o, 0 + distance);
            tessellator.addVertex(0.0F - o, 0.0F + o, 0 + distance);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(red, color, color, 1.0F);
            tessellator.addVertex(0.0F + o, 0.0F + o, 0.0);
            tessellator.addVertex(0.0F - o, 0.0F + o, 0.0);
            tessellator.addVertex(0.0F - o, 0.0F + o, 0 + distance);
            tessellator.addVertex(0.0F + o, 0.0F + o, 0 + distance);
            tessellator.draw();
        }

        GL11.glDisable(3042);
        //GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public void doRender(EntityLaserBurst entityLaserBurst, double x, double y, double z, float yaw, float pitch) {
        this.renderLaserBurst((EntityLaserBurst)entityLaserBurst, x, y, z, yaw, pitch);
    }

    protected ResourceLocation getEntityTexture(EntityLaserBurst entity) {
        return null;
    }
}
