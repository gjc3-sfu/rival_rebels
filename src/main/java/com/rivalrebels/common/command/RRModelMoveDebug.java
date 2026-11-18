
package com.rivalrebels.common.command;

import com.rivalrebels.client.render.ModelDebugTransform;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RRModelMoveDebug extends CommandBase {

    @Override
    public String getName() {
        return "rrmodeldebug";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/rrmodeldebug <translate|rotate|add|reset|status|print>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {

        if (args.length == 1) {

            String command = args[0].toLowerCase();

            if (command.equals("reset")) {
                ModelDebugTransform.reset();
                sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.transforms_reset")
                    .setStyle(new Style().setColor(TextFormatting.GREEN)));
                return;
            }

            if (command.equals("status")) {
                printStatus(sender);
                return;
            }

            if (command.equals("print")) {
                printCode(sender);
                return;
            }
        }

        if (args.length != 3) {
            sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.usage", getUsage(sender))
                .setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        String operation = args[0].toLowerCase();
        String axis = args[1].toLowerCase();

        float value;

        try {
            value = Float.parseFloat(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.invalid_number")
                .setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        boolean translate = operation.equals("translate");
        boolean rotate = operation.equals("rotate");
        boolean add = operation.equals("add");

        if (!translate && !rotate && !add) {
            sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.invalid_operation")
                .setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        if (!axis.equals("x") && !axis.equals("y") && !axis.equals("z")) {
            sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.invalid_axis")
                .setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        if (translate || add) {
            switch (axis) {
                case "x":
                    ModelDebugTransform.translateX =
                        add ? ModelDebugTransform.translateX + value : value;
                    break;

                case "y":
                    ModelDebugTransform.translateY =
                        add ? ModelDebugTransform.translateY + value : value;
                    break;

                case "z":
                    ModelDebugTransform.translateZ =
                        add ? ModelDebugTransform.translateZ + value : value;
                    break;
            }
        } else {
            switch (axis) {
                case "x":
                    ModelDebugTransform.rotateX =
                        add ? ModelDebugTransform.rotateX + value : value;
                    break;

                case "y":
                    ModelDebugTransform.rotateY =
                        add ? ModelDebugTransform.rotateY + value : value;
                    break;

                case "z":
                    ModelDebugTransform.rotateZ =
                        add ? ModelDebugTransform.rotateZ + value : value;
                    break;
            }
        }

        sender.sendMessage(new TextComponentTranslation(
            "rivalrebels.command.model.updated",
            operation,
            axis,
            getValue(translate || add, axis)
        ).setStyle(new Style().setColor(TextFormatting.GREEN)));
    }

    private float getValue(boolean translation, String axis) {
        if (translation) {
            switch (axis) {
                case "x": return ModelDebugTransform.translateX;
                case "y": return ModelDebugTransform.translateY;
                case "z": return ModelDebugTransform.translateZ;
            }
        } else {
            switch (axis) {
                case "x": return ModelDebugTransform.rotateX;
                case "y": return ModelDebugTransform.rotateY;
                case "z": return ModelDebugTransform.rotateZ;
            }
        }

        return 0;
    }

    private void printStatus(ICommandSender sender) {
        sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.title")
            .setStyle(new Style().setColor(TextFormatting.YELLOW)));

        sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.translation_label")
            .setStyle(new Style().setColor(TextFormatting.AQUA))
            .appendSibling(new TextComponentString(
                " " + ModelDebugTransform.translateX + ", "
                    + ModelDebugTransform.translateY + ", "
                    + ModelDebugTransform.translateZ
            ).setStyle(new Style().setColor(TextFormatting.WHITE))));

        sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.rotation_label")
            .setStyle(new Style().setColor(TextFormatting.AQUA))
            .appendSibling(new TextComponentString(
                " " + ModelDebugTransform.rotateX + ", "
                    + ModelDebugTransform.rotateY + ", "
                    + ModelDebugTransform.rotateZ
            ).setStyle(new Style().setColor(TextFormatting.WHITE))));
    }

    private void printCode(ICommandSender sender) {
        sender.sendMessage(new TextComponentTranslation("rivalrebels.command.model.copy_renderer")
            .setStyle(new Style().setColor(TextFormatting.GREEN)));

        sender.sendMessage(new TextComponentTranslation(
            "rivalrebels.command.model.translate_code",
            ModelDebugTransform.translateX,
            ModelDebugTransform.translateY,
            ModelDebugTransform.translateZ
        ).setStyle(new Style().setColor(TextFormatting.WHITE)));

        sender.sendMessage(new TextComponentTranslation(
            "rivalrebels.command.model.rotate_code",
            ModelDebugTransform.rotateX,
            1,
            0,
            0
        ).setStyle(new Style().setColor(TextFormatting.WHITE)));

        sender.sendMessage(new TextComponentTranslation(
            "rivalrebels.command.model.rotate_code",
            ModelDebugTransform.rotateY,
            0,
            1,
            0
        ).setStyle(new Style().setColor(TextFormatting.WHITE)));

        sender.sendMessage(new TextComponentTranslation(
            "rivalrebels.command.model.rotate_code",
            ModelDebugTransform.rotateZ,
            0,
            0,
            1
        ).setStyle(new Style().setColor(TextFormatting.WHITE)));
    }

    @Override
    public List<String> getTabCompletions(
        MinecraftServer server,
        ICommandSender sender,
        String[] args,
        BlockPos pos
    ) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(
                args,
                "translate", "rotate", "add", "reset", "status", "print"
            );
        }

        if (args.length == 2 &&
            (args[0].equalsIgnoreCase("translate") ||
             args[0].equalsIgnoreCase("rotate") ||
             args[0].equalsIgnoreCase("add"))) {

            return getListOfStringsMatchingLastWord(args, "x", "y", "z");
        }

        if (args.length == 3 &&
            (args[0].equalsIgnoreCase("translate") ||
             args[0].equalsIgnoreCase("rotate") ||
             args[0].equalsIgnoreCase("add"))) {

            return getListOfStringsMatchingLastWord(
                args, "0", "0.01", "0.1", "0.5", "1", "5", "15", "45", "90"
            );
        }

        return Collections.emptyList();
    }
}
