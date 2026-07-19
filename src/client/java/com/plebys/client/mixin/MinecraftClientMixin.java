package com.plebys.client.mixin;

import com.plebys.client.events.EventBus;
import com.plebys.client.events.TickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void plebys$onTick(CallbackInfo ci) {
        EventBus.INSTANCE.post(TickEvent.INSTANCE);
    }
}
