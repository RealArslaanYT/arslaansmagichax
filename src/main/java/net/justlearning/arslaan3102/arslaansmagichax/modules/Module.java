package net.justlearning.arslaan3102.arslaansmagichax.modules;

import net.minecraft.client.MinecraftClient;

public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    public MinecraftClient mc;
    private final int glfwKeybinding;

    private boolean enabled = false;

    public Module(String name, String description, int glfwKeybinding, Category category) {
        this.name = name;
        this.description = description;
        this.mc = MinecraftClient.getInstance();
        this.glfwKeybinding = glfwKeybinding;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getGlfwKeybinding() {
        return glfwKeybinding;
    }

    public Category getCategory() {
        return category;
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