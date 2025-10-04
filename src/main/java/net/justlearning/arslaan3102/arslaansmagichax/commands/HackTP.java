package net.justlearning.arslaan3102.arslaansmagichax.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
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
    private static int packetsPerTick = 5;

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

    private static int computeSafePacketsPerTick(int requestedSpeed) {
        final int STEP_HORIZ = 2;
        final double SAFE_HORIZ = 8.5; // server maximum horizontal move

        int allowed = (int) Math.floor(SAFE_HORIZ / STEP_HORIZ);
        if (allowed < 1) allowed = 1;

        return Math.max(1, Math.min(requestedSpeed, allowed));
    }

    private static double parseCoordinate(String arg, double current) {
        if (arg.startsWith("c")) {
            if (arg.length() == 1) return current;
            return current + Double.parseDouble(arg.substring(1));
        } else {
            return Double.parseDouble(arg);
        }
    }

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(ClientCommandManager.literal("hacktp")
                .then(ClientCommandManager.argument("x", StringArgumentType.word())
                        .then(ClientCommandManager.argument("y", StringArgumentType.word())
                                .then(ClientCommandManager.argument("z", StringArgumentType.word())
                                        .then(ClientCommandManager.argument("speed", IntegerArgumentType.integer(0, 25))
                                                .executes(HackTP::run)
                                        )
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
                for (int i = 0; i < packetsPerTick && !stepQueue.isEmpty(); i++) {
                    double[] pos = stepQueue.poll();

//                    client.getNetworkHandler().sendPacket(
//                            new PlayerMoveC2SPacket.PositionAndOnGround(
//                                    pos[0], pos[1], pos[2], true, client.player.horizontalCollision
//                            )
//                    );

                    client.player.setPos(pos[0], pos[1], pos[2]);

                    client.player.sendMessage(Text.literal("Teleported to " + pos[0] + " " + pos[1] + " " + pos[2] + " with packetsPerTick/speed " + packetsPerTick), false);
                }
            }
        });
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        int speed = IntegerArgumentType.getInteger(context, "speed");

        ClientPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFeedback(Text.literal("No player found."));
            return -1;
        }

        double currentX = player.getX();
        double currentY = player.getY();
        double currentZ = player.getZ();

        String xArg = context.getArgument("x", String.class);
        String yArg = context.getArgument("y", String.class);
        String zArg = context.getArgument("z", String.class);

        double targetX = parseCoordinate(xArg, currentX);
        double targetY = parseCoordinate(yArg, currentY);
        double targetZ = parseCoordinate(zArg, currentZ);

        if (speed < 1) {
            packetsPerTick = computeSafePacketsPerTick(packetsPerTick);
        } else {
            packetsPerTick = computeSafePacketsPerTick(speed);
        }

        context.getSource().sendFeedback(Text.literal("Using " + packetsPerTick + " packets/tick (requested " + speed + ")"));

        int stepX = (targetX > currentX) ? 2 : -2;
        int stepY = (targetY > currentY) ? 2 : -2;
        int stepZ = (targetZ > currentZ) ? 2 : -2;

        for (int i = 1; i <= 8; i++) {
            stepQueue.add(new double[]{currentX, currentY, currentZ});  // I don't know how this works but it makes Y more reliable (go through more blocks on the Y axis?)
        }
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

        return 1;
    }
}
