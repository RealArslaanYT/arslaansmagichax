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
import net.minecraft.inventory.StackWithSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.storage.WriteView;

public class HackXP {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("hackxp")
                .then(ClientCommandManager.argument("xp", IntegerArgumentType.integer())
                .executes(HackXP::run)
                )
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        int xp = IntegerArgumentType.getInteger(context,"xp");

        if (MinecraftClient.getInstance().player == null) return -1;
        MinecraftClient.getInstance().player.experienceLevel += xp;

        return 1;
    }
}
