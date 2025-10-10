package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Objects;

public class Fullbright extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Gamma", "Effect", "Gamma", "Effect + Gamma", "Break AO Renderer");
    private double previousGamma = 1.0;
    private boolean setPreviousGamma = false;
    private String lastMode = ""; // track previous mode

    public Fullbright() {
        super("Fullbright", "Brightens up your world!", -1, Category.RENDER);
        addSetting(mode);
        lastMode = mode.getMode(); // initialize
    }

    @Override
    protected void onEnable() {
        if (mc.options == null) return;
        applyMode(mode.getMode());

        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;

        String currentMode = mode.getMode();
        if (!Objects.equals(currentMode, lastMode)) {
            applyMode(currentMode);

            if (mc.worldRenderer != null) {
                mc.worldRenderer.reload();
            }

            lastMode = currentMode;
        }

        if (Objects.equals(currentMode, "Effect")) {
            mc.player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()), 1, 255));
        } else if (Objects.equals(currentMode, "Effect + Gamma")) {
            mc.player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()), 1, 255));
        }
    }

    private void applyMode(String modeName) {
        if (Objects.equals(modeName, "Gamma")) {
            if (!setPreviousGamma) previousGamma = mc.options.getGamma().getValue();
            setPreviousGamma = true;
            mc.options.getGamma().setValue(1000.0);
        } else if (Objects.equals(modeName, "Effect + Gamma")) {
            if (!setPreviousGamma) previousGamma = mc.options.getGamma().getValue();
            setPreviousGamma = true;
            mc.options.getGamma().setValue(1000.0);
        } else if (Objects.equals(modeName, "Effect")) {
            mc.options.getGamma().setValue(previousGamma);
            setPreviousGamma = false;
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) return;

        if (mc.player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()))) {
            mc.player.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()));
        }

        mc.options.getGamma().setValue(previousGamma);

        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }
}

