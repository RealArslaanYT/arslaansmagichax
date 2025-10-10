package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.justlearning.arslaan3102.arslaansmagichax.events.PacketEvent;
import net.justlearning.arslaan3102.arslaansmagichax.events.ReceivePacketEventBus;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onHandlePacket(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof BundleS2CPacket bundle) {
            for (Iterator<Packet<? super ClientPlayPacketListener>> it = bundle.getPackets().iterator(); it.hasNext(); ) {
                Packet<? super ClientPlayPacketListener> innerPacket = it.next();
                PacketEvent.Receive ev = new PacketEvent.Receive(innerPacket);
                ReceivePacketEventBus.post(ev);
                if (ev.isCancelled()) it.remove();
            }
        } else {
            PacketEvent.Receive ev = new PacketEvent.Receive(packet);
            ReceivePacketEventBus.post(ev);
            if (ev.isCancelled()) ci.cancel();
        }
    }
}

