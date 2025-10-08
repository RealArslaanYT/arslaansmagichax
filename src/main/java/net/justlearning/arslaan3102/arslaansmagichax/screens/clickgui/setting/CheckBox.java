package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting;

import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.BooleanSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.Setting;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.ModuleButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.*;

public class CheckBox extends Component {

    private BooleanSetting booleanSetting = (BooleanSetting) setting;

    public CheckBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.booleanSetting = (BooleanSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        String displayText = booleanSetting.getName() + ": " + booleanSetting.isEnabled();
        int textWidth = mc.textRenderer.getWidth(displayText);
        int padding = 5;
        int boxWidth = Math.max(parent.parent.width, textWidth + (padding * 2));

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
                parent.parent.x + padding,
                parent.parent.y + parent.offset + offset + (parent.parent.height - mc.textRenderer.fontHeight) / 2,
                Color.white.getRGB()
        );
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            booleanSetting.toggle();
        }
    }
}
