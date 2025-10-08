package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class Jetpack extends Module {
    private NumberSetting speed = new NumberSetting("Thrust Speed", 1, 10, 1, 1);

    public Jetpack() {
        super("Jetpack", "Turns your jump key into a jetpack.", -1, Category.MOVEMENT);
        addSetting(speed);
    }

    @Override
    protected void onEnable() {
        if (mc.player == null) return;
        mc.player.sendMessage(Text.literal("[Jetpack] It is recommended to use this hack in combination with NoFall."), false);
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;

        if (mc.options.jumpKey.isPressed()) {
            mc.player.setVelocity(new Vec3d(mc.player.getVelocity().x, 0.42 * speed.getValue(), mc.player.getVelocity().z));
            mc.player.fallDistance = 0;
        }
    }
}
