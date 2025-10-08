package net.justlearning.arslaan3102.arslaansmagichax.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class SendPacketEventBus {
    private static final List<Consumer<PacketEvent.Send>> listeners = new CopyOnWriteArrayList<>();

    public static void register(Consumer<PacketEvent.Send> listener) {
        listeners.add(listener);
    }

    public static void post(PacketEvent.Send ev) {
        for (Consumer<PacketEvent.Send> l : listeners) l.accept(ev);
    }
}

