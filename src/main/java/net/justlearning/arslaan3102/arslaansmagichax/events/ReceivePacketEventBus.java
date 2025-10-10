package net.justlearning.arslaan3102.arslaansmagichax.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class ReceivePacketEventBus {
    private static final List<Consumer<PacketEvent.Receive>> listeners = new CopyOnWriteArrayList<>();

    public static void register(Consumer<PacketEvent.Receive> listener) {
        listeners.add(listener);
    }

    public static void post(PacketEvent.Receive ev) {
        for (Consumer<PacketEvent.Receive> l : listeners) l.accept(ev);
    }
}

