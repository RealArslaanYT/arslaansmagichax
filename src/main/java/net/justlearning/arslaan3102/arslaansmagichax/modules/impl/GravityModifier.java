package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.NumberSetting;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class GravityModifier extends Module {
    private NumberSetting gravity = new NumberSetting("Modifier", -1, 1, 0, 0.01);

    public GravityModifier() {
        super("Gravity Modifier", "Changes gravity.", GLFW.GLFW_KEY_GRAVE_ACCENT, Category.MOVEMENT);
        addSetting(gravity);
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) mc.player.sendMessage(Text.literal("[Gravity Modifier] It is recommended to use this hack with NoFall."), false);
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;

        Vec3d velocity = mc.player.getVelocity();
        Vec3d newVelocity = new Vec3d(velocity.x, velocity.y + gravity.getValue(), velocity.z);
        mc.player.setVelocity(newVelocity);
    }
}
