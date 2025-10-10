package net.justlearning.arslaan3102.arslaansmagichax.events;

import net.minecraft.network.packet.Packet;

public final class PacketEvent {
    private PacketEvent() {}

    public static final class Send {
        private final Packet<?> packet;
        private boolean cancelled;

        public Send(Packet<?> packet) { this.packet = packet; }

        public Packet<?> packet() { return packet; }
        public void setCancelled(boolean c) { cancelled = c; }
        public boolean isCancelled() { return cancelled; }
    }

    public static final class Receive {
        private final Packet<?> packet;
        private boolean cancelled;

        public Receive(Packet<?> packet) { this.packet = packet; }

        public Packet<?> packet() { return packet; }
        public void setCancelled(boolean c) { cancelled = c; }
        public boolean isCancelled() { return cancelled; }
    }
}