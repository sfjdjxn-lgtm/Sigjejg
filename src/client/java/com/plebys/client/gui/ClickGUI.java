package com.plebys.client.gui;

import com.plebys.client.modules.Category;
import com.plebys.client.modules.Module;
import com.plebys.client.modules.ModuleManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.class_332;

public class ClickGUI extends Screen {
    private Category currentCategory = Category.COMBAT;

    public ClickGUI() {
        super(Text.literal("Plebys"));
    }

    @Override
    public void render(class_332 context, int mouseX, int mouseY, float delta) {
        // Fondo Azul Marino
        context.method_28045(0, 0, this.width, this.height, 0xFF001233);

        // Panel
        context.method_28045(80, 40, this.width - 160, this.height - 80, 0xFF002244);

        // Header
        context.drawTextWithShadow(textRenderer, "Plebys Client - Azul Marino", 100, 50, 0xFF00CCFF);

        // Categorías
        int x = 100;
        for (Category cat : Category.values()) {
            int color = (cat == currentCategory) ? 0xFF00BFFF : 0xFFAAAAAA;
            context.drawTextWithShadow(textRenderer, cat.name(), x, 80, color);
            x += 90;
        }

        // Módulos
        int y = 120;
        for (Module m : ModuleManager.INSTANCE.getByCategory(currentCategory)) {
            int color = m.isEnabled() ? 0xFF00FF88 : 0xFFFFFFFF;
            context.drawTextWithShadow(textRenderer, m.name, 100, y, color);
            y += 22;
        }
    }
}
