package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.justlearning.arslaan3102.arslaansmagichax.events.SendPacketEventBus;
import net.justlearning.arslaan3102.arslaansmagichax.events.PacketEvent;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.text.Text;

public class NoPackets extends Module {
    public NoPackets() {
        super("NoPackets", "Stops all outgoing packets from being sent. ONLY USE THIS IF YOU KNOW WHAT YOU ARE DOING!", -1, Category.MISC);
        SendPacketEventBus.register(NoPackets::packetEventListener);
    }

    private static void packetEventListener(PacketEvent.Send event) {
        if (!ArslaansMagicHaxClient.noPackets.isEnabled()) return;

        if (mc.player != null) mc.player.sendMessage(Text.literal("Cancelled outgoing packet: " + event.packet().getClass().getSimpleName() + " -> " + event.packet()), false);

        event.setCancelled(true);
    }
}
