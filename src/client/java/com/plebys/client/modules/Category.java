package com.plebys.client.modules;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    WORLD("World"),
    MISC("Misc"),
    CLIENT("Client"); // Para settings del client, GUI, etc.

    public final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }
}
