package com.rivalrebels.client.guihelper;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiScroll extends net.minecraft.client.gui.GuiButton {
    public int limit;
    public int scroll;
    public boolean pressed = false;

    public static ResourceLocation button_texture = new ResourceLocation(ModInfo.modid, "textures/gui/knob_button.png");

    public GuiScroll(int par1, int par2, int par3, int par4) {
        super(par1, par2, par3, 5, 11, "");
        this.limit = par4;
        this.pressed = false;
    }

    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        this.mouseDragged(par1Minecraft, par2, par3);
        if (this.scroll > this.limit) {
            this.scroll = this.limit;
        }

        if (this.scroll < 0) {
            this.scroll = 0;
        }

        par1Minecraft.renderEngine.bindTexture(button_texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int state = 0;
        if (this.pressed || this.mousePressed(par1Minecraft, par2, par3)) {
            state = 11;
        }

        this.drawTexturedModalRect(this.x, this.y + this.scroll, 0, state, this.width, this.height);
    }

    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        if (Mouse.isButtonDown(0)) {
            if (this.mousePressed(par1Minecraft, par2, par3)) {
                this.pressed = true;
            }

            if (this.pressed) {
                this.scroll = par3 - this.y - 5;
            }
        } else {
            this.pressed = false;
        }
    }

    public void mouseReleased(int par2, int par3) {
        this.pressed = false;
    }

    public int getScroll() {
        return this.scroll;
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        return this.enabled
            && this.visible
            && par2 >= this.x
            && par3 >= this.y
            && par2 < this.x + this.width
            && par3 < this.y + this.limit + this.height;
    }
}

