package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.minecraft.util.math.Vec3d;

public class Spider extends Module {
    private NumberSetting climbSpeed = new NumberSetting("Climb Speed", 0, 1, 0.2, 0.1);

    public Spider() {
        super("Spider", "Allows you to climb walls like a spider.", -1, Category.MOVEMENT);
        addSetting(climbSpeed);
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;
        if (!mc.player.horizontalCollision) return;

        Vec3d velocity = mc.player.getVelocity();
        if (velocity.y >= 0.2) return;

        mc.player.setVelocity(velocity.x, climbSpeed.getValue(), velocity.z);
    }
}
