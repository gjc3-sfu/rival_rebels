package com.rivalrebels.client.guiLoader;

import org.lwjgl.opengl.GL11;

import com.rivalrebels.ModInfo;
import com.rivalrebels.RivalRebels;
import com.rivalrebels.client.guihelper.GuiCustomButton;
import com.rivalrebels.client.guihelper.Rectangle;
import com.rivalrebels.client.guihelper.Vector;
import com.rivalrebels.common.container.ReactorContainer;
import com.rivalrebels.common.tileentity.ReactorTileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiReactor extends GuiContainer {

    protected ReactorTileEntity tile_reactor_ref;

    private static ResourceLocation texture = new ResourceLocation(ModInfo.modid, "textures/gui/reactor.png");
    private GuiCustomButton power_button;
    private GuiCustomButton eject_button;

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

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawGuiContainerBackgroundLayer'");
    }

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
