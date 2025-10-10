package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "getAmbientOcclusionLightLevel", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (mc == null) mc = MinecraftClient.getInstance(); // just in case :)
        if (mc != null) {
            if (mc.world != null && (ArslaansMagicHaxClient.fullbright.isEnabled())) {
                if (Objects.equals(ArslaansMagicHaxClient.fullbright.mode.getMode(), "Break AO Renderer")) {
                    cir.setReturnValue(15.0F); // over the limit of 1.0F, breaks the ambient occlusion renderer and messes everything up :) yay
                } else {
                    cir.setReturnValue(1.0F);
                }
            }
        }
    }
}
