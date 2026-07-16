package com.plebys.client.modules;

import com.plebys.client.modules.movement.SprintModule;
import com.plebys.client.modules.render.FullbrightModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Registro central de todos los módulos disponibles.
 * Acá agregás una línea por cada módulo nuevo que crees.
 */
public final class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();

    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {}

    public void init() {
        register(new SprintModule());
        register(new FullbrightModule());
        // Agregá acá tus próximos módulos, ej:
        // register(new FlyModule());
        // register(new XRayModule());
        // register(new NametagsModule());
    }

    private void register(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public Optional<Module> get(String name) {
        return modules.stream()
                .filter(m -> m.name.equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Module> getByCategory(Category category) {
        return modules.stream().filter(m -> m.category == category).toList();
    }
}
