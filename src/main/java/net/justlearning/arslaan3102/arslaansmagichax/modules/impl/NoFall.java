package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Negates fall damage.", -1, Category.PLAYER);
    }

    @Override
    public void tick() {
        if (this.isEnabled()) {
            if (mc.player != null) {
                if (!mc.player.isOnGround() && mc.player.fallDistance > 3) {
                    // magic hax
                    PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.000000001, mc.player.getZ(),
                            mc.player.getYaw(), mc.player.getPitch(), false, mc.player.horizontalCollision);
                    mc.player.networkHandler.sendPacket(packet);
                }
            }
        }
    }
}
