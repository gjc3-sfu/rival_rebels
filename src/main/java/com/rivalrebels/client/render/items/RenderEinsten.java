package com.rivalrebels.client.render.items;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.model.ModelEinstenBlasterBack;
import com.rivalrebels.client.model.ModelEinstenBlasterBarrel;
import com.rivalrebels.client.model.ModelEinstenBlasterBody;
import com.rivalrebels.client.model.ModelEinstenBlasterHandle;
import com.rivalrebels.client.model.RodModel;
import com.rivalrebels.client.oldstuff.Tessellator;
import com.rivalrebels.client.renderhelper.ItemRenderBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

 
public class RenderEinsten extends ItemRenderBase {
    ModelEinstenBlasterBarrel barrel_model = new ModelEinstenBlasterBarrel(); //md1
    ModelEinstenBlasterHandle handle_model = new ModelEinstenBlasterHandle();
    ModelEinstenBlasterBody body_model = new ModelEinstenBlasterBody();
    ModelEinstenBlasterBack back_model = new ModelEinstenBlasterBack();
    RodModel rod_model = new RodModel(); //md5

    protected static ResourceLocation barrel_texture = new ResourceLocation(ModInfo.modid, "textures/models/einsten_barrel.png");
    protected static ResourceLocation back_texture = new ResourceLocation(ModInfo.modid, "textures/models/einsten_back.png");
    protected static ResourceLocation handle_texture = new ResourceLocation(ModInfo.modid, "textures/models/einsten_handle.png");

    protected static ResourceLocation redstone_rod_texture = new ResourceLocation(ModInfo.modid, "textures/models/redstone_rod.png");

    float pullback = 0.0F;
    float rotation = 0.0F;
    boolean isreloading = false;
    int stage = 0;
    int spin = 0;
    int time = 1;
    int reloadcooldown = 0;

    public RenderEinsten() {
    }

    public boolean handleRenderType(ItemStack item, TransformType type) {
        return type == TransformType.FIRST_PERSON_RIGHT_HAND
            || type == TransformType.FIRST_PERSON_LEFT_HAND
            || type == TransformType.GROUND;
    }

    public boolean shouldUseRenderHelper(TransformType type, ItemStack item) {
        return false;
    }

    public void renderItem(TransformType type, ItemStack item, Object... data) {
        this.spin++;
        if (item.getRepairCost() >= 1) {
            this.spin = (int)(this.spin + item.getRepairCost() / 2.2);
        }

        this.spin %= 628;
        if (this.reloadcooldown > 0) {
            this.reloadcooldown--;
        }

        if (item.getRepairCost() > 20 && this.reloadcooldown == 0) {
            this.isreloading = true;
        }

        if (this.isreloading) {
            if (this.stage == 0) {
                if (this.pullback < 0.3) {
                this.pullback = (float)(this.pullback + 0.03);
                } else {
                this.stage = 1;
                }
            }

            if (this.stage == 1) {
                if (this.rotation < 90.0F) {
                this.rotation = (float)(this.rotation + 4.5);
                } else {
                this.stage = 2;
                }
            }

            if (this.stage == 2) {
                if (this.pullback > 0.0F) {
                this.pullback = (float)(this.pullback - 0.03);
                } else {
                this.stage = 0;
                this.isreloading = false;
                this.reloadcooldown = 60;
                this.rotation = 0.0F;
                }
            }
        }

        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glTranslatef(0.4F, 0.35F, -0.03F);
        GL11.glRotatef(-55.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -0.05F, 0.05F);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.9F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(barrel_texture);
        this.barrel_model.render();
        /* //Ignore enchantments for now
        if (item.getEnchantmentTagList() != null && item.getEnchantmentTagList().tagCount() > 0) {
            GL11.glBindTexture(
                3553, TileEntityForceFieldNodeRenderer.id[(int)(TileEntityForceFieldNodeRenderer.getTime() / 100L % TileEntityForceFieldNodeRenderer.frames)]
            );
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDisable(2896);
            this.md1.render();
            GL11.glDisable(3042);
            GL11.glEnable(2896);
        }
        */

        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.22F, -0.025F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.03125F, 0.03125F, 0.03125F);
        Minecraft.getMinecraft().renderEngine.bindTexture(handle_texture);
        this.handle_model.render();

        /* //Ignore enchantments for now
        if (item.getEnchantmentTagList() != null && item.getEnchantmentTagList().tagCount() > 0) {
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
        GL11.glTranslatef(0.0F, 0.2F, 0.0F);
        GL11.glScaled(0.85, 0.85, 0.85);
        Minecraft.getMinecraft().renderEngine.bindTexture(back_texture);
        this.back_model.render();

        /* //Ignore enchantments for now
        if (item.getEnchantmentTagList() != null && item.getEnchantmentTagList().tagCount() > 0) {
            GL11.glBindTexture(
                3553, TileEntityForceFieldNodeRenderer.id[(int)(TileEntityForceFieldNodeRenderer.getTime() / 100L % TileEntityForceFieldNodeRenderer.frames)]
            );
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDisable(2896);
            this.back_model.render();
            GL11.glDisable(3042);
            GL11.glEnable(2896);
        }
        */

        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -this.pullback, 0.0F);
        GL11.glRotatef(this.rotation, 0.0F, 1.0F, 0.0F);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.12F, 0.1F, 0.12F);
        GL11.glRotatef(this.pullback * 270.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.3F, 0.7F, 0.3F);
        Minecraft.getMinecraft().renderEngine.bindTexture(redstone_rod_texture);
        this.rod_model.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.12F, 0.1F, 0.12F);
        GL11.glRotatef(this.pullback * 270.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.3F, 0.7F, 0.3F);
        Minecraft.getMinecraft().renderEngine.bindTexture(redstone_rod_texture);
        this.rod_model.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.12F, 0.1F, -0.12F);
        GL11.glRotatef(this.pullback * 270.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.3F, 0.7F, 0.3F);
        Minecraft.getMinecraft().renderEngine.bindTexture(redstone_rod_texture);
        this.rod_model.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.12F, 0.1F, -0.12F);
        GL11.glRotatef(this.pullback * 270.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.3F, 0.7F, 0.3F);
        Minecraft.getMinecraft().renderEngine.bindTexture(redstone_rod_texture);
        this.rod_model.render();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glTranslatef(0.0F, 0.25F, 0.0F);
        float segmentDistance = 0.1F;
        float distance = 0.5F;
        float radius = 0.01F;
        Random rand = new Random();
        Tessellator tessellator = Tessellator.instance;
        double AddedX = 0.0;
        double AddedZ = 0.0;
        double prevAddedX = 0.0;
        double prevAddedZ = 0.0;

        for (float AddedY = distance; AddedY >= 0.0F; AddedY -= segmentDistance) {
            prevAddedX = AddedX;
            prevAddedZ = AddedZ;
            AddedX = (rand.nextFloat() - 0.5) * 0.1F;
            AddedZ = (rand.nextFloat() - 0.5) * 0.1F;
            double dist = Math.sqrt(AddedX * AddedX + AddedZ * AddedZ);
            if (dist != 0.0) {
                double tempAddedX = AddedX / dist;
                double tempAddedZ = AddedZ / dist;
                if (Math.abs(tempAddedX) < Math.abs(AddedX)) {
                AddedX = tempAddedX;
                }

                if (Math.abs(tempAddedZ) < Math.abs(AddedZ)) {
                AddedZ = tempAddedZ;
                }
            }

            if (AddedY <= 0.0F) {
                AddedZ = 0.0;
                AddedX = 0.0;
            }

            for (float o = 0.0F; o <= radius; o += radius / 2.0F) {
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
                tessellator.addVertex(AddedX + o, AddedY, AddedZ - o);
                tessellator.addVertex(AddedX + o, AddedY, AddedZ + o);
                tessellator.addVertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o);
                tessellator.addVertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
                tessellator.addVertex(AddedX - o, AddedY, AddedZ - o);
                tessellator.addVertex(AddedX + o, AddedY, AddedZ - o);
                tessellator.addVertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o);
                tessellator.addVertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
                tessellator.addVertex(AddedX - o, AddedY, AddedZ + o);
                tessellator.addVertex(AddedX - o, AddedY, AddedZ - o);
                tessellator.addVertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o);
                tessellator.addVertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
                tessellator.addVertex(AddedX + o, AddedY, AddedZ + o);
                tessellator.addVertex(AddedX - o, AddedY, AddedZ + o);
                tessellator.addVertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o);
                tessellator.addVertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o);
                tessellator.draw();
            }
        }

        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.8F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(this.spin, 0.0F, 1.0F, 0.0F);
        GL11.glScaled(0.9, 4.1, 0.9);
        this.body_model.render((float)(0.22F + Math.sin(this.spin / 10) * 0.005), 0.5F, 0.0F, 0.0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.8F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-this.spin, 0.0F, 1.0F, 0.0F);
        GL11.glScaled(0.9, 4.1, 0.9);
        this.body_model.render((float)(0.22F + Math.cos(-this.spin / 15) * 0.005), 0.5F, 0.0F, 0.0F, 1.0F);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
