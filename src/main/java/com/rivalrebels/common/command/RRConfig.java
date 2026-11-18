package com.rivalrebels.common.command;

import com.rivalrebels.common.init.RRConfigOptions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RRConfig extends CommandBase {
    @Override
    public String getName() {
        return "rrconfig";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length == 2){
            String str = args[0];
            if(str.equals("enable_old_nuke")){
                String str2 = args[1];
                boolean i = Boolean.parseBoolean(str2);
                String result = i ? "will" : "will not";
                RRConfigOptions.render_old_nuke = i;
                sender.sendMessage(
                    new TextComponentTranslation("rivalrebels.message.rrconfig_result", result)
                        .setStyle(new Style().setColor(TextFormatting.RED))
                );
                return;
            }
        }
        sender.sendMessage(
            new TextComponentTranslation("rivalrebels.message.rrconfig_usage")
                .setStyle(new Style().setColor(TextFormatting.RED))
        );
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> list = new ArrayList<>();
        list.add("render_old_nuke");
        return list;
    }
}
