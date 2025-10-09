package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(targets = "net.minecraft.client.option.SimpleOption$DoubleSliderCallbacks")
public class DoubleSliderCallbacksMixin {
    @Inject(method = "validate", at = @At("HEAD"), cancellable = true)
    private void allowHighGamma(Double value, CallbackInfoReturnable<Optional<Double>> cir) {
        if (ArslaansMagicHaxClient.fullbright.isEnabled() || ArslaansMagicHaxClient.xray.isEnabled()) {
            cir.setReturnValue(Optional.of(value));
        }
    }
}
