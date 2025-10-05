package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;

public class NoAttackCooldown extends Module {
    public NoAttackCooldown() {
        super("NoAttackCooldown", "Removes attack cooldown", -1, Category.COMBAT);
    }

    // removing cooldown handled in PlayerEntityMixin
}
