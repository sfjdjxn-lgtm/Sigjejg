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
        super(Text.literal("Plebys ClickGUI"));
    }

    @Override
    public void render(class_332 context, int mouseX, int mouseY, float delta) {
        // Fondo Azul Marino fuerte
        context.method_28045(0, 0, this.width, this.height, 0xFF001233);

        // Panel principal
        context.method_28045(100, 60, this.width - 200, this.height - 120, 0xFF002244);

        // Título
        context.drawTextWithShadow(textRenderer, "§bPlebys §7- §9Azul Marino", 120, 70, 0xFFFFFFFF);

        // Categorías
        int catX = 120;
        for (Category cat : Category.values()) {
            int color = (cat == currentCategory) ? 0xFF00BFFF : 0xFF777777;
            context.drawTextWithShadow(textRenderer, cat.name(), catX, 95, color);
            catX += 85;
        }

        // Lista de módulos
        int y = 130;
        for (Module module : ModuleManager.INSTANCE.getByCategory(currentCategory)) {
            String prefix = module.isEnabled() ? "§a● " : "§7○ ";
            context.drawTextWithShadow(textRenderer, prefix + module.name, 130, y, 0xFFFFFFFF);
            y += 22;
        }
    }
}
