package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class Flight extends Module {
    private int flyingTickCounter = 0;
    private boolean sentFirstPacket = false;

    public Flight() {
        super("Flight", "Enables creative flight in survival.", GLFW.GLFW_KEY_G, Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        if (mc.player == null) return;
        mc.player.sendMessage(Text.literal("[Flight] It is recommended to use this hack in combination with NoFall."), false);
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;

        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;

        if (flyingTickCounter >= 20) {
            if (!sentFirstPacket) {
                sendYPacket(-0.0625);
                sentFirstPacket = true;
            } else {
                sendYPacket(0);
                sentFirstPacket = false;
                flyingTickCounter = 0;
            }
        }
        flyingTickCounter++;
    }


    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.getAbilities().allowFlying = false;
            mc.player.getAbilities().flying = false;
        }
    }

    private static void sendYPacket(double height) {
        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.PositionAndOnGround(x, y + height, z, false, mc.player.horizontalCollision);

        mc.player.networkHandler.sendPacket(packet);
    }
}
