package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    private static final Random RANDOM = new Random();

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void onGet(CallbackInfoReturnable<SplashTextRenderer> cir) {
        String[] splashes = {"\"one can only hope.\"", "hackerman approved", "Cheater!", "totally not malware", "cheats for block game", "dont use /surprise"};
        String selected = splashes[RANDOM.nextInt(splashes.length)];
        cir.setReturnValue(new SplashTextRenderer(selected));
    }
}
