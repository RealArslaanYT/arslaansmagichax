package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.BooleanSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.Setting;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {

    public Module module;
    public Frame parent;
    public int offset;
    public List<Component> components;
    public boolean extended;

    public ModuleButton(Module module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<>();

        int setOffset = parent.height;
        components.add(new KeybindingSelector(this, setOffset));
        setOffset += parent.height;
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, this, setOffset));
            } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, this, setOffset));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, this, setOffset));
            }
            setOffset += parent.height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int alpha = isHovered(mouseX, mouseY) ? 230 : 160;

        context.fill(parent.x, parent.y + offset,  parent.x + parent.width, parent.y + offset + parent.height, new Color(30, 30, 47, alpha).getRGB());

        if (isHovered(mouseX, mouseY)) {
            context.drawTooltip(Text.literal(module.getDescription()), mouseX, mouseY);
        }

        String text = module.getName();

        int textX = parent.x + 5;
        int textY = parent.y + offset + (parent.height - parent.mc.textRenderer.fontHeight) / 2;

        int textColor = module.isEnabled() ? new Color(252, 210, 77, 255).getRGB() : Color.white.getRGB();

        context.drawTextWithShadow(parent.mc.textRenderer, Text.literal(text), textX, textY, textColor);

        if (extended) {
            for (Component component : components) {
                component.render(context, mouseX, mouseY, deltaTicks);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            }
            else if (button == 1) {
                extended = !extended;
                parent.updateButtons();
            }
        }

        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        // luigi wins by doing absolutely nothing

        // nevermind we did something
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }
}
