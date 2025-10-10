/*
THIS FILE EXISTS AS AN ARCHIVE!!!!
Injecting into getLuminance causes TONS of weird crashes and issues related to world rendering
New logic is implemented in AbstractBlockMixin.java
 */
package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlockState.class)
public class AbstractBlockStateMixin {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    private void onGetLuminance(CallbackInfoReturnable<Integer> cir) {
        AbstractBlockState state = (AbstractBlockState)(Object)this;

        if (mc.world != null && (ArslaansMagicHaxClient.xray.isEnabled() && ArslaansMagicHaxClient.xray.isInterestingBlock((BlockState) state) || ArslaansMagicHaxClient.fullbright.isEnabled())) {
            cir.setReturnValue(15);
        }
    }
}

