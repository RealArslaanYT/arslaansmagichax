package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Objects;

public class Fullbright extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Gamma", "Effect", "Gamma", "Effect + Gamma");
    private SimpleOption<Double> option;
    private double previousGamma = 1.0;
    private boolean setPreviousGamma = false;

    public Fullbright() {
        super("Fullbright", "Brightens up your world!", -1, Category.RENDER);
        addSetting(mode);
    }

    @Override
    protected void onEnable() {
        if (mc.options == null) return;
        if (Objects.equals(mode.getMode(), "Gamma")) {
            if (!setPreviousGamma) previousGamma = mc.options.getGamma().getValue();
            setPreviousGamma = true;
            mc.options.getGamma().setValue(1000.0);
        }
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;

        if (Objects.equals(mode.getMode(), "Effect")) {
            mc.options.getGamma().setValue(previousGamma);
            setPreviousGamma = false;
            mc.player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()), 1, 255));
        }
        if (Objects.equals(mode.getMode(), "Gamma")) {
            if (!setPreviousGamma) previousGamma = mc.options.getGamma().getValue();
            setPreviousGamma = true;
            mc.options.getGamma().setValue(1000.0);
        }
        if (Objects.equals(mode.getMode(), "Effect + Gamma")) {
            if (!setPreviousGamma) previousGamma = mc.options.getGamma().getValue();
            setPreviousGamma = true;
            mc.options.getGamma().setValue(1000.0);
            mc.player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()), 1, 255));
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) return;
        if (mc.player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()))) {
            mc.player.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()));
        }

        mc.options.getGamma().setValue(previousGamma);
    }
}
