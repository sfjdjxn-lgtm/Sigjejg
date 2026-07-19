package com.plebys.client.modules.render;

import com.plebys.client.modules.Category;
import com.plebys.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

/**
 * Sube el brillo al máximo mientras está activo y restaura el valor
 * original del usuario al desactivarse.
 */
public class FullbrightModule extends Module {
    private double previousGamma;

    public FullbrightModule() {
        super("Fullbright", "Máxima visibilidad en zonas oscuras.", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        GameOptions options = MinecraftClient.getInstance().options;
        previousGamma = options.getGamma().getValue();
        options.getGamma().setValue(15.0); // vanilla tope normal es 1.0; los clientes suelen permitir mucho más
    }

    @Override
    protected void onDisable() {
        MinecraftClient.getInstance().options.getGamma().setValue(previousGamma);
    }
}
