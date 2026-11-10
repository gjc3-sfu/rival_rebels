package com.rivalrebels.client.model;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.oldstuff.Tessellator;
import com.rivalrebels.client.renderhelper.TextureVertice;
import com.rivalrebels.client.renderhelper.Vertice;


public class ReactorModel {
    float xoff = 1.0F;
    float yoff = 1.0F;
    float zoff = 1.0F;
    TextureVertice t1 = new TextureVertice(0.0F, 0.0F);
    TextureVertice t2 = new TextureVertice(0.25F, 0.0F);
    TextureVertice t3 = new TextureVertice(0.5F, 0.0F);
    TextureVertice t4 = new TextureVertice(0.609375F, 0.0F);
    TextureVertice t5 = new TextureVertice(0.640625F, 0.0F);
    TextureVertice t6 = new TextureVertice(0.6875F, 0.0F);
    TextureVertice t7 = new TextureVertice(0.5F, 0.015625F);
    TextureVertice t8 = new TextureVertice(0.640625F, 0.046875F);
    TextureVertice t9 = new TextureVertice(0.6875F, 0.046875F);
    TextureVertice t10 = new TextureVertice(0.609375F, 0.21875F);
    TextureVertice t11 = new TextureVertice(0.640625F, 0.21875F);
    TextureVertice t12 = new TextureVertice(0.5F, 0.234375F);
    TextureVertice t13 = new TextureVertice(0.0F, 0.25F);
    TextureVertice t14 = new TextureVertice(0.25F, 0.25F);
    TextureVertice t15 = new TextureVertice(0.265625F, 0.25F);
    TextureVertice t16 = new TextureVertice(0.484375F, 0.25F);
    TextureVertice t17 = new TextureVertice(0.5F, 0.25F);
    TextureVertice t18 = new TextureVertice(0.609375F, 0.25F);
    TextureVertice t19 = new TextureVertice(0.25F, 0.359375F);
    TextureVertice t20 = new TextureVertice(0.265625F, 0.359375F);
    TextureVertice t21 = new TextureVertice(0.484375F, 0.359375F);
    TextureVertice t22 = new TextureVertice(0.5F, 0.359375F);
    TextureVertice t23 = new TextureVertice(0.25F, 0.46875F);
    TextureVertice t24 = new TextureVertice(0.5F, 0.46875F);
    TextureVertice t1v = new TextureVertice(0.8203125F, 0.0078125F);
    TextureVertice t2v = new TextureVertice(0.9921875F, 0.1796875F);
    TextureVertice t3v = new TextureVertice(0.8203125F, 0.3515625F);
    TextureVertice t4v = new TextureVertice(0.6484375F, 0.1796875F);
    TextureVertice t5v = new TextureVertice(0.03125F, 0.28125F);
    TextureVertice t6v = new TextureVertice(0.21875F, 0.28125F);
    TextureVertice t7v = new TextureVertice(0.03125F, 0.46875F);
    TextureVertice t8v = new TextureVertice(0.21875F, 0.46875F);
    TextureVertice t9v = new TextureVertice(0.015625F, 0.53125F);
    TextureVertice t10v = new TextureVertice(0.234375F, 0.53125F);
    TextureVertice t11v = new TextureVertice(0.015625F, 0.75F);
    TextureVertice t12v = new TextureVertice(0.234375F, 0.75F);
    float s = 0.5F;
    Vertice v1 = new Vertice(this.s, -this.s, this.s);
    Vertice v2 = new Vertice(this.s, -this.s, -this.s);
    Vertice v3 = new Vertice(-this.s, -this.s, -this.s);
    Vertice v4 = new Vertice(-this.s, -this.s, this.s);
    Vertice v5 = new Vertice(this.s, this.s, this.s);
    Vertice v6 = new Vertice(this.s, this.s, -this.s);
    Vertice v7 = new Vertice(-this.s, this.s, -this.s);
    Vertice v8 = new Vertice(-this.s, this.s, this.s);
    Vertice v9 = new Vertice(0.4375F, 0.8125F, 0.4375F);
    Vertice v10 = new Vertice(0.4375F, 0.8125F, -0.4375F);
    Vertice v11 = new Vertice(-0.4375F, 0.8125F, -0.4375F);
    Vertice v12 = new Vertice(-0.4375F, 0.8125F, 0.4375F);
    Vertice v13 = new Vertice(0.5F, 0.8125F, 0.5F);
    Vertice v14 = new Vertice(0.5F, 0.8125F, -0.5F);
    Vertice v15 = new Vertice(-0.5F, 0.8125F, -0.5F);
    Vertice v16 = new Vertice(-0.5F, 0.8125F, 0.5F);

