package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting;

import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class KeybindingSelector extends Component {

    private boolean listening = false;

    public KeybindingSelector(ModuleButton parent, int offset) {
        super(null, parent, offset);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int alpha = isHovered(mouseX, mouseY) ? 230 : 160;
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(30, 30, 47, alpha).getRGB());

        if (listening) {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(252, 210, 77, 255).getRGB());
        }

        String displayText = listening ? "Press a key..." : "Keybind: " + getKeyName(parent.module.getGlfwKeybinding());
        context.drawTextWithShadow(mc.textRenderer, displayText, parent.parent.x + 5, parent.parent.y + parent.offset + offset + (parent.parent.height - mc.textRenderer.fontHeight) / 2, Color.white.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            listening = !listening;
        }
    }

    public void onKeyPressed(int keyCode) {
        if (!listening) return;

        listening = false;

        if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE || keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            // remove bingdingd AHH I CANT SPELL
            parent.module.setGlfwKeybinding(-1);
            return;
        }

        parent.module.setGlfwKeybinding(keyCode);
    }

    private String getKeyName(int key) {
        if (key == -1) return "NONE";
        String name = GLFW.glfwGetKeyName(key, 0);

        if (name != null) return name.toUpperCase();

        return switch (key) {
            case GLFW.GLFW_KEY_LEFT_SHIFT -> "LSHIFT";
            case GLFW.GLFW_KEY_RIGHT_SHIFT -> "RSHIFT";
            case GLFW.GLFW_KEY_LEFT_CONTROL -> "LCTRL";
            case GLFW.GLFW_KEY_RIGHT_CONTROL -> "RCTRL";
            case GLFW.GLFW_KEY_LEFT_ALT -> "LALT";
            case GLFW.GLFW_KEY_RIGHT_ALT -> "RALT";
            case GLFW.GLFW_KEY_SPACE -> "SPACE";
            case GLFW.GLFW_KEY_TAB -> "TAB";
            case GLFW.GLFW_KEY_ENTER -> "ENTER";
            case GLFW.GLFW_KEY_UP -> "UP";
            case GLFW.GLFW_KEY_DOWN -> "DOWN";
            case GLFW.GLFW_KEY_LEFT -> "LEFT";
            case GLFW.GLFW_KEY_RIGHT -> "RIGHT";
            default -> "KEY_" + key;
        };
    }
}
