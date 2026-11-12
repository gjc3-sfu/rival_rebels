package com.rivalrebels.client.render;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.model.ModelRocketLauncherBody;
import com.rivalrebels.client.model.ModelRocketLauncherHandle;
import com.rivalrebels.client.model.ModelRocketLauncherTube;
import com.rivalrebels.client.renderhelper.ItemRenderBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderM202 extends ItemRenderBase {
    private ModelRocketLauncherHandle model_handle = new ModelRocketLauncherHandle();
    private ModelRocketLauncherBody model_body = new ModelRocketLauncherBody();
    private ModelRocketLauncherTube model_tube = new ModelRocketLauncherTube();

    private static ResourceLocation body_texture = new ResourceLocation(ModInfo.modid, "textures/models/m202_body.png");
    private static ResourceLocation handle_texture = new ResourceLocation(ModInfo.modid, "textures/models/m202_handle.png");
    private static ResourceLocation tube_texture = new ResourceLocation(ModInfo.modid, "textures/models/m202_tube.png");

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        if (type == TransformType.GUI) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            Minecraft.getMinecraft().getRenderItem().renderItem(itemStackIn, baked_model);
            GL11.glPopMatrix();
        } else {
            renderItem(type, itemStackIn);
        }
    }

    public boolean handleRenderType(ItemStack item, TransformType type) {
        return type == TransformType.GUI
            || type == TransformType.FIRST_PERSON_RIGHT_HAND
            || type == TransformType.FIRST_PERSON_LEFT_HAND
            || type == TransformType.GROUND
            || type == TransformType.THIRD_PERSON_LEFT_HAND
            || type == TransformType.THIRD_PERSON_RIGHT_HAND;
    }

    public boolean shouldUseRenderHelper(TransformType type, ItemStack item, TransformType helper) {
        return false;
    }

    public void renderItem(TransformType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glEnable(2896);
        GL11.glTranslatef(0.4F, 0.35F, -0.03F);
        GL11.glRotatef(-55.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, 0.05F, 0.05F);
        if (type == TransformType.FIRST_PERSON_LEFT_HAND || type == TransformType.FIRST_PERSON_RIGHT_HAND) {
            GL11.glScalef(1.0F, 1.0F, -1.0F);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.22F, -0.025F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.03125F, 0.03125F, 0.03125F);
        Minecraft.getMinecraft().renderEngine.bindTexture(handle_texture);
        this.model_handle.render();
        /* 
        if (item.getEnchantmentTagList() != null) {
            GL11.glBindTexture(
                3553, TileEntityForceFieldNodeRenderer.id[(int)(TileEntityForceFieldNodeRenderer.getTime() / 100L % TileEntityForceFieldNodeRenderer.frames)]
            );
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDisable(2896);
            this.md2.render();
            GL11.glDisable(3042);
            GL11.glEnable(2896);
        }
        */

        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F, 0.31F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.4F, 0.4F, 0.4F);
        Minecraft.getMinecraft().renderEngine.bindTexture(body_texture);
        this.model_body.render();
        /* 
        if (item.getEnchantmentTagList() != null) {
            GL11.glBindTexture(
                3553, TileEntityForceFieldNodeRenderer.id[(int)(TileEntityForceFieldNodeRenderer.getTime() / 100L % TileEntityForceFieldNodeRenderer.frames)]
            );
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDisable(2896);
            this.md3.render();
            GL11.glDisable(3042);
            GL11.glEnable(2896);
        }
        */

        GL11.glPopMatrix();
        float s = 0.0812F;
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F + s, 0.71F, s);
        GL11.glScalef(0.15F, 0.1F, 0.15F);
        Minecraft.getMinecraft().renderEngine.bindTexture(tube_texture);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F - s, 0.71F, s);
        GL11.glScalef(0.15F, 0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F + s, 0.71F, -s);
        GL11.glScalef(0.15F, 0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F - s, 0.71F, -s);
        GL11.glScalef(0.15F, 0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F + s, -0.285F, s);
        GL11.glScalef(0.15F, -0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F - s, -0.285F, s);
        GL11.glScalef(0.15F, -0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F + s, -0.285F, -s);
        GL11.glScalef(0.15F, -0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.07F - s, -0.285F, -s);
        GL11.glScalef(0.15F, -0.1F, 0.15F);
        this.model_tube.render();
        GL11.glPopMatrix();
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
}