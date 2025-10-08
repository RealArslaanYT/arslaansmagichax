package net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.setting;

import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.Setting;
import net.justlearning.arslaan3102.arslaansmagichax.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component {

    public NumberSetting numberSetting = (NumberSetting) setting;

    private boolean sliding = false;

    public Slider(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numberSetting = (NumberSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        String displayText = numberSetting.getName() + ": " + numberSetting.getValue();
        int defaultPadding = 5;
        int boxWidth = parent.parent.width;

        int alpha = isHovered(mouseX, mouseY) ? 230 : 160;

        context.fill(
                parent.parent.x,
                parent.parent.y + parent.offset + offset,
                parent.parent.x + boxWidth,
                parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(30, 30, 47, alpha).getRGB()
        );

        double diff = Math.min(boxWidth, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int) (boxWidth * (numberSetting.getValue() - numberSetting.getMin()) / (numberSetting.getMax() - numberSetting.getMin()));

        context.fill(
                parent.parent.x,
                parent.parent.y + parent.offset + offset,
                parent.parent.x + renderWidth,
                parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(252, 210, 77, 255).getRGB()
        );

        if (sliding) {
            if (diff == 0) {
                numberSetting.setValue(numberSetting.getMin());
            } else {
                numberSetting.setValue(roundToPlace((diff / boxWidth) * (numberSetting.getMax() - numberSetting.getMin()) + numberSetting.getMin(), 1));
            }
        }

        int textWidth = mc.textRenderer.getWidth(displayText);
        int leftPadding = defaultPadding;
        if (textWidth + 2 * defaultPadding > boxWidth) {
            leftPadding = Math.max(2, boxWidth - textWidth - 2);
        }

        context.drawTextWithShadow(
                mc.textRenderer,
                Text.literal(displayText),
                parent.parent.x + leftPadding,
                parent.parent.y + parent.offset + offset + (parent.parent.height - mc.textRenderer.fontHeight) / 2,
                Color.white.getRGB()
        );
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) sliding = true;
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
