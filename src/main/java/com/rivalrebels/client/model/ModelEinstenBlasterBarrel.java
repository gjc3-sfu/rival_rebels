package com.rivalrebels.client.model;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.client.renderhelper.TextureVertice;
import com.rivalrebels.client.renderhelper.Vertice;

public class ModelEinstenBlasterBarrel {
    private float i = 0.035714287F;
    private float[] barrelx = new float[]{0.0F, 0.2F, 0.2F, 0.25F, 0.25F, 0.2F, 0.0F};
    private float[] barrely = new float[]{0.0F, 0.0F, 0.2F, 0.2F, 0.0F, -0.1F, -0.1F};
    private float[] tsart = new float[]{this.i * 28.0F, this.i * 24.0F, this.i * 16.0F, this.i * 15.0F, this.i * 7.0F, this.i * 4.0F, this.i * 3.0F, 0.0F};
    private int segments = 8;
    private float deg = (float) (Math.PI * 2) / this.segments;
    private float sin = (float)Math.sin(this.deg);
    private float cos = (float)Math.cos(this.deg);
    private float add = (float)(360 / this.segments);

    public void render() {
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2884);

        for (float i = 0.0F; i < this.segments; i++) {
            GL11.glPushMatrix();
            GL11.glRotatef(this.add * i, 0.0F, 1.0F, 0.0F);

            for (int f = 1; f < this.barrelx.length; f++) {
                TextureVertice t1 = new TextureVertice(1.0F / this.segments * i, this.tsart[f]);
                TextureVertice t2 = new TextureVertice(1.0F / this.segments * i, this.tsart[f - 1]);
                TextureVertice t3 = new TextureVertice(1.0F / this.segments * (i + 1.0F), this.tsart[f - 1]);
                TextureVertice t4 = new TextureVertice(1.0F / this.segments * (i + 1.0F), this.tsart[f]);
                RenderHelper.addFace(
                new Vertice(0.0F, this.barrely[f], this.barrelx[f]),
                new Vertice(0.0F, this.barrely[f - 1], this.barrelx[f - 1]),
                new Vertice(this.barrelx[f - 1] * this.sin, this.barrely[f - 1], this.barrelx[f - 1] * this.cos),
                new Vertice(this.barrelx[f] * this.sin, this.barrely[f], this.barrelx[f] * this.cos),
                t1,
                t2,
                t3,
                t4
                );
            }

            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
    }
}