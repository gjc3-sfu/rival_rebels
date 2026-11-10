package com.rivalrebels.client.render;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.model.LaptopModel;
import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.client.renderhelper.TextureVertice;
import com.rivalrebels.client.renderhelper.Vertice;
import com.rivalrebels.common.tileentity.TileLaptop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/*
@SideOnly(Side.CLIENT) 
public class TileEntityLaptopRenderer extends TileEntitySpecialRenderer<TileLaptop> {
   private LaptopModel model = new LaptopModel();

   public void renderAModelAt(TileEntityLaptop tile, double d, double d1, double d2, float f) {
      GL11.glDisable(2896);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
      int var9 = tile.getBlockMetadata();
      short var11 = 0;
      if (var9 == 2) {
         var11 = 180;
      }

      if (var9 == 3) {
         var11 = 0;
      }

      if (var9 == 4) {
         var11 = -90;
      }

      if (var9 == 5) {
         var11 = 90;
      }

      GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etlaptop);
      this.model.renderModel((float)(-tile.slide));
      Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etubuntu);
      this.model.renderScreen((float)(-tile.slide));
      GL11.glPopMatrix();
   }

   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
      //this.renderAModelAt((TileEntityLaptop)tileentity, d, d1, d2, f);
   }
}
*/