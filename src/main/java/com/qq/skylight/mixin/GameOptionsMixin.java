package com.qq.skylight.mixin;

import com.qq.skylight.state.SkylightState;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Blocks perspective switching (F5) while free camera is active.
 */
@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Inject(method = "setPerspective", at = @At("HEAD"), cancellable = true)
    private void onSetPerspective(Perspective perspective, CallbackInfo ci) {
        if (SkylightState.isFreeCamActive() && perspective != Perspective.FIRST_PERSON) {
            ci.cancel();
        }
    }
}
