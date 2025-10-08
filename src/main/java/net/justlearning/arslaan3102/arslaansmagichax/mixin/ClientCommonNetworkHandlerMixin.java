package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.events.SendPacketEventBus;
import net.justlearning.arslaan3102.arslaansmagichax.events.PacketEvent;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {
    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        PacketEvent.Send ev = new PacketEvent.Send(packet);
        SendPacketEventBus.post(ev);

        if (ev.isCancelled()) {
            ci.cancel();
        }
    }
}