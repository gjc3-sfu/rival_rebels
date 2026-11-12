package com.rivalrebels.client.guiLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.guihelper.GuiCustomButton;
import com.rivalrebels.client.guihelper.GuiScroll;
import com.rivalrebels.client.guihelper.Rectangle;
import com.rivalrebels.client.guihelper.Vector;
import com.rivalrebels.client.oldstuff.Tessellator;
import com.rivalrebels.common.container.ReactorContainer;
import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.noise.RivalRebelsSimplexNoise;
import com.rivalrebels.common.packet.PacketDispatcher;
import com.rivalrebels.common.tileentity.ReactorTileEntity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiReactor extends GuiContainer {

    protected ReactorTileEntity tile_reactor_ref;

    private Block[] toggleable_machines = new Block[]{RRBlocks.reactive, RRBlocks.reactive};

    private static ResourceLocation texture = new ResourceLocation(ModInfo.modid, "textures/gui/reactor.png");
    private GuiCustomButton power_button;
    private GuiCustomButton eject_button;
    private GuiScroll scroll_bar;

      

    public GuiReactor(IInventory player_inventory, IInventory reactor_inv) {
        super(new ReactorContainer(player_inventory, reactor_inv));
        this.ySize = 200;

    }

    public void initGui() {
        super.initGui();
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;
        //this.scroll = new GuiScroll(0, posX + 236, posY + 127, 60);
        this.power_button = new GuiCustomButton(1, new Rectangle(posX + 70, posY + 164, 22, 22), texture, new Vector(212, 0), true);
        this.eject_button = new GuiCustomButton(2, new Rectangle(posX + 164, posY + 164, 22, 22), texture, new Vector(234, 0), false);

    }

    protected float lerp(float f1, float f2, float f3) {
        return f1 * f3 + f2 * (1.0F - f3);
    }

    protected void drawNoiseSphere(
        float startRed,
        float startGreen,
        float startBlue,
        float endRed,
        float endGreen,
        float endBlue,
        float animationFrame,
        int noiseOctaves,
        int sphereRadius,
        int outerRadius,
        float renderResolution,
        float noiseScale,
        float startingColor
    ) 
    {
        Tessellator tessellator = Tessellator.instance;
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);

        GL11.glPushMatrix();
        GL11.glPointSize(scaledResolution.getScaleFactor() / renderResolution);
        GL11.glDisable(3553);
        GL11.glEnable(3042);

        tessellator.startDrawing(0);

        sphereRadius = (int)(sphereRadius * renderResolution);

        int maxRadius = (int)(sphereRadius + outerRadius * renderResolution);
        int fadeDistance = maxRadius - sphereRadius;

        float centerY = this.height / 2 - 45;
        float centerX = this.width / 2;
        float squaredRadius = sphereRadius * sphereRadius;

        for (int offsetX = 1 - maxRadius; offsetX < maxRadius; offsetX++) {
            int squaredX = offsetX * offsetX;
            double screenX = (double)offsetX / renderResolution + centerX;

            for (int offsetY = 1 - maxRadius; offsetY < maxRadius; offsetY++) {
                int squaredDistance = offsetY * offsetY + squaredX;
                double screenY = (double)offsetY / renderResolution + centerY;
                double distanceFromCenter = Math.sqrt(squaredDistance);

                if (distanceFromCenter >= sphereRadius && distanceFromCenter < maxRadius) {
                    float noiseValue = 0.0F;
                    float amplitude = 1.0F;
                    float currentScale = noiseScale;

                    for (int octave = 0; octave < noiseOctaves; octave++) {
                        noiseValue += (float)(
                            (1.0 + RivalRebelsSimplexNoise.noise(
                                screenX * currentScale,
                                screenY * currentScale,
                                animationFrame * currentScale
                            )) / 2.0
                        ) * amplitude;

                        currentScale *= 2.0F;
                        amplitude /= 2.0F;
                    }

                    noiseValue = (float)(
                        noiseValue * (1.0 - (distanceFromCenter - sphereRadius) / fadeDistance)
                    );

                    tessellator.setColorRGBA_F(
                        this.lerp(startRed, endRed, noiseValue),
                        this.lerp(startGreen, endGreen, noiseValue),
                        this.lerp(startBlue, endBlue, noiseValue),
                        noiseValue
                    );

                    tessellator.addVertex(screenX, screenY, 4.0);
                } else {
                    double depth = Math.sqrt(squaredRadius - squaredDistance) / renderResolution;

                    float noiseValue = startingColor;
                    float amplitude = 1.0F;
                    float currentScale = noiseScale;

                    for (int octave = 0; octave < noiseOctaves; octave++) {
                        noiseValue += (float)(
                            (1.0 + RivalRebelsSimplexNoise.noise(
                                screenX * currentScale,
                                screenY * currentScale,
                                depth * currentScale,
                                animationFrame * currentScale
                            )) / 2.0
                        ) * amplitude;

                        currentScale *= 2.0F;
                        amplitude /= 2.0F;
                    }

                    tessellator.setColorRGBA_F(
                        this.lerp(startRed, endRed, noiseValue),
                        this.lerp(startGreen, endGreen, noiseValue),
                        this.lerp(startBlue, endBlue, noiseValue),
                        noiseValue
                    );

                    tessellator.addVertex(screenX, screenY, 4.0);
                }
            }
        }

        tessellator.draw();

        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (this.tile_reactor_ref.isInvalid()) {
            Minecraft.getMinecraft().currentScreen = null;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.drawTexturedModalRect(this.width / 2 - 89, this.height / 2 - 103, 0, 0, 212, 208);
        float scroll_proportion = (float)this.scroll_bar.scroll / this.scroll_bar.limit;
        int off = (int)Math.floor((this.toggleable_machines.length - 2) * scroll_proportion);
        if (off < 0) {
            off = 0;
        }

        int X = 89 + this.width / 2;
        int Y = 0 + this.height / 2;

        /* 
        // Show 4 slots
        for (int i = 0; i < 4; i++) {
            if (off + i >= this.toggleable_machines.length) {
                this.machines[i] = null;
            } else {
                boolean current = new Rectangle(X, Y, 15, 15).isVecInside(new Vector(mouseX, mouseY)); // whether it's in the bound
                if (off + i < this.toggleable_machines.length && off + i >= 0) {
                this.machines[i] = this.toggleable_machines[off + i];
                this.onmachines[i] = this.enabledmachines[off + i];
                if (Mouse.isButtonDown(0) && current) {
                    this.enabledmachines[off + i] = !this.prevenabledmachines[off + i];
                } else {
                    this.prevenabledmachines[off + i] = this.enabledmachines[off + i];
                }
                } else {
                this.machines[i] = null;
                this.onmachines[i] = false;
                }

                Y += 18;
            }
        }
        */

    }


    /* 
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        GL11.glPushMatrix();
        GL11.glScalef(1.25F, 1.0F, 1.0F);
        if (this.inventorySlots instanceof ContainerReactor) {
            ((ContainerReactor)this.inventorySlots).core.locked = this.ter.on;
            ((ContainerReactor)this.inventorySlots).fuel.locked = this.ter.fuel != null && this.ter.on;
        }

        this.fontRendererObj.drawString("ToKaMaK", 10, 8, 4473924);
        GL11.glPopMatrix();
        this.fontRendererObj.drawString("Teslas: " + df.format(this.ter.getPower() - this.ter.consumed), 120, 8, 16777215);
        this.fontRendererObj.drawString("Output/t: " + df.format(this.ter.lasttickconsumed), 140, 18, 16777215);
        int mousex = par1;
        int mousey = par2;
        int posx = (this.width - this.xSize) / 2;
        int posy = (this.height - this.ySize) / 2;
        int coordx = posx + 53;
        int coordy = posy + 191;
        int widthx = 72;
        int widthy = 8;
        if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy) {
            mousex -= posx;
            mousey -= posy;
            this.drawGradientRect(mousex, mousey, mousex + this.fontRendererObj.getStringWidth("rivalrebels.com") + 3, mousey + 12, -1441722095, -1441722095);
            this.fontRendererObj.drawString("rivalrebels.com", mousex + 2, mousey + 2, 16777215);
            if (Desktop.isDesktopSupported() && !this.buttondown && Mouse.isButtonDown(0)) {
                try {
                Desktop.getDesktop().browse(new URI("http://rivalrebels.com"));
                } catch (IOException e) {
                e.printStackTrace();
                } catch (URISyntaxException e) {
                e.printStackTrace();
                }
            }
        }

        this.buttondown = Mouse.isButtonDown(0);
    }
    */

    /*
    protected void drawDock() {
        int X = 89 + this.width / 2;
        int Y = 0 + this.height / 2;

        for (int i = 0; i < 4; i++) {
            if (this.machines[i] == null) {
                return;
            }

            Block display = this.machines[i];
            int meta = 2;
            String toppath = "Minecraft:textures/blocks/" + display.getIcon(1, meta).getIconName() + ".png";
            String lsidepath = "Minecraft:textures/blocks/" + display.getIcon(4, meta).getIconName() + ".png";
            String rsidepath = "Minecraft:textures/blocks/" + display.getIcon(2, meta).getIconName() + ".png";
            if (toppath.contains("RivalRebels:")) {
                toppath = "RivalRebels:textures/blocks/" + display.getBlockTextureFromSide(1).getIconName().split("RivalRebels:")[1] + ".png";
            }

            if (lsidepath.contains("RivalRebels:")) {
                lsidepath = "RivalRebels:textures/blocks/" + display.getBlockTextureFromSide(4).getIconName().split("RivalRebels:")[1] + ".png";
            }

            if (rsidepath.contains("RivalRebels:")) {
                rsidepath = "RivalRebels:textures/blocks/" + display.getBlockTextureFromSide(2).getIconName().split("RivalRebels:")[1] + ".png";
            }

            Tessellator tessellator = Tessellator.instance;
            GL11.glEnable(3042);
            float alpha = 0.5F;
            if (this.onmachines[i]) {
                alpha = 1.0F;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            this.mc.renderEngine.bindTexture(new ResourceLocation(toppath));
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(X + 1, Y + 3.5, this.zLevel, 0.0, 1.0);
            tessellator.addVertexWithUV(X + 8, Y + 7, this.zLevel, 0.0, 0.0);
            tessellator.addVertexWithUV(X + 15, Y + 3.5, this.zLevel, 1.0, 0.0);
            tessellator.addVertexWithUV(X + 8, Y, this.zLevel, 1.0, 1.0);
            tessellator.draw();
            GL11.glColor4f(0.666F, 0.666F, 0.666F, alpha);
            this.mc.renderEngine.bindTexture(new ResourceLocation(lsidepath));
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(X + 1, Y + 3.5, this.zLevel, 0.0, 0.0);
            tessellator.addVertexWithUV(X + 1, Y + 12.5, this.zLevel, 0.0, 1.0);
            tessellator.addVertexWithUV(X + 8, Y + 16, this.zLevel, 1.0, 1.0);
            tessellator.addVertexWithUV(X + 8, Y + 7, this.zLevel, 1.0, 0.0);
            tessellator.draw();
            GL11.glColor4f(0.5F, 0.5F, 0.5F, alpha);
            this.mc.renderEngine.bindTexture(new ResourceLocation(rsidepath));
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(X + 15, Y + 12.5, this.zLevel, 1.0, 1.0);
            tessellator.addVertexWithUV(X + 15, Y + 3.5, this.zLevel, 1.0, 0.0);
            tessellator.addVertexWithUV(X + 8, Y + 7, this.zLevel, 0.0, 0.0);
            tessellator.addVertexWithUV(X + 8, Y + 16, this.zLevel, 0.0, 1.0);
            tessellator.draw();
            GL11.glDisable(3042);
            Y += 18;
        }
    }

    */

    

    
}
