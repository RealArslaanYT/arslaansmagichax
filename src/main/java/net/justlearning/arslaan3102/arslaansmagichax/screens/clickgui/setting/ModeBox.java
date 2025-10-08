package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting;

import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.Setting;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.*;

public class ModeBox extends Component {

    private ModeSetting modeSetting = (ModeSetting) setting;

    public ModeBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.modeSetting = (ModeSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        String displayText = modeSetting.getName() + ": " + modeSetting.getMode();
        int textWidth = mc.textRenderer.getWidth(displayText);

        int padding = 5;
        int boxWidth = Math.max(parent.parent.width, textWidth + padding * 2);

        int alpha = isHovered(mouseX, mouseY) ? 230 : 160;
        context.fill(
                parent.parent.x,
                parent.parent.y + parent.offset + offset,
                parent.parent.x + boxWidth,
                parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(30, 30, 47, alpha).getRGB()
        );

        context.drawTextWithShadow(
                mc.textRenderer,
                Text.literal(displayText),
                parent.parent.x + padding, // left padding
                parent.parent.y + parent.offset + offset + (parent.parent.height - mc.textRenderer.fontHeight) / 2,
                Color.white.getRGB()
        );
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            modeSetting.cycle();
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
    }
}
