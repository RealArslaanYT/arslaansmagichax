package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.BooleanSetting;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;

public class Speed extends Module {
    private final NumberSetting speed = new NumberSetting("Speed", 0.1, 2.0, 0.4, 0.05);
    private final BooleanSetting enableWhileSneaking = new BooleanSetting("Enable while Sneaking", true);
    private final BooleanSetting enableInWater = new BooleanSetting("Enable in Water", true);
    private final BooleanSetting enableInLava = new BooleanSetting("Enable in Lava", true);

    public Speed() {
        super("Speed", "Makes you faster.", -1, Category.MOVEMENT);
        addSetting(speed);
        addSetting(enableWhileSneaking);
        addSetting(enableInWater);
        addSetting(enableInLava);
    }

    @Override
    public void tick() {
        if (!isEnabled() || mc.player == null) return;
        if (!enableWhileSneaking.isEnabled() && mc.player.isSneaking()) return;
        if (!enableInLava.isEnabled() && mc.player.isInLava()) return;
        if (!enableInWater.isEnabled() && mc.player.isTouchingWater()) return;

        float forward = mc.player.forwardSpeed;
        float strafe = mc.player.sidewaysSpeed;
        float yaw = mc.player.getYaw();

        if (forward == 0 && strafe == 0) return;

        if (forward != 0) {
            if (strafe > 0) yaw += (forward > 0 ? -45 : 45);
            else if (strafe < 0) yaw += (forward > 0 ? 45 : -45);
            strafe = 0;
            forward = forward > 0 ? 1 : -1;
        }

        double rad = Math.toRadians(yaw);
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);

        double x = (forward * -sin + strafe * cos) * speed.getValue();
        double z = (forward * cos - strafe * -sin) * speed.getValue();

        mc.player.setVelocity(x, mc.player.getVelocity().y, z);
    }
}
