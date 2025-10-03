package net.justlearning.arslaan3102.arslaansmagichax.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.Queue;

public class HackTP {
    private static final Queue<double[]> stepQueue = new LinkedList<>();

    public static BlockPos findClosestAirNearPosition(int x, int y, int z) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) return null;

        for (int blockY = y; blockY <= world.getHeight(); blockY++) {
            BlockPos pos = new BlockPos(x, blockY, z);
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isAir()) {
                return pos;
            }
        }

        for (int blockY = y; blockY >= 0; blockY--) {
            BlockPos pos = new BlockPos(x, blockY, z);
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isAir()) {
                return pos;
            }
        }
        return null;
    }

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("hacktp")
                .then(ClientCommandManager.argument("x", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("y", IntegerArgumentType.integer())
                                .then(ClientCommandManager.argument("z", IntegerArgumentType.integer())
                                        .executes(HackTP::run)
                                )
                        )
                )
        );

//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (!stepQueue.isEmpty() && client.player != null) {
//                double[] pos = stepQueue.poll();
//
//                client.getNetworkHandler().sendPacket(
//                        new PlayerMoveC2SPacket.PositionAndOnGround(pos[0], pos[1], pos[2], true, client.player.horizontalCollision)
//                );
//
//                client.player.setPos(pos[0], pos[1], pos[2]);
//
//                client.player.sendMessage(Text.literal("Teleported to " + pos[0] + " " + pos[1] + " " + pos[2]), false);
//            }
//        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!stepQueue.isEmpty() && client.player != null) {
                int packetsPerTick = 5; // how many packets you want per tick
                for (int i = 0; i < packetsPerTick && !stepQueue.isEmpty(); i++) {
                    double[] pos = stepQueue.poll();

                    client.getNetworkHandler().sendPacket(
                            new PlayerMoveC2SPacket.PositionAndOnGround(
                                    pos[0], pos[1], pos[2], true, client.player.horizontalCollision
                            )
                    );

                    client.player.setPos(pos[0], pos[1], pos[2]);

                    client.player.sendMessage(Text.literal("Teleported to " + pos[0] + " " + pos[1] + " " + pos[2]), false);
                }
            }
        });
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        int targetX = IntegerArgumentType.getInteger(context, "x");
        int targetY = IntegerArgumentType.getInteger(context, "y");
        int targetZ = IntegerArgumentType.getInteger(context, "z");

        ClientPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFeedback(Text.literal("No player found."));
            return -1;
        }

        double currentX = player.getX();
        double currentY = player.getY();
        double currentZ = player.getZ();

        int stepX = (targetX > currentX) ? 5 : -5;
        int stepY = (targetY > currentY) ? 2 : -2;
        int stepZ = (targetZ > currentZ) ? 5 : -5;

        while ((stepY > 0 && currentY < targetY) || (stepY < 0 && currentY > targetY)) {
            currentY += stepY;
            if ((stepY > 0 && currentY > targetY) || (stepY < 0 && currentY < targetY)) currentY = targetY;
            stepQueue.add(new double[]{currentX, currentY, currentZ});
            context.getSource().sendFeedback(Text.literal(
                    "Queued teleport to " + currentX + " " + currentY + " " + currentZ
            ));
        }

        while ((stepX > 0 && currentX < targetX) || (stepX < 0 && currentX > targetX)) {
            currentX += stepX;
            if ((stepX > 0 && currentX > targetX) || (stepX < 0 && currentX < targetX)) currentX = targetX;

            BlockPos airPos = findClosestAirNearPosition((int) currentX, (int) currentY, (int) currentZ);
            if (airPos != null) currentY = airPos.getY();

            stepQueue.add(new double[]{currentX, currentY, currentZ});
            context.getSource().sendFeedback(Text.literal(
                    "Queued teleport to " + currentX + " " + currentY + " " + currentZ
            ));
        }

        while ((stepZ > 0 && currentZ < targetZ) || (stepZ < 0 && currentZ > targetZ)) {
            currentZ += stepZ;
            if ((stepZ > 0 && currentZ > targetZ) || (stepZ < 0 && currentZ < targetZ)) currentZ = targetZ;

            BlockPos airPos = findClosestAirNearPosition((int) currentX, (int) currentY, (int) currentZ);
            if (airPos != null) currentY = airPos.getY();

            stepQueue.add(new double[]{currentX, currentY, currentZ});
            context.getSource().sendFeedback(Text.literal(
                    "Queued teleport to " + currentX + " " + currentY + " " + currentZ
            ));
        }

        context.getSource().sendFeedback(Text.literal(
                "Queued teleport to " + targetX + " " + targetY + " " + targetZ
        ));

        return 1;
    }
}
