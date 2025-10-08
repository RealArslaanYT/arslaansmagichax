package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.mixininterface.IPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerInteractEntityC2SPacket.class)
public abstract class PlayerInteractEntityC2SPacketMixin implements IPacket {
    private int tag = -1;

    @Override
    public void hax$setTag(int value) { tag = value; }

    @Override
    public int hax$getTag() { return tag; }
}