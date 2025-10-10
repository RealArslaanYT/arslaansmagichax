package net.justlearning.arslaan3102.arslaansmagichax.modules.impl;

import net.justlearning.arslaan3102.arslaansmagichax.modules.Category;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.settings.ModeSetting;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Xray extends Module {
    private List<BlockState> interestingBlocks = new ArrayList<>();

    public Xray() {
        super("Xray", "Allows you to see through blocks.", -1, Category.RENDER);

        // Add ores, spawners, liquids, etc.
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
        if (mc.worldRenderer != null) mc.worldRenderer.reload();

        if (mc.player != null) mc.player.sendMessage(Text.literal("[Xray] It is recommended to use this hack with Fullbright for a better experience."), false);
    }


    @Override
    protected void onDisable() {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }

    public boolean isInterestingBlock(BlockState state) {
        return interestingBlocks.contains(state);
    }

    // xraying is handled in BlockMixin.java
}

