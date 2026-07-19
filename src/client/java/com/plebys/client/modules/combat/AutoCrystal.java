package com.plebys.client.modules.combat;

import com.plebys.client.modules.Category;
import com.plebys.client.modules.Module;
import com.plebys.client.modules.Setting;
import net.minecraft.class_310;
import net.minecraft.class_1297;
import net.minecraft.class_1511;

public class AutoCrystal extends Module {

    private final Setting<Double> range = new Setting<>("Range", "Distancia", 5.5);
    private final Setting<Boolean> doPlace = new Setting<>("Place", "Colocar", true);
    private final Setting<Boolean> doBreak = new Setting<>("Break", "Romper", true);

    private long lastAction = 0;

    public AutoCrystal() {
        super("AutoCrystal", "Auto Crystal optimizado", Category.COMBAT);
        register(range);
        register(doPlace);
        register(doBreak);
    }

    @Override
    protected void onTick() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1687 == null) return;

        long now = System.currentTimeMillis();
        if (now - lastAction < 60) return; // anti lag

        if (doBreak.get()) {
            class_1511 crystal = findCrystal(mc);
            if (crystal != null) {
                mc.field_1724.method_6075(crystal);
                lastAction = now;
            }
        }
    }

    private class_1511 findCrystal(class_310 mc) {
        for (class_1297 e : mc.field_1687.method_18112()) {
            if (e instanceof class_1511 crystal) {
                if (mc.field_1724.method_19538().distanceTo(crystal.method_19538()) < range.get()) {
                    return crystal;
                }
            }
        }
        return null;
    }
}
