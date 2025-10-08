package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.ClickGUIScreen;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "GUI to manage modules.", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.MISC);
    }

    @Override
    protected void onEnable() {
        MinecraftClient.getInstance().setScreen(ClickGUIScreen.INSTANCE);
    }

    @Override
    protected void onDisable() {
        MinecraftClient.getInstance().setScreen(null);
    }
}
