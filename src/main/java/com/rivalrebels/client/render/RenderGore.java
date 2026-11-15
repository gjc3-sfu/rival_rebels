package com.rivalrebels.client.render;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.client.renderhelper.RenderHelper;
import com.rivalrebels.common.entity.EntityGore;
import com.rivalrebels.common.entity.EntityGore.Model_Type;
import com.rivalrebels.common.entity.EntityGore.Part_Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGore extends Render<EntityGore> {


    private static final ResourceLocation player = new ResourceLocation("textures/entity/steve.png");
    private static final ResourceLocation creeper = new ResourceLocation("textures/entity/creeper/creeper.png");
    private static final ResourceLocation enderman = new ResourceLocation("textures/entity/enderman/enderman.png");
    private static final ResourceLocation ghast = new ResourceLocation("textures/entity/ghast/ghast.png");
    private static final ResourceLocation skeleton = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation slime = new ResourceLocation("textures/entity/slime/slime.png");
    private static final ResourceLocation magmacube = new ResourceLocation("textures/entity/slime/magmacube.png");
    private static final ResourceLocation spider = new ResourceLocation("textures/entity/spider/spider.png");
    private static final ResourceLocation cavespider = new ResourceLocation("textures/entity/spider/cave_spider.png");
    private static final ResourceLocation zombiepigman = new ResourceLocation("textures/entity/zombie_pigman.png");
    private static final ResourceLocation zombie = new ResourceLocation("textures/entity/zombie/zombie.png");
    RenderHelper md = new RenderHelper();

    private static final ResourceLocation SMALL_SPLASH_TEXTURE = new ResourceLocation("textures/blocks/gore_small.png");
    private static final ResourceLocation MEDIUM_SPLASH_TEXTURE = new ResourceLocation("textures/blocks/gore_medium.png");
    private static final ResourceLocation LARGE_SPLASH_TEXTURE = new ResourceLocation("textures/blocks/gore_large.png");


    public RenderGore(RenderManager renderManager) {
        super(renderManager);
    }

    public void doRender(EntityGore entity, double x, double y, double z, float f, float f1) {
        if (entity.mob_type == null || entity.limb_type == null) {
            return;
        }

        GL11.glPushMatrix();
        GL11.glEnable(2896);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-entity.rotationYaw + 180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        EntityGore eg = (EntityGore)entity;
        Model_Type mob_model_type = eg.mob_type;
        Part_Type type = eg.limb_type;
        double size = eg.size;
        switch (mob_model_type) {
            case CAVESPIDER:
                Minecraft.getMinecraft().renderEngine.bindTexture(cavespider);
                GL11.glScalef(0.666F, 0.666F, 0.666F);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 32.0F, 4.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    RenderHelper.renderBox(8.0F, 12.0F, 10.0F, 4.0F, 12.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(2.0F, 2.0F, 16.0F, 18.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case CREEPER:
                Minecraft.getMinecraft().renderEngine.bindTexture(creeper);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(4.0F, 12.0F, 8.0F, 16.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(4.0F, 6.0F, 4.0F, 0.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case DEFAULT:
                if (size < 1.0) {
                    Minecraft.getMinecraft().renderEngine.bindTexture(SMALL_SPLASH_TEXTURE);
                } else if (size < 2.0) {
                    Minecraft.getMinecraft().renderEngine.bindTexture(MEDIUM_SPLASH_TEXTURE);
                } else {
                    Minecraft.getMinecraft().renderEngine.bindTexture(LARGE_SPLASH_TEXTURE);
                }

                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox((int)(8.0 * size), (int)(8.0 * size), (int)(8.0 * size), 0.0F, 0.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox((int)(4.0 * size), (int)(12.0 * size), (int)(8.0 * size), 0.0F, 0.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.ARM) {
                    RenderHelper.renderBox((int)(4.0 * size), (int)(12.0 * size), (int)(4.0 * size), 0.0F, 0.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox((int)(4.0 * size), (int)(12.0 * size), (int)(4.0 * size), 0.0F, 0.0F, 64.0F, 64.0F, 16.0F);
                }
                break;
            case ENDERMAN:
                Minecraft.getMinecraft().renderEngine.bindTexture(enderman);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                    GL11.glTranslated(0.0, -0.125, 0.0);
                    GL11.glScaled(0.875, 0.875, 0.875);
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(4.0F, 12.0F, 8.0F, 32.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.ARM) {
                    RenderHelper.renderBox(2.0F, 30.0F, 2.0F, 56.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(2.0F, 30.0F, 2.0F, 56.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case GHAST:
                Minecraft.getMinecraft().renderEngine.bindTexture(ghast);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(16.0F, 16.0F, 16.0F, 0.0F, 0.0F, 64.0F, 32.0F, 4.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(2.0F, 14.0F, 2.0F, 0.0F, 0.0F, 64.0F, 32.0F, 4.0F);
                }
                break;
            case MAGMACUBE:
                Minecraft.getMinecraft().renderEngine.bindTexture(magmacube);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(6.0F, 6.0F, 6.0F, 0.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case PIGZOMBIE:
                Minecraft.getMinecraft().renderEngine.bindTexture(zombiepigman);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(4.0F, 12.0F, 8.0F, 16.0F, 16.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.ARM) {
                    RenderHelper.renderBox(4.0F, 12.0F, 4.0F, 40.0F, 16.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(4.0F, 12.0F, 4.0F, 0.0F, 16.0F, 64.0F, 64.0F, 16.0F);
                }
                break;
            case PLAYER:
                if (eg.playerSkin != null) {
                    Minecraft.getMinecraft().renderEngine.bindTexture(eg.playerSkin);
                } else {
                    Minecraft.getMinecraft().renderEngine.bindTexture(player);
                }

                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(4.0F, 12.0F, 8.0F, 16.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.ARM) {
                    RenderHelper.renderBox(4.0F, 12.0F, 4.0F, 40.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(4.0F, 12.0F, 4.0F, 0.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case SKELETON:
                Minecraft.getMinecraft().renderEngine.bindTexture(skeleton);
                GL11.glDisable(2884);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(4.0F, 12.0F, 8.0F, 16.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.ARM) {
                    RenderHelper.renderBox(2.0F, 10.0F, 2.0F, 40.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(2.0F, 10.0F, 2.0F, 0.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case SLIME:
                Minecraft.getMinecraft().renderEngine.bindTexture(slime);
                if (type == Part_Type.HEAD) {
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    RenderHelper.renderBox(6.0F, 6.0F, 6.0F, 0.0F, 16.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case SPIDER:
                Minecraft.getMinecraft().renderEngine.bindTexture(spider);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 32.0F, 4.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    RenderHelper.renderBox(8.0F, 12.0F, 10.0F, 4.0F, 12.0F, 64.0F, 32.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(2.0F, 2.0F, 16.0F, 18.0F, 0.0F, 64.0F, 32.0F, 16.0F);
                }
                break;
            case ZOMBIE:
                Minecraft.getMinecraft().renderEngine.bindTexture(zombie);
                if (type == Part_Type.HEAD) {
                    RenderHelper.renderBox(8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.TORSO) {
                    RenderHelper.renderBox(4.0F, 12.0F, 8.0F, 16.0F, 16.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.ARM) {
                    RenderHelper.renderBox(4.0F, 12.0F, 4.0F, 40.0F, 16.0F, 64.0F, 64.0F, 16.0F);
                } else if (type == Part_Type.LEG) {
                    RenderHelper.renderBox(4.0F, 12.0F, 4.0F, 0.0F, 16.0F, 64.0F, 64.0F, 16.0F);
                }
                break;
            default:
                break;
            
        }


        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGore entity) {
        return null;
    }
}
