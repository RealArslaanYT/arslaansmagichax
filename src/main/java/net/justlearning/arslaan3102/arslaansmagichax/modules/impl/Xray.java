package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public class Xray extends Module {
    private List<BlockState> interestingBlocks = new ArrayList<>();
    private double previousGamma = 1.0;
    private boolean setPreviousGamma = false;

    public Xray() {
        super("Xray", "Allows you to see through blocks.", -1, Category.RENDER);

        interestingBlocks.add(Blocks.DIAMOND_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState());
        interestingBlocks.add(Blocks.GOLD_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_GOLD_ORE.getDefaultState());
        interestingBlocks.add(Blocks.IRON_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_IRON_ORE.getDefaultState());
        interestingBlocks.add(Blocks.REDSTONE_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_REDSTONE_ORE.getDefaultState());
        interestingBlocks.add(Blocks.LAPIS_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_LAPIS_ORE.getDefaultState());
        interestingBlocks.add(Blocks.EMERALD_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState());
        interestingBlocks.add(Blocks.COAL_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_COAL_ORE.getDefaultState());
        interestingBlocks.add(Blocks.COPPER_ORE.getDefaultState());
        interestingBlocks.add(Blocks.DEEPSLATE_COPPER_ORE.getDefaultState());
        interestingBlocks.add(Blocks.NETHER_QUARTZ_ORE.getDefaultState());
        interestingBlocks.add(Blocks.NETHER_GOLD_ORE.getDefaultState());

        interestingBlocks.add(Blocks.SPAWNER.getDefaultState());
        interestingBlocks.add(Blocks.CHEST.getDefaultState());
        interestingBlocks.add(Blocks.ANCIENT_DEBRIS.getDefaultState());
        interestingBlocks.add(Blocks.GLOWSTONE.getDefaultState());

        interestingBlocks.add(Blocks.WATER.getDefaultState());
        interestingBlocks.add(Blocks.LAVA.getDefaultState());
    }

    @Override
    protected void onEnable() {
        if (mc.worldRenderer == null) return;

        mc.worldRenderer.reload();
    }

    @Override
    public void tick() {
        if (!this.isEnabled() || mc.player == null || mc.world == null || mc.options == null) return;

        if (!setPreviousGamma) previousGamma = mc.options.getGamma().getValue();
        setPreviousGamma = true;
        mc.options.getGamma().setValue(1000.0);
        mc.player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()), 1, 255));
    }

    @Override
    protected void onDisable() {
        if (mc.worldRenderer == null || mc.options == null) return;

        mc.worldRenderer.reload();
        mc.options.getGamma().setValue(previousGamma);
        setPreviousGamma = false;
    }

    public boolean isInterestingBlock(BlockState state) {
        return interestingBlocks.contains(state);
    }

    // xraying is handled in BlockMixin
}
