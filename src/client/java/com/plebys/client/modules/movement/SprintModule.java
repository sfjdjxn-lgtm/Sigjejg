package com.plebys.client.modules.movement;

import com.plebys.client.modules.Category;
import com.plebys.client.modules.Module;
import net.minecraft.client.MinecraftClient;

/**
 * Mantiene al jugador corriendo todo el tiempo que pueda,
 * incluso sin apretar la tecla de sprint.
 */
public class SprintModule extends Module {
    public SprintModule() {
        super("Sprint", "Corre automáticamente cuando es posible.", Category.MOVEMENT);
    }

    @Override
    protected void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        // hungerManager.getFoodLevel() > 6 es la condición vanilla para poder sprintear
        if (mc.player.forwardSpeed > 0 && mc.player.getHungerManager().getFoodLevel() > 6) {
            mc.player.setSprinting(true);
        }
    }
}
