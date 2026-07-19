package com.plebys.client.modules;

/**
 * Un valor configurable dentro de un módulo (ej: velocidad, distancia, color).
 * La GUI (cuando la construyas) puede iterar los settings de cada módulo
 * genéricamente en vez de tener que conocer cada campo a mano.
 */
public class Setting<T> {
    public final String name;
    public final String description;
    private T value;
    public final T defaultValue;

    public Setting(String name, String description, T defaultValue) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public void reset() {
        this.value = defaultValue;
    }
}
