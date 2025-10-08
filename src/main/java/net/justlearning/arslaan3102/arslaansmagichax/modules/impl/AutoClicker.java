package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Objects;
import java.util.Random;

public class AutoClicker extends Module {
    private final NumberSetting cps = new NumberSetting("CPS", 5, 50, 12, 0.1); // default 10 CPS
    private final ModeSetting mode = new ModeSetting("Mode", "NoDelay", "CPS", "NoDelay");
    private long nextClickTime = 0L;

    public AutoClicker() {
        super("AutoClicker", "Automatically attacks entities under your crosshair.", -1, Category.COMBAT);
        addSetting(mode);
        addSetting(cps);
    }

    @Override
    public void tick() {
        if (!isEnabled() || mc.player == null || mc.world == null) return;
        if (mc.currentScreen != null) return;

        HitResult hr = mc.crosshairTarget;
        if (!(hr instanceof EntityHitResult eResult)) return;

        Entity target = eResult.getEntity();
        if (target == null || !target.isAlive()) return;

        if (!Objects.equals(mode.getMode(), "NoDelay")) {
            long now = System.currentTimeMillis();
            if (now < nextClickTime) return;

            double delayMs = 1000.0 / cps.getValue();
            nextClickTime = now + (long) Math.max(1, delayMs);
        }

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
