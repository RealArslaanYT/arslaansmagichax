package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class GUN extends Module {
    public GUN() {
        super("G U N", "NoAttackCooldown + Criticals + AutoClicker = G U N", -1, Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        ArslaansMagicHaxClient.clickGUI.setEnabled(false);
        ArslaansMagicHaxClient.noAttackCooldown.setEnabled(true);
        ArslaansMagicHaxClient.criticals.setEnabled(true);
        ArslaansMagicHaxClient.autoClicker.setEnabled(true);
        this.setEnabled(false);
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("[G U N] Enabled NoAttackCooldown, Criticals, AutoClicker"), false);
            mc.player.sendMessage(Text.literal("[G U N] Disabled ClickGUI & self (this is a macro module)"), false);
            mc.inGameHud.setTitle(Text.literal("G U N").formatted(Formatting.RED));
            mc.inGameHud.setSubtitle(Text.literal("THE POWER").formatted(Formatting.YELLOW));
            mc.player.sendMessage(Text.literal("Tip: when using G U N, if there is no anticheat set AutoClicker mode to NoDelay"), true);
            mc.player.playSound(SoundEvent.of(Identifier.of("minecraft:entity.evoker.prepare_wololo")), 100, 1);
        }
    }
}
