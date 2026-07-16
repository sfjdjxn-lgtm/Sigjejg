package com.plebys.client.events;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Bus de eventos central. Cualquier módulo o sistema puede suscribirse
 * a un tipo de evento y será notificado cuando se publique una instancia.
 *
 * Se usa CopyOnWriteArrayList porque los listeners se agregan/quitan
 * poco (al activar/desactivar módulos) pero se recorren muy seguido
 * (cada tick, cada frame), así que priorizamos lectura rápida.
 */
public final class EventBus {
    public static final EventBus INSTANCE = new EventBus();

    private final Map<Class<?>, List<Consumer<Object>>> listeners = new ConcurrentHashMap<>();

    private EventBus() {}

    @SuppressWarnings("unchecked")
    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                .add((Consumer<Object>) listener);
    }

    @SuppressWarnings("unchecked")
    public <T> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<Object>> list = listeners.get(eventType);
        if (list != null) {
            list.remove((Consumer<Object>) listener);
        }
    }

    public void post(Object event) {
        List<Consumer<Object>> list = listeners.get(event.getClass());
        if (list == null) return;
        for (Consumer<Object> listener : list) {
            try {
                listener.accept(event);
            } catch (Exception e) {
                // Nunca dejamos que un módulo roto tumbe el juego entero.
                System.err.println("[Plebys] Error en listener de " + event.getClass().getSimpleName());
                e.printStackTrace();
            }
        }
    }
}
