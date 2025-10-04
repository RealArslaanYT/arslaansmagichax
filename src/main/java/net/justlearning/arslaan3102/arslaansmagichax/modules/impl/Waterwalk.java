package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class Waterwalk extends Module {
    public Waterwalk() {
        super("Waterwalk", "Allows you to walk on water.");
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null) return;

        BlockPos blockBelow = mc.player.getBlockPos().down();

        if (mc.world.getBlockState(blockBelow).isOf(Blocks.WATER)) {
            if (mc.player.getVelocity().y < 0) {
                mc.player.setVelocity(mc.player.getVelocity().x, 0.0, mc.player.getVelocity().z);

                if (!mc.player.isJumping()) mc.player.setPos(mc.player.getX(), Math.floor(mc.player.getY()), mc.player.getZ());
            }
            mc.player.setOnGround(true);
        }
    }
}