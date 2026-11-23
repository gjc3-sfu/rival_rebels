package com.rivalrebels.common.packet;

import io.netty.buffer.ByteBuf;
import com.rivalrebels.common.tileentity.ReactorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactorGuiPacket implements IMessage {

    private BlockPos reactorPos;
    private byte type;

    public ReactorGuiPacket() {
    }

    public ReactorGuiPacket(BlockPos reactorPos, byte type) {
        this.reactorPos = reactorPos;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.reactorPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.type = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.reactorPos.getX());
        buf.writeInt(this.reactorPos.getY());
        buf.writeInt(this.reactorPos.getZ());
        buf.writeByte(this.type);
    }

    public static class Handler implements IMessageHandler<ReactorGuiPacket, IMessage> {
        @Override
        public IMessage onMessage(ReactorGuiPacket message, MessageContext context) {
            context.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                if (context.getServerHandler().player.getDistanceSq(
                        message.reactorPos.getX() + 0.5,
                        message.reactorPos.getY() + 0.5,
                        message.reactorPos.getZ() + 0.5
                    ) >= 100.0) {
                    return;
                }

                TileEntity tileEntity = context.getServerHandler().player.world
                    .getTileEntity(message.reactorPos);
                if (!(tileEntity instanceof ReactorTileEntity)) {
                    return;
                }

                ReactorTileEntity reactor = (ReactorTileEntity)tileEntity;
                if (message.type == 0) {
                    reactor.toggleOn();
                } else if (message.type == 1) {
                    reactor.ejectCore();
                }
            });
            return null;
        }
    }
}
