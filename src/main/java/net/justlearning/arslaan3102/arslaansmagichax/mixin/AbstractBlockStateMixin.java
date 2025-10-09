package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlockState.class)
public class AbstractBlockStateMixin {

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    private void onGetLuminance(CallbackInfoReturnable<Integer> cir) {
        AbstractBlockState state = (AbstractBlockState)(Object)this;

        if ((ArslaansMagicHaxClient.xray.isEnabled() && ArslaansMagicHaxClient.xray.isInterestingBlock((BlockState) state)) || ArslaansMagicHaxClient.fullbright.isEnabled()) {
            cir.setReturnValue(15);
        }
    }
}

