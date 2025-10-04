package net.justlearning.arslaan3102.arslaansmagichax.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;

public class CrashCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("surprise")
                        .executes(CrashCommand::run)
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) throws CommandSyntaxException {
        // almost guaranteed to cook the entire game
        MinecraftClient.getInstance().player = null;
        MinecraftClient.getInstance().cameraEntity = null;
        MinecraftClient.getInstance().currentScreen = null;
        MinecraftClient.getInstance().crosshairTarget = null;
        MinecraftClient.getInstance().targetedEntity = null;

        return 1;
    }
}
