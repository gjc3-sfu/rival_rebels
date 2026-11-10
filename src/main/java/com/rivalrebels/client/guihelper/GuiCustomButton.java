package com.rivalrebels.client.guihelper;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCustomButton extends net.minecraft.client.gui.GuiButton {
   Rectangle bbox;
   Vector tbox;
   ResourceLocation resloc;
   boolean toggleable;
   public boolean isPressed = false;
   public boolean wasPressed = false;
   public boolean mouseDown = false;

   public GuiCustomButton(int id, Rectangle rec, ResourceLocation rl, Vector uv, boolean isToggle) {
      super(id, rec.xMin, rec.yMin, rec.xMax - rec.xMin, rec.yMax - rec.yMin, "");
      this.bbox = rec;
      this.tbox = uv;
      this.resloc = rl;
      this.toggleable = isToggle;
   }

    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        boolean current = Mouse.isButtonDown(0) && this.bbox.isVecInside(new Vector(par2, par3));
        this.wasPressed = false;
        if (this.toggleable && current && !this.mouseDown) {
            this.mouseDown = true;
            this.isPressed = !this.isPressed;
            this.wasPressed = true;
        } else if (this.toggleable && !current) {
            this.mouseDown = false;
        } else if (!this.toggleable && current) {
            this.isPressed = true;
        } else if (!this.toggleable && !current) {
            this.isPressed = false;
        }

        if (this.isPressed) {
            par1Minecraft.renderEngine.bindTexture(this.resloc);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.bbox.xMin, this.bbox.yMin, this.tbox.x, this.tbox.y, this.bbox.xMax - this.bbox.xMin, this.bbox.yMax - this.bbox.yMin);
        }
    }
}
