package org.okunev.scalingmobs.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ScalingMobsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("scalingmobs")
                .then(Commands.literal("reload")
                        .requires(source -> source.hasPermission(2))
                        .executes(ScalingMobsCommand::reloadConfig)
                ));
    }

    private static int reloadConfig(CommandContext<CommandSourceStack> context) {
        ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.COMMON, FMLPaths.CONFIGDIR.get());

        context.getSource().sendSuccess(() -> Component.literal("The ScalingMobs configuration has been reloaded!"), true);
        return Command.SINGLE_SUCCESS;
    }
}