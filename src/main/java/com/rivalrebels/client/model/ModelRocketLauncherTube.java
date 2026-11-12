package com.rivalrebels.client.model;

import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.client.renderhelper.Vertice;

import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ModelRocketLauncherTube {
    Vertice vy1 = new Vertice(0.0F, 0.0F, 0.0F);
    Vertice vpx1 = new Vertice(0.5F, 0.0F, 0.0F);
    Vertice vnx1 = new Vertice(-0.5F, 0.0F, 0.0F);
    Vertice vpz1 = new Vertice(0.0F, 0.0F, 0.5F);
    Vertice vnz1 = new Vertice(0.0F, 0.0F, -0.5F);
    Vertice vpxpz1 = new Vertice(0.3535533F, 0.0F, 0.3535533F);
    Vertice vpxnz1 = new Vertice(0.3535533F, 0.0F, -0.3535533F);
    Vertice vnxpz1 = new Vertice(-0.3535533F, 0.0F, 0.3535533F);
    Vertice vnxnz1 = new Vertice(-0.3535533F, 0.0F, -0.3535533F);
    Vertice vpx2 = new Vertice(0.5F, 2.0F, 0.0F);
    Vertice vnx2 = new Vertice(-0.5F, 2.0F, 0.0F);
    Vertice vpz2 = new Vertice(0.0F, 2.0F, 0.5F);
    Vertice vnz2 = new Vertice(0.0F, 2.0F, -0.5F);
    Vertice vpxpz2 = new Vertice(0.3535533F, 2.0F, 0.3535533F);
    Vertice vpxnz2 = new Vertice(0.3535533F, 2.0F, -0.3535533F);
    Vertice vnxpz2 = new Vertice(-0.3535533F, 2.0F, 0.3535533F);
    Vertice vnxnz2 = new Vertice(-0.3535533F, 2.0F, -0.3535533F);
    float tx1 = 0.0F;
    float tx2 = 0.28125F;
    float tx4 = 0.5625F;
    float tx5 = 0.65625F;
    float ty1 = 0.0F;
    float ty2 = 0.09375F;

    public void render() {
        GL11.glPushMatrix();
        RenderHelper.addFace(this.vpx1, this.vpx2, this.vpxpz2, this.vpxpz1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vpxpz1, this.vpxpz2, this.vpz2, this.vpz1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vpz1, this.vpz2, this.vnxpz2, this.vnxpz1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vnxpz1, this.vnxpz2, this.vnx2, this.vnx1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vnx1, this.vnx2, this.vnxnz2, this.vnxnz1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vnxnz1, this.vnxnz2, this.vnz2, this.vnz1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vnz1, this.vnz2, this.vpxnz2, this.vpxnz1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vpxnz1, this.vpxnz2, this.vpx2, this.vpx1, this.tx1, this.tx2, this.ty1, this.ty2);
        RenderHelper.addFace(this.vpx1, this.vpxpz1, this.vpz1, this.vy1, this.tx4, this.tx5, this.ty1, this.ty2);
        RenderHelper.addFace(this.vpz1, this.vnxpz1, this.vnx1, this.vy1, this.tx4, this.tx5, this.ty1, this.ty2);
        RenderHelper.addFace(this.vnx1, this.vnxnz1, this.vnz1, this.vy1, this.tx4, this.tx5, this.ty1, this.ty2);
        RenderHelper.addFace(this.vnz1, this.vpxnz1, this.vpx1, this.vy1, this.tx4, this.tx5, this.ty1, this.ty2);
        GL11.glPopMatrix();
    }
}