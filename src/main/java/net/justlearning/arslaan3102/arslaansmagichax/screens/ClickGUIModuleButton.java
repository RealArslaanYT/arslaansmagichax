package net.justlearning.arslaan3102.arslaansmagichax.screens;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.Color;

public class ClickGUIModuleButton {

    public Module module;
    public ClickGUIFrame parent;
    public int offset;

    public ClickGUIModuleButton(Module module, ClickGUIFrame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int alpha = isHovered(mouseX, mouseY) ? 230 : 160;
        context.fill(parent.x, parent.y + offset,  parent.x + parent.width, parent.y + offset + parent.height, new Color(30, 30, 47, alpha).getRGB());

        String text = module.getName();

        int textX = parent.x + 5;
        int textY = parent.y + offset + (parent.height - parent.mc.textRenderer.fontHeight) / 2;

        int textColor = module.isEnabled() ? new Color(252, 210, 77, 255).getRGB() : Color.white.getRGB();

        context.drawTextWithShadow(parent.mc.textRenderer, Text.literal(text), textX, textY, textColor);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            module.toggle();
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }
}
