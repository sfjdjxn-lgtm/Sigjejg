package com.plebys.client.modules;

import com.plebys.client.events.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para toda función activable del cliente (ej: Sprint, Fullbright, ESP).
 *
 * Un módulo se suscribe a eventos SOLO mientras está activo: así evitamos
 * que 200 módulos inactivos sigan gastando ciclos en cada tick/frame,
 * que es uno de los cuellos de botella típicos en este tipo de clientes.
 */
public abstract class Module {
    public final String name;
    public final String description;
    public final Category category;
    private final List<Setting<?>> settings = new ArrayList<>();

    private boolean enabled = false;
    public int keyBind = -1; // GLFW key code, -1 = sin bind

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    protected <T> Setting<T> register(Setting<T> setting) {
        settings.add(setting);
        return setting;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public final void toggle() {
        if (enabled) disable();
        else enable();
    }

    public final void enable() {
        if (enabled) return;
        enabled = true;
        EventBus.INSTANCE.subscribe(com.plebys.client.events.TickEvent.class, this::onTickInternal);
        EventBus.INSTANCE.subscribe(com.plebys.client.events.Render2DEvent.class, this::onRender2DInternal);
        onEnable();
    }

    public final void disable() {
        if (!enabled) return;
        enabled = false;
        EventBus.INSTANCE.unsubscribe(com.plebys.client.events.TickEvent.class, this::onTickInternal);
        EventBus.INSTANCE.unsubscribe(com.plebys.client.events.Render2DEvent.class, this::onRender2DInternal);
        onDisable();
    }

    public final boolean isEnabled() {
        return enabled;
    }

    private void onTickInternal(com.plebys.client.events.TickEvent event) {
        onTick();
    }

    private void onRender2DInternal(com.plebys.client.events.Render2DEvent event) {
        onRender2D(event);
    }

    // --- Métodos que cada módulo concreto puede sobreescribir ---
    protected void onEnable() {}
    protected void onDisable() {}
    protected void onTick() {}
    protected void onRender2D(com.plebys.client.events.Render2DEvent event) {}
}
