package com.plebys.client.events;

/** Se publica una vez por cada tick del cliente (20 veces por segundo). */
public final class TickEvent {
    public static final TickEvent INSTANCE = new TickEvent();
    private TickEvent() {}
}
