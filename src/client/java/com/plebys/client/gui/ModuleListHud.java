package com.plebys.client.gui;

import com.plebys.client.events.EventBus;
import com.plebys.client.events.Render2DEvent;
import com.plebys.client.modules.Module;
import com.plebys.client.modules.ModuleManager;
import net.minecraft.client.MinecraftClient;

/**
 * Lista de texto en la esquina superior derecha con los módulos
 * activos en este momento. Siempre está suscripta (no es un Module)
 * porque es parte del núcleo visual del cliente, no algo togglable.
 */
public final class ModuleListHud {

    public void init() {
        EventBus.INSTANCE.subscribe(Render2DEvent.class, this::onRender);
    }

    private void onRender(Render2DEvent event) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden) return;

        int screenWidth = mc.getWindow().getScaledWidth();
        int y = 4;

        for (Module module : ModuleManager.INSTANCE.getModules()) {
            if (!module.isEnabled()) continue;
            String text = module.name;
            int textWidth = mc.textRenderer.getWidth(text);
            event.drawContext.drawTextWithShadow(
                    mc.textRenderer,
                    text,
                    screenWidth - textWidth - 4,
                    y,
                    0x00D9FF // celeste, cambialo a gusto
            );
            y += mc.textRenderer.fontHeight + 2;
        }
    }
}
