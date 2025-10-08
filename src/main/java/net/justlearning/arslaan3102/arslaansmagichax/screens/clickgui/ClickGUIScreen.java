package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting.Component;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting.KeybindingSelector;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGUIScreen extends Screen {
    public static final ClickGUIScreen INSTANCE = new ClickGUIScreen();

    private List<Frame> frames;

    private ClickGUIScreen() {
        super(Text.literal("ClickGUI"));

        frames = new ArrayList<>();

        int offset = 20;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, offset, 30, 100, 20));
            offset += 120;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        for (Frame frame : frames) {
            frame.render(context, mouseX, mouseY, deltaTicks);
            frame.updatePosition(mouseX, mouseY);
        }
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Frame frame : frames) {
            for (ModuleButton button : frame.getButtons()) {
                for (Component comp : button.components) {
                    if (comp instanceof KeybindingSelector keybindingSelector) {
                        keybindingSelector.onKeyPressed(keyCode);
                    }
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        super.close();
        ArslaansMagicHaxClient.clickGUI.setEnabled(false);
    }
}
