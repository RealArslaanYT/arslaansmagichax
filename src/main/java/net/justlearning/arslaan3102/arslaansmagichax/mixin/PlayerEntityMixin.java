package net.justlearning.arslaan3102.arslaansmagichax.mixin;

import net.justlearning.arslaan3102.arslaansmagichax.ArslaansMagicHaxClient;
import net.justlearning.arslaan3102.arslaansmagichax.modules.impl.NoAttackCooldown;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void onGetAttackCooldownProgress(CallbackInfoReturnable<Float> cir) {
        if (ArslaansMagicHaxClient.noAttackCooldown.isEnabled()) cir.setReturnValue(1.0F);
    }
}