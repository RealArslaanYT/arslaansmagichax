package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void onGetWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Arslaan's Magic Hax | Minecraft 1.21.8");
    }
}