    public void renderModel() {
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        this.addFace(this.v13, this.v14, this.v15, this.v16, this.t14, this.t2, this.t3, this.t17);
        this.addFace(this.v9, this.v5, this.v6, this.v10, this.t7, this.t4, this.t18, this.t12);
        this.addFace(this.v10, this.v6, this.v7, this.v11, this.t15, this.t19, this.t22, this.t16);
        this.addFace(this.v11, this.v7, this.v8, this.v12, this.t7, this.t4, this.t18, this.t12);
        this.addFace(this.v12, this.v8, this.v5, this.v9, this.t21, this.t24, this.t23, this.t20);
        this.addFace(this.v5, this.v1, this.v2, this.v6, this.t2, this.t14, this.t13, this.t1);
        this.addFace(this.v6, this.v2, this.v3, this.v7, this.t2, this.t14, this.t13, this.t1);
        this.addFace(this.v7, this.v3, this.v4, this.v8, this.t2, this.t14, this.t13, this.t1);
        this.addFace(this.v8, this.v4, this.v1, this.v5, this.t2, this.t14, this.t13, this.t1);
        this.addFace(this.v2, this.v1, this.v4, this.v3, this.t1v, this.t2v, this.t3v, this.t4v);
        this.xoff = 0.9375F;
        this.addFace(this.v5, this.v1, this.v2, this.v6, this.t1v, this.t2v, this.t3v, this.t4v);
        this.xoff = 1.0F;
        this.zoff = 0.9375F;
        this.addFace(this.v6, this.v2, this.v3, this.v7, this.t1v, this.t2v, this.t3v, this.t4v);
        this.xoff = 0.9375F;
        this.zoff = 1.0F;
        this.addFace(this.v7, this.v3, this.v4, this.v8, this.t1v, this.t2v, this.t3v, this.t4v);
        this.xoff = 1.0F;
        this.zoff = 0.9375F;
        this.addFace(this.v8, this.v4, this.v1, this.v5, this.t1v, this.t2v, this.t3v, this.t4v);
        this.zoff = 1.0F;
        this.xoff = this.yoff = this.zoff = 0.6875F;
        this.addFace(this.v2, this.v1, this.v4, this.v3, this.t5v, this.t6v, this.t8v, this.t7v);
        this.addFace(this.v5, this.v1, this.v2, this.v6, this.t5v, this.t6v, this.t8v, this.t7v);
        this.addFace(this.v6, this.v2, this.v3, this.v7, this.t5v, this.t6v, this.t8v, this.t7v);
        this.addFace(this.v7, this.v3, this.v4, this.v8, this.t5v, this.t6v, this.t8v, this.t7v);
        this.addFace(this.v8, this.v4, this.v1, this.v5, this.t5v, this.t6v, this.t8v, this.t7v);
        this.addFace(this.v5, this.v6, this.v7, this.v8, this.t5v, this.t6v, this.t8v, this.t7v);
        this.xoff = this.yoff = this.zoff = 0.8125F;
        this.addFace(this.v2, this.v1, this.v4, this.v3, this.t9v, this.t10v, this.t12v, this.t11v);
        this.addFace(this.v5, this.v1, this.v2, this.v6, this.t9v, this.t10v, this.t12v, this.t11v);
        this.addFace(this.v6, this.v2, this.v3, this.v7, this.t9v, this.t10v, this.t12v, this.t11v);
        this.addFace(this.v7, this.v3, this.v4, this.v8, this.t9v, this.t10v, this.t12v, this.t11v);
        this.addFace(this.v8, this.v4, this.v1, this.v5, this.t9v, this.t10v, this.t12v, this.t11v);
        this.addFace(this.v5, this.v6, this.v7, this.v8, this.t9v, this.t10v, this.t12v, this.t11v);
        this.xoff = this.yoff = this.zoff = 1.0F;
        GL11.glPopMatrix();
    }

    private void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        this.addVertice(v1, t1);
        this.addVertice(v2, t2);
        this.addVertice(v3, t3);
        this.addVertice(v4, t4);
        t.draw();
    }

    private void addVertice(Vertice v, TextureVertice t) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.addVertexWithUV(v.x * this.xoff, v.y * this.yoff, v.z * this.zoff, t.x, t.y);
    }
}
