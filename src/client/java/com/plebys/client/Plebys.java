package com.plebys.client;

import com.plebys.client.events.EventBus;
import com.plebys.client.events.Render2DEvent;
import com.plebys.client.gui.ModuleListHud;
import com.plebys.client.modules.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class Plebys implements ClientModInitializer {
    public static final String MOD_ID = "plebys";

    @Override
    public void onInitializeClient() {
        // 1. Registramos todos los módulos disponibles
        ModuleManager.INSTANCE.init();
        
// Tecla para abrir el menú (RIGHT SHIFT por defecto)
        PlebysKeybind.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> PlebysKeybind.tick());
        
        // 2. Comandos locales (.plebys <modulo>) para togglear sin GUI todavía
        PlebysCommands.register();

        // 3. HUD con la lista de módulos activos
        new ModuleListHud().init();

        // 4. Conectamos el render del HUD vanilla con nuestro EventBus
        HudRenderCallback.EVENT.register((drawContext, tickCounter) ->
                EventBus.INSTANCE.post(new Render2DEvent(drawContext))
        );

        System.out.println("[Plebys] Cargado correctamente.");
    }
}
import com.plebys.client.gui.PlebysKeybind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
