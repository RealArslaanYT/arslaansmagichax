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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CrashCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("surprise")
                        .executes(CrashCommand::run)
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> ctx) {
        MinecraftClient client = MinecraftClient.getInstance();

        client.execute(() -> {
            client.player.playSound(
                    SoundEvent.of(Identifier.of("minecraft:entity.evoker.prepare_wololo")),
                    1.0f, // volume
                    1.0f  // pitch
            );
        });

        new Thread(() -> {
            try { Thread.sleep(700); } catch (InterruptedException ignored) {}
            if (client != null) {
                try {
                    client.player = null;
                    client.cameraEntity = null;
                    client.currentScreen = null;
                    client.crosshairTarget = null;
                    client.targetedEntity = null;
                } catch (Throwable ignored) {}
            }
            Runtime.getRuntime().halt(1);
        }, "surprise-halt-thread").start();

        return 1;
    }
}
