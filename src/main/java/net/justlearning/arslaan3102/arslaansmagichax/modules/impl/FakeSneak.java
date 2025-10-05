package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.PlayerInput;

public class FakeSneak extends Module {
    public FakeSneak() {
        super("Fake Sneak", "Makes you appear as sneaking to the server when you're not. Can be used to hide nametags.", -1, Category.MOVEMENT);
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;
        PlayerInput fakeSneak = new PlayerInput(false, false, false, false, false, true, false);
        mc.player.networkHandler.sendPacket(new PlayerInputC2SPacket(fakeSneak));
    }
}
