package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;

public class HideModulesHUD extends Module {
    public HideModulesHUD() {
        super("Hide Modules HUD", "Hides the list of modules in the top-left of the HUD.", -1, Category.RENDER);
    }

    // actual shit is in ModuleManager::onRenderHud (if condition to check if this is not enabled)
}
