package com.plebys.client.gui;

import com.plebys.client.modules.Category;
import com.plebys.client.modules.Module;
import com.plebys.client.modules.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

/**
 * ClickGUI simple: categorías a la izquierda, módulos a la derecha.
 * Tema: azul marino fuerte.
 */
public class ClickGuiScreen extends Screen {

    // --- Paleta azul marino ---
    private static final int PANEL_BG   = 0xF00B1830; // azul marino casi negro, panel principal
    private static final int SIDEBAR_BG = 0xF008132A; // un toque más oscuro para el costado
    private static final int ACCENT     = 0xFF2E5CE6; // azul fuerte de acento
    private static final int ACCENT_SOFT= 0x552E5CE6;
    private static final int ROW_BG     = 0x30FFFFFF;
    private static final int ON_COLOR   = 0xFF39FF88;
    private static final int TEXT       = 0xFFE9EEF2;
    private static final int TEXT_DIM   = 0xFF7C8894;

    private int panelX, panelY, panelW, panelH;
    private int sidebarW = 90;
    private Category selected = Category.COMBAT;

    public ClickGuiScreen() {
        super(Text.literal("Plebys"));
    }

    @Override
    protected void init() {
        panelW = 320;
        panelH = 220;
        panelX = (this.width - panelW) / 2;
        panelY = (this.height - panelH) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        // Panel principal
        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, PANEL_BG);
        drawBorder(context, panelX, panelY, panelW, panelH, ACCENT);

        // Título
        context.drawText(this.textRenderer, Text.literal("PLEBYS"), panelX + 10, panelY + 8, ACCENT, true);

        // Sidebar de categorías
        int sbX = panelX;
        int sbY = panelY + 24;
        int sbH = panelH - 24;
        context.fill(sbX, sbY, sbX + sidebarW, sbY + sbH, SIDEBAR_BG);

        Category[] cats = Category.values();
        int rowH = 20;
        for (int i = 0; i < cats.length; i++) {
            Category cat = cats[i];
            int rowY = sbY + i * rowH;
            boolean isSelected = cat == selected;
            if (isSelected) {
                context.fill(sbX, rowY, sbX + sidebarW, rowY + rowH, ACCENT_SOFT);
                context.fill(sbX, rowY, sbX + 2, rowY + rowH, ACCENT);
            }
            int color = isSelected ? ACCENT : TEXT_DIM;
            context.drawText(this.textRenderer, Text.literal(cat.displayName), sbX + 8, rowY + 6, color, false);
        }

        // Lista de módulos de la categoría seleccionada
        int listX = panelX + sidebarW + 8;
        int listY = panelY + 30;
        int listW = panelW - sidebarW - 16;

        List<Module> mods = ModuleManager.INSTANCE.getByCategory(selected);
        for (int i = 0; i < mods.size(); i++) {
            Module mod = mods.get(i);
            int rowY = listY + i * 22;
            int rowColor = mod.isEnabled() ? (0x40 << 24 | 0x39FF88) : ROW_BG;
            context.fill(listX, rowY, listX + listW, rowY + 18, rowColor);

            int nameColor = mod.isEnabled() ? ON_COLOR : TEXT;
            context.drawText(this.textRenderer, Text.literal(mod.name), listX + 6, rowY + 5, nameColor, false);

            // "toggle" visual a la derecha de la fila
            int toggleW = 24;
            int toggleX = listX + listW - toggleW - 4;
            int toggleColor = mod.isEnabled() ? 0xFF39FF88 : 0xFF444444;
            context.fill(toggleX, rowY + 4, toggleX + toggleW, rowY + 14, toggleColor);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // click en categorías
            int sbX = panelX;
            int sbY = panelY + 24;
            Category[] cats = Category.values();
            int rowH = 20;
            for (int i = 0; i < cats.length; i++) {
                int rowY = sbY + i * rowH;
                if (mouseX >= sbX && mouseX <= sbX + sidebarW && mouseY >= rowY && mouseY <= rowY + rowH) {
                    selected = cats[i];
                    return true;
                }
            }

            // click en módulos (toggle)
            int listX = panelX + sidebarW + 8;
            int listY = panelY + 30;
            int listW = panelW - sidebarW - 16;
            List<Module> mods = ModuleManager.INSTANCE.getByCategory(selected);
            for (int i = 0; i < mods.size(); i++) {
                int rowY = listY + i * 22;
                if (mouseX >= listX && mouseX <= listX + listW && mouseY >= rowY && mouseY <= rowY + 18) {
                    mods.get(i).toggle();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void drawBorder(DrawContext context, int x, int y, int w, int h, int color) {
        context.fill(x, y, x + w, y + 1, color);
        context.fill(x, y + h - 1, x + w, y + h, color);
        context.fill(x, y, x + 1, y + h, color);
        context.fill(x + w - 1, y, x + w, y + h, color);
    }
}
