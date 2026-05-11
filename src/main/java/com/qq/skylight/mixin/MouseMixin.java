package com.qq.skylight.mixin;

import com.qq.skylight.camera.FreeCamera;
import com.qq.skylight.state.SkylightState;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to handle mouse input for free camera rotation.
 */
@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void onUpdateMouse(double timeDelta, CallbackInfo ci) {
        if (SkylightState.isFreeCamActive()) {
            // In freecam, we redirect changeLookDirection to FreeCamera
            // Don't cancel here - let updateMouse run so sensitivity/smoothing is applied
        }
    }

    @Redirect(
        method = "updateMouse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"
        )
    )
    private void redirectChangeLookDirection(ClientPlayerEntity player, double cursorDeltaX, double cursorDeltaY) {
        if (SkylightState.isFreeCamActive()) {
            FreeCamera.getInstance().updateRotation(cursorDeltaX, cursorDeltaY);
        } else {
            player.changeLookDirection(cursorDeltaX, cursorDeltaY);
        }
    }
}
