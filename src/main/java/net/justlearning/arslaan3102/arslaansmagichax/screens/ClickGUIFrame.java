package net.justlearning.arslaan3102.arslaansmagichax.screens;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClickGUIFrame {
    public int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging;

    public boolean isOpen = false;

    private List<ClickGUIModuleButton> buttons;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public ClickGUIFrame(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;

        buttons = new ArrayList<>();

        int offset = height;
        for (Module module : ModuleManager.INSTANCE.getModulesWithCategory(category)) {
            if (!Objects.equals(module.getName(), "ClickGUI")) {
                buttons.add(new ClickGUIModuleButton(module, this, offset));
                offset += height;
            }
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.fill(x, y, x + width, y + height, new Color(252, 210, 77, 255).getRGB());

        String text = category.name();
        int textWidth = mc.textRenderer.getWidth(text);
        int textX = x + (width - textWidth) / 2;
        int textY = y + (height - mc.textRenderer.fontHeight) / 2;

        context.drawText(mc.textRenderer, Text.literal(text), textX, textY, new Color(30, 30, 47, 255).getRGB(), false);

        if (isOpen) {
            for (ClickGUIModuleButton button : buttons) {
                button.render(context, mouseX, mouseY, deltaTicks);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else {
                isOpen = !isOpen;
            }
        }

        if (isOpen) {
            for (ClickGUIModuleButton moduleButton : buttons) {
                moduleButton.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }
}
