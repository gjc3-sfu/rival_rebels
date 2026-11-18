package com.rivalrebels.common.packet;

import com.rivalrebels.common.init.FileRW;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TextPacket implements IMessage {
    public String text;
    public TextFormatting format;
    public boolean isFormatted = false;
    public TextPacket(){

    }
    public TextPacket(String text){
        this.text = text;
    }
    public TextPacket(TextFormatting formatting, String text){
        this.text = text;
        this.format = formatting;
        this.isFormatted = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        byte[] dst = new byte[buf.readInt()];
        buf.readBytes(dst);
        text = FileRW.getStringBytes(dst);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(text.length());
        buf.writeBytes(FileRW.getBytesString(text));
    }
    public static class Handler implements IMessageHandler<TextPacket, IMessage> {

        @Override
        public IMessage onMessage(TextPacket m, MessageContext ctx) {
            if (m.text.startsWith("-t"))
            {
                String[] str = m.text.substring(2, m.text.length()).split("\n");
                for (String s : str)
                    Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(s), false);
            }
            else
            {
                String[] s = m.text.split(" ");
                ITextComponent message = new TextComponentString("");
                if (m.isFormatted) {
                    message.setStyle(new Style().setColor(m.format));
                }
                for (String value : s) {
                    message.appendSibling(new TextComponentTranslation(value));
                    message.appendSibling(new TextComponentString(" "));
                }
                Minecraft.getMinecraft().player.sendStatusMessage(message, false);
            }
            return null;
        }
    }
}
