package com.plebys.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Punto de entrada de ejemplo para modificar el comportamiento del jugador
 * local. Acá es donde vas a inyectar cosas como NoFall, Speed, Step, etc.
 * De momento no hace nada (solo demuestra el patrón).
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void plebys$onTick(CallbackInfo ci) {
        // Ejemplo: acá podrías leer el estado de un módulo y modificar
        // el movimiento antes de que el juego procese el tick.
        // if (ModuleManager.INSTANCE.get("Speed")...) { ... }
    }
}
