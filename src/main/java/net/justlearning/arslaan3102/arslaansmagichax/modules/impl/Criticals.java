package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.justlearning.arslaan3102.arslaansmagichax.events.PacketEvent;
import net.justlearning.arslaan3102.arslaansmagichax.events.SendPacketEventBus;
import net.justlearning.arslaan3102.arslaansmagichax.mixininterface.IPacket;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.PlayerInput;

import java.util.Objects;

public class Criticals extends Module {
    private static ModeSetting mode = new ModeSetting("Mode", "Packet", "Packet", "Bypass");

    public Criticals() {
        super("Criticals", "Always gives critical hits.", -1, Category.COMBAT);
        SendPacketEventBus.register(Criticals::packetEventListener);
        addSetting(mode);
    }

    private static void packetEventListener(PacketEvent.Send event) {
        if (!ArslaansMagicHaxClient.criticals.isEnabled() || mc.player == null) return;

        String obamaHaveDih = "barapbarapba";  // kinda put it in mbappe

        Packet<?> packet = event.packet();

        // mojang did a mojang thing and you cant detect if packet is InteractType.ATTACK anymore so just assume it is and pray it works
        if (packet instanceof PlayerInteractEntityC2SPacket && ((IPacket) packet).hax$getTag() != 1337) {
            event.setCancelled(true);

            if (Objects.equals(mode.getMode(), "Packet")) {
                // fake not sprinting if sprinting so it lets you crit
                boolean wasSprinting = false;

                if (mc.player.isSprinting()) {
                    PlayerInput fakeNoSprint = new PlayerInput(false, false, false, false, false, false, false);
                    mc.player.networkHandler.sendPacket(new PlayerInputC2SPacket(fakeNoSprint));

                    ClientCommandC2SPacket fakeNoSprintCommand = new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING);
                    mc.player.networkHandler.sendPacket(fakeNoSprintCommand);

                    wasSprinting = true;
                }

                sendYPacket(0.0625);
                sendYPacket(0);

                ((IPacket) packet).hax$setTag(1337);
                mc.player.networkHandler.sendPacket(packet);

                // send sprinting packet again just in case it stopped the player from sprinting client-side (happens)
                if (wasSprinting) {
                    PlayerInput fakeNoSprint = new PlayerInput(false, false, false, false, false, false, true);
                    mc.player.networkHandler.sendPacket(new PlayerInputC2SPacket(fakeNoSprint));

                    ClientCommandC2SPacket fakeNoSprintCommand = new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING);
                    mc.player.networkHandler.sendPacket(fakeNoSprintCommand);

                    mc.player.setSprinting(true);
                }
            } else if (Objects.equals(mode.getMode(), "Bypass")) {
                // don't do the sprint bypass and make it more random-looking to throw off anticheat
                sendYPacket(0.11);
                sendYPacket(0.1100013579);
                sendYPacket(0.0000013579);
            }
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
