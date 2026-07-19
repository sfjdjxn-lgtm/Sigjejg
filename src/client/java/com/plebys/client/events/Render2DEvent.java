package com.plebys.client.events;

import net.minecraft.client.gui.DrawContext;

/** Se publica en cada frame, después de que el juego dibuja su propio HUD. */
public final class Render2DEvent {
    public final DrawContext drawContext;

    public Render2DEvent(DrawContext drawContext) {
        this.drawContext = drawContext;
    }
}
