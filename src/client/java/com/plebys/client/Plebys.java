package com.plebys.client.gui;

import com.plebys.client.gui.ClickGUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class PlebysKeybind {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Abre el menú con la tecla P
            if (client.currentScreen == null && client.options.chatKey.isPressed()) {
                client.setScreen(new ClickGUI());
            }
        });
    }
}
