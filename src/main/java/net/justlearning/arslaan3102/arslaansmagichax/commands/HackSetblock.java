package net.justlearning.arslaan3102.arslaansmagichax.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class HackSetblock {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("hacksetblock")
                .then(ClientCommandManager.argument("block", StringArgumentType.string())
                        // Case: just block -> place at player's feet
                        .executes(HackSetblock::runAtFeet)

                        // Case: block + xyz -> place at given position
                        .then(ClientCommandManager.argument("x", IntegerArgumentType.integer())
                                .then(ClientCommandManager.argument("y", IntegerArgumentType.integer())
                                        .then(ClientCommandManager.argument("z", IntegerArgumentType.integer())
                                                .executes(HackSetblock::runAtPos)
                                        )
                                )
                        )
                )
        );
    }

    private static int runAtFeet(CommandContext<FabricClientCommandSource> context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return 0;

        String blockId = StringArgumentType.getString(context, "block");
        Block block = Registries.BLOCK.get(Identifier.of(blockId));
        if (block == null) {
            context.getSource().sendError(Text.literal("Unknown block: " + blockId));
            return 0;
        }

        BlockPos pos = client.player.getBlockPos();
        BlockState blockState = block.getDefaultState();

        client.world.setBlockState(pos, blockState);

        return 1;
    }

    private static int runAtPos(CommandContext<FabricClientCommandSource> context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return 0;

        String blockId = StringArgumentType.getString(context, "block");
        int x = IntegerArgumentType.getInteger(context, "x");
        int y = IntegerArgumentType.getInteger(context, "y");
        int z = IntegerArgumentType.getInteger(context, "z");

        Block block = Registries.BLOCK.get(Identifier.of(blockId));
        if (block == null) {
            context.getSource().sendError(Text.literal("Unknown block: " + blockId));
            return 0;
        }

        BlockPos pos = new BlockPos(x, y, z);
        BlockState blockState = block.getDefaultState();

        client.world.setBlockState(pos, blockState);

        return 1;
    }
}
