package net.justlearning.arslaan3102.arslaansmagichax.modules;

import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.BooleanSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.Setting;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    public static MinecraftClient mc;
    private int glfwKeybinding;
    private final List<Setting> settings = new ArrayList<>();

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

    public void setGlfwKeybinding(int glfwKeybinding) {
        this.glfwKeybinding = glfwKeybinding;
    }

    protected void onEnable() {}
    protected void onDisable() {}

    public void tick() {}

    protected <T extends Setting> T addSetting(T setting) {
        settings.add(setting);
        return setting;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public Optional<Setting> getSetting(String name) {
        return settings.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<BooleanSetting> getBoolean(String name) {
        return getSetting(name)
                .filter(s -> s instanceof BooleanSetting)
                .map(s -> (BooleanSetting) s);
    }

    public Optional<NumberSetting> getNumber(String name) {
        return getSetting(name)
                .filter(s -> s instanceof NumberSetting)
                .map(s -> (NumberSetting) s);
    }

    public Optional<ModeSetting> getMode(String name) {
        return getSetting(name)
                .filter(s -> s instanceof ModeSetting)
                .map(s -> (ModeSetting) s);
    }
}