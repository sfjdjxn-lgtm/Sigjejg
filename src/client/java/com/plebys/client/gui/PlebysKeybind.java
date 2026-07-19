package com.plebys.client.gui;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class PlebysKeybind {
    public static KeyBinding openGui;

    public static void register() {
        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.plebys.opengui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.plebys.general"
        ));
    }

    public static void tick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        while (openGui.wasPressed()) {
            if (mc.currentScreen == null) {
                mc.setScreen(new ClickGuiScreen());
            }
        }
    }
}
