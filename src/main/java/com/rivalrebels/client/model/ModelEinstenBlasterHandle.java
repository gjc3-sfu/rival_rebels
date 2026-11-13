package com.rivalrebels.client.model;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.client.renderhelper.TextureFace;
import com.rivalrebels.client.renderhelper.TextureVertice;
import com.rivalrebels.client.renderhelper.Vertice;

import net.minecraft.client.model.ModelBase;

public class ModelEinstenBlasterHandle extends ModelBase {
    TextureFace handleside = new TextureFace(
        new TextureVertice(0.0F, 0.34375F), new TextureVertice(0.0F, 0.5625F), new TextureVertice(0.0625F, 0.5625F), new TextureVertice(0.0625F, 0.34375F)
    );
    TextureFace handlefront = new TextureFace(
        new TextureVertice(0.0625F, 0.34375F),
        new TextureVertice(0.0625F, 0.5625F),
        new TextureVertice(0.109375F, 0.5625F),
        new TextureVertice(0.109375F, 0.34375F)
    );
    TextureFace handlebottom = new TextureFace(
        new TextureVertice(0.0625F, 0.5625F), new TextureVertice(0.0F, 0.5625F), new TextureVertice(0.0F, 0.65625F), new TextureVertice(0.0625F, 0.65625F)
    );
    TextureFace bottomside = new TextureFace(
        new TextureVertice(0.765625F, 0.3125F),
        new TextureVertice(0.71875F, 0.40625F),
        new TextureVertice(0.5625F, 0.40625F),
        new TextureVertice(0.5625F, 0.3125F)
    );
    TextureFace bottomfront = new TextureFace(
        new TextureVertice(0.71875F, 0.40625F),
        new TextureVertice(0.78125F, 0.40625F),
        new TextureVertice(0.78125F, 0.53125F),
        new TextureVertice(0.71875F, 0.53125F)
    );
    TextureFace bottombottom = new TextureFace(
        new TextureVertice(0.71875F, 0.53125F),
        new TextureVertice(0.5625F, 0.53125F),
        new TextureVertice(0.5625F, 0.40625F),
        new TextureVertice(0.71875F, 0.40625F)
    );
    TextureFace bottomback = new TextureFace(
        new TextureVertice(0.515625F, 0.40625F),
        new TextureVertice(0.5625F, 0.40625F),
        new TextureVertice(0.5625F, 0.53125F),
        new TextureVertice(0.515625F, 0.53125F)
    );
    Vertice vht1 = new Vertice(11.0F, 0.0F, 2.0F);
    Vertice vht2 = new Vertice(15.0F, 0.0F, 2.0F);
    Vertice vht3 = new Vertice(15.0F, 0.0F, -2.0F);
    Vertice vht4 = new Vertice(11.0F, 0.0F, -2.0F);
    Vertice vhb1 = new Vertice(7.0F, -7.0F, 2.0F);
    Vertice vhb2 = new Vertice(11.0F, -7.0F, 2.0F);
    Vertice vhb3 = new Vertice(11.0F, -7.0F, -2.0F);
    Vertice vhb4 = new Vertice(7.0F, -7.0F, -2.0F);
    Vertice vbt1 = new Vertice(8.0F, 3.0F, 2.0F);
    Vertice vbt2 = new Vertice(23.0F, 3.0F, 2.0F);
    Vertice vbt3 = new Vertice(23.0F, 3.0F, -2.0F);
    Vertice vbt4 = new Vertice(8.0F, 3.0F, -2.0F);
    Vertice vbb1 = new Vertice(8.0F, 0.0F, 2.0F);
    Vertice vbb2 = new Vertice(20.0F, 0.0F, 2.0F);
    Vertice vbb3 = new Vertice(20.0F, 0.0F, -2.0F);
    Vertice vbb4 = new Vertice(8.0F, 0.0F, -2.0F);

    public void render() {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glDisable(2896);
        GL11.glPushMatrix();
        GL11.glScaled(1.3, 1.0, 1.0);
        RenderHelper.addFace(this.vbt3, this.vbt4, this.vbt1, this.vbt2, this.bottombottom);
        RenderHelper.addFace(this.vbb1, this.vbt1, this.vbt4, this.vbb4, this.bottomfront);
        RenderHelper.addFace(this.vbb3, this.vbt3, this.vbt2, this.vbb2, this.bottomback);
        RenderHelper.addFace(this.vbt2, this.vbb2, this.vbb1, this.vbt1, this.bottomside);
        RenderHelper.addFace(this.vbt3, this.vbb3, this.vbb4, this.vbt4, this.bottomside);
        RenderHelper.addFace(this.vbb3, this.vbb4, this.vbb1, this.vbb2, this.bottombottom);
        GL11.glPopMatrix();
        RenderHelper.addFace(this.vht4, this.vhb4, this.vhb1, this.vht1, this.handlefront);
        RenderHelper.addFace(this.vht2, this.vhb2, this.vhb3, this.vht3, this.handlefront);
        RenderHelper.addFace(this.vht1, this.vhb1, this.vhb2, this.vht2, this.handleside);
        RenderHelper.addFace(this.vht3, this.vhb3, this.vhb4, this.vht4, this.handleside);
        RenderHelper.addFace(this.vhb2, this.vhb1, this.vhb4, this.vhb3, this.handlebottom);
        GL11.glPopMatrix();
    }
}

