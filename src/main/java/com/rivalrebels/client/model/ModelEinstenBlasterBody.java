package com.rivalrebels.client.model;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.client.renderhelper.Vertice;

public class ModelEinstenBlasterBody {
    Vertice vx = new Vertice(1.0F, 0.0F, 0.0F).normalize();
    Vertice vy = new Vertice(0.0F, 1.0F, 0.0F).normalize();
    Vertice vz = new Vertice(0.0F, 0.0F, 1.0F).normalize();
    Vertice vxy = new Vertice(0.5F, 0.5F, 0.0F).normalize();
    Vertice vyz = new Vertice(0.0F, 0.5F, 0.5F).normalize();
    Vertice vxz = new Vertice(0.5F, 0.0F, 0.5F).normalize();
    Vertice vx1 = new Vertice(0.75F, 0.25F, 0.0F).normalize();
    Vertice vx2 = new Vertice(0.5F, 0.25F, 0.25F).normalize();
    Vertice vx3 = new Vertice(0.75F, 0.0F, 0.25F).normalize();
    Vertice vy1 = new Vertice(0.0F, 0.75F, 0.25F).normalize();
    Vertice vy2 = new Vertice(0.25F, 0.5F, 0.25F).normalize();
    Vertice vy3 = new Vertice(0.25F, 0.75F, 0.0F).normalize();
    Vertice vz1 = new Vertice(0.25F, 0.0F, 0.75F).normalize();
    Vertice vz2 = new Vertice(0.25F, 0.25F, 0.5F).normalize();
    Vertice vz3 = new Vertice(0.0F, 0.25F, 0.75F).normalize();

    public void render(float size, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glScalef(size, size, size);
        GL11.glDisable(3553);
        GL11.glDisable(2896);

        for (int p = 0; p < 4; p++) {
            GL11.glPushMatrix();
            GL11.glRotatef(p * 90, 0.0F, 1.0F, 0.0F);
            RenderHelper.addTri(this.vy, this.vy1, this.vy3);
            RenderHelper.addTri(this.vy1, this.vyz, this.vy2);
            RenderHelper.addTri(this.vy3, this.vy2, this.vxy);
            RenderHelper.addTri(this.vy1, this.vy2, this.vy3);
            RenderHelper.addTri(this.vx, this.vx1, this.vx3);
            RenderHelper.addTri(this.vx1, this.vxy, this.vx2);
            RenderHelper.addTri(this.vx3, this.vx2, this.vxz);
            RenderHelper.addTri(this.vx1, this.vx2, this.vx3);
            RenderHelper.addTri(this.vz, this.vz1, this.vz3);
            RenderHelper.addTri(this.vz1, this.vxz, this.vz2);
            RenderHelper.addTri(this.vz3, this.vz2, this.vyz);
            RenderHelper.addTri(this.vz1, this.vz2, this.vz3);
            RenderHelper.addTri(this.vyz, this.vz2, this.vy2);
            RenderHelper.addTri(this.vxy, this.vy2, this.vx2);
            RenderHelper.addTri(this.vxz, this.vx2, this.vz2);
            RenderHelper.addTri(this.vx2, this.vy2, this.vz2);
            GL11.glPopMatrix();
        }

        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
}

