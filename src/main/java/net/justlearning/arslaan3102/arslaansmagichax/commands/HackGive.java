package net.justlearning.arslaan3102.arslaansmagichax.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HackGive {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("hackgive")
                .then(ClientCommandManager.argument("item", StringArgumentType.string())
                        .then(ClientCommandManager.argument("amount", IntegerArgumentType.integer(1))
                                .executes(HackGive::run)
                        )
                )
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return 0;

        String itemId = StringArgumentType.getString(context, "item");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        Item item = Registries.ITEM.get(Identifier.of(itemId));
        if (item == null) {
            context.getSource().sendError(Text.literal("Unknown item: " + itemId));
            return 0;
        }

        ItemStack stack = new ItemStack(item, amount);
        client.player.getInventory().insertStack(stack);
        return 1;
    }
}
