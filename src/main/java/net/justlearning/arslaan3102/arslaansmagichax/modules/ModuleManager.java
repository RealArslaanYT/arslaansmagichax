package net.justlearning.arslaan3102.arslaansmagichax.modules;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private boolean hudRegistered = false;

    private static List<Module> modules = new ArrayList<>();
    public static ModuleManager INSTANCE = new ModuleManager();
    public void register(Module module) {
        modules.add(module);
    }

    public static List<Module> getAllModules() {
        return modules;
    }

    public static List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public static void tick() {
        for (Module module : modules) {
            module.tick();
        }
    }

    public void registerHUD() {
        if (!hudRegistered) {
            HudRenderCallback.EVENT.register(this::onRenderHud);
            hudRegistered = true;
        }
    }

    private void onRenderHud(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        float alpha = 1.0F;
        int i = MathHelper.ceil(alpha * 255.0F) << 24; // I don't know what this dark magic is but it works
        int x = 5;
        int y = 5;

        int titleColor = 16568909 | i; // weird magic

        drawContext.drawTextWithBackground(textRenderer, Text.literal("Arslaan's Magic Hax"), x, y, 50, titleColor);
        y += textRenderer.fontHeight + 2;

        for (Module module : ModuleManager.getEnabledModules()) {
            int color = 16777215 | i; // more magic I don't understand
            drawContext.drawTextWithShadow(textRenderer, Text.literal(module.getName()), x, y, color);
            y += textRenderer.fontHeight + 2;
        }
    }
}
