package net.justlearning.arslaan3102.arslaansmagichax.modules;

import net.minecraft.client.MinecraftClient;

public abstract class Module {
    private final String name;
    private final String description;
    public MinecraftClient mc;

    private boolean enabled = false;

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
        this.mc = MinecraftClient.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public void setEnabled(boolean state) {
        if (enabled != state) toggle();
    }

    protected void onEnable() {}
    protected void onDisable() {}

    public void tick() {}
}