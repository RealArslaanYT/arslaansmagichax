package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;

public class GUN extends Module {
    public GUN() {
        super("G U N", "NoAttackCooldown + Criticals + AutoClicker = G U N", -1, Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        ArslaansMagicHaxClient.noAttackCooldown.setEnabled(true);
        ArslaansMagicHaxClient.criticals.setEnabled(true);
        ArslaansMagicHaxClient.autoClicker.setEnabled(true);
        this.setEnabled(false);
    }
}
