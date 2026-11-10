package com.rivalrebels.client.model;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.client.renderhelper.TextureVertice;
import com.rivalrebels.client.renderhelper.Vertice;

public class LaptopModel {
   TextureVertice t1 = new TextureVertice(0.03125F, 0.0F);
   TextureVertice t2 = new TextureVertice(0.3125F, 0.0F);
   TextureVertice t3 = new TextureVertice(0.0F, 0.03125F);
   TextureVertice t4 = new TextureVertice(0.03125F, 0.03125F);
   TextureVertice t5 = new TextureVertice(0.46875F, 0.03125F);
   TextureVertice t6 = new TextureVertice(0.5F, 0.03125F);
   TextureVertice t7 = new TextureVertice(0.9375F, 0.03125F);
   TextureVertice t8 = new TextureVertice(0.0F, 0.3125F);
   TextureVertice t9 = new TextureVertice(0.03125F, 0.3125F);
   TextureVertice t10 = new TextureVertice(0.46875F, 0.3125F);
   TextureVertice t11 = new TextureVertice(0.5F, 0.3125F);
   TextureVertice t12 = new TextureVertice(0.9375F, 0.3125F);
   TextureVertice t13 = new TextureVertice(0.03125F, 0.34375F);
   TextureVertice t14 = new TextureVertice(0.46875F, 0.34375F);
   TextureVertice t1t = new TextureVertice(0.0625F, 0.34375F);
   TextureVertice t2t = new TextureVertice(0.5F, 0.34375F);
   TextureVertice t3t = new TextureVertice(0.0F, 0.40625F);
   TextureVertice t4t = new TextureVertice(0.0625F, 0.40625F);
   TextureVertice t5t = new TextureVertice(0.5F, 0.40625F);
   TextureVertice t6t = new TextureVertice(0.5625F, 0.40625F);
   TextureVertice t7t = new TextureVertice(1.0F, 0.40625F);
   TextureVertice t8t = new TextureVertice(0.0F, 0.6875F);
   TextureVertice t9t = new TextureVertice(0.0625F, 0.6875F);
   TextureVertice t10t = new TextureVertice(0.5F, 0.6875F);
   TextureVertice t11t = new TextureVertice(0.5625F, 0.6875F);
   TextureVertice t12t = new TextureVertice(1.0F, 0.6875F);
   TextureVertice t13t = new TextureVertice(0.0625F, 0.75F);
   TextureVertice t14t = new TextureVertice(0.5F, 0.75F);
   Vertice v1 = new Vertice(0.4375F, 0.0F, 0.5625F);
   Vertice v2 = new Vertice(0.4375F, 0.0F, 0.0F);
   Vertice v3 = new Vertice(-0.4375F, 0.0F, 0.0F);
   Vertice v4 = new Vertice(-0.4375F, 0.0F, 0.5625F);
   Vertice v5 = new Vertice(0.4375F, 0.0625F, 0.5625F);
   Vertice v6 = new Vertice(0.4375F, 0.0625F, 0.0F);
   Vertice v7 = new Vertice(-0.4375F, 0.0625F, 0.0F);
   Vertice v8 = new Vertice(-0.4375F, 0.0625F, 0.5625F);
   Vertice v9 = new Vertice(0.4375F, 0.125F, 0.5625F);
   Vertice v10 = new Vertice(0.4375F, 0.125F, 0.0F);
   Vertice v11 = new Vertice(-0.4375F, 0.125F, 0.0F);
   Vertice v12 = new Vertice(-0.4375F, 0.125F, 0.5625F);
   TextureVertice t111 = new TextureVertice(0.0F, 0.0F);
   TextureVertice t222 = new TextureVertice(1.0F, 0.0F);
   TextureVertice t333 = new TextureVertice(1.0F, 1.0F);
   TextureVertice t444 = new TextureVertice(0.0F, 1.0F);
   Vertice v1v = new Vertice(0.375F, 0.0F, 0.5F);
   Vertice v2v = new Vertice(0.375F, 0.0F, 0.0625F);
   Vertice v3v = new Vertice(-0.375F, 0.0F, 0.0625F);
   Vertice v4v = new Vertice(-0.375F, 0.0F, 0.5F);

   public void renderModel(float turn) {
      GL11.glPushMatrix();
      RenderHelper.addFace(this.v11, this.v12, this.v9, this.v10, this.t4t, this.t9t, this.t10t, this.t5t);
      RenderHelper.addFace(this.v12, this.v4, this.v1, this.v9, this.t9t, this.t13t, this.t14t, this.t10t);
      RenderHelper.addFace(this.v11, this.v3, this.v4, this.v12, this.t4t, this.t3t, this.t8t, this.t9t);
      RenderHelper.addFace(this.v10, this.v2, this.v3, this.v11, this.t5t, this.t2t, this.t1t, this.t4t);
      RenderHelper.addFace(this.v9, this.v1, this.v2, this.v10, this.t10t, this.t11t, this.t6t, this.t5t);
      RenderHelper.addFace(this.v2, this.v1, this.v4, this.v3, this.t6t, this.t11t, this.t12t, this.t7t);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.125F, 0.0F);
      GL11.glRotatef(turn, 0.1875F, 0.0F, 0.0F);
      RenderHelper.addFace(this.v5, this.v6, this.v7, this.v8, this.t4, this.t9, this.t10, this.t5);
      RenderHelper.addFace(this.v8, this.v4, this.v1, this.v5, this.t9, this.t13, this.t14, this.t10);
      RenderHelper.addFace(this.v7, this.v3, this.v4, this.v8, this.t4, this.t3, this.t8, this.t9);
      RenderHelper.addFace(this.v6, this.v2, this.v3, this.v7, this.t5, this.t2, this.t1, this.t4);
      RenderHelper.addFace(this.v5, this.v1, this.v2, this.v6, this.t10, this.t11, this.t6, this.t5);
      RenderHelper.addFace(this.v2, this.v1, this.v4, this.v3, this.t6, this.t11, this.t12, this.t7);
      GL11.glPopMatrix();
   }

   public void renderScreen(float turn) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.125F, 0.0F);
      GL11.glRotatef(turn, 0.1875F, 0.0F, 0.0F);
      RenderHelper.addFace(this.v2v, this.v1v, this.v4v, this.v3v, this.t333, this.t222, this.t111, this.t444);
      GL11.glPopMatrix();
   }
}
