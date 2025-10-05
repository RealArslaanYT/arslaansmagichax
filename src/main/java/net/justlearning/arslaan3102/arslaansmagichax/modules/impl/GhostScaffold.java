package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class GhostScaffold extends Module {
    public GhostScaffold() {
        super("GhostScaffold", "Allows you to scaffold but with ghost blocks", -1, Category.MOVEMENT);
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null || mc.world == null) return;

        BlockPos pos = mc.player.getBlockPos().down();
        BlockState blockState = Blocks.STONE.getDefaultState();

        mc.world.setBlockState(pos, blockState);
    }
}
