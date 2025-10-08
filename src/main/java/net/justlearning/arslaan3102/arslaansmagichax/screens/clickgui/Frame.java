package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.ModuleManager;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting.Component;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    public int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging;

    public boolean isOpen = false;

    private List<ModuleButton> buttons;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;

        buttons = new ArrayList<>();

        int offset = height;
        for (Module module : ModuleManager.INSTANCE.getModulesWithCategory(category)) {
            buttons.add(new ModuleButton(module, this, offset));
            offset += height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.fill(x, y, x + width, y + height, new Color(252, 210, 77, 255).getRGB());

        String text = category.name();
        int textY = y + (height - mc.textRenderer.fontHeight) / 2;

        context.drawText(mc.textRenderer, Text.literal(text), x + 5, textY, new Color(30, 30, 47, 255).getRGB(), false);
        context.drawText(mc.textRenderer, isOpen ? "-" : "+", x + width - 5 - mc.textRenderer.getWidth("+"), textY, new Color(30, 30, 47, 255).getRGB(), false);

        if (isOpen) {
            for (ModuleButton button : buttons) {
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
            for (ModuleButton moduleButton : buttons) {
                moduleButton.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
        }

        if (isOpen) {
            for (ModuleButton moduleButton : buttons) {
                moduleButton.mouseReleased(mouseX, mouseY, button);
            }
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

    public List<ModuleButton> getButtons() {
        return buttons;
    }

    public void updateButtons() {
        int offset = height;

        for (ModuleButton button : buttons) {
            button.offset = offset;
            offset += height;

            if (button.extended) {
                for (Component component : button.components) {
                    if (component.setting != null) {
                        if (component.setting.isVisible()) {
                            offset += height;
                        }
                    } else {
                        // this is 99.99% the keybinding thing, just add offset anyway and hope it works i guess
                        offset += height;
                    }
                }
            }
        }
    }
}
