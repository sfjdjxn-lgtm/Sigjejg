package com.plebys.client.modules.combat;

import com.plebys.client.modules.Category;
import com.plebys.client.modules.Module;
import com.plebys.client.modules.Setting;
import net.minecraft.class_310;
import net.minecraft.class_1297;
import net.minecraft.class_1511; // EntityEndCrystal
import net.minecraft.class_2338;
import net.minecraft.class_243;

public class AutoCrystal extends Module {

    private final Setting<Double> range = new Setting<>("Range", "Distancia máxima", 5.5);
    private final Setting<Boolean> place = new Setting<>("Place", "Colocar crystals", true);
    private final Setting<Boolean> break = new Setting<>("Break", "Romper crystals", true);
    private final Setting<Boolean> rotate = new Setting<>("Rotate", "Rotar al target", true);

    private long lastBreakTime = 0;
    private long lastPlaceTime = 0;

    public AutoCrystal() {
        super("AutoCrystal", "Auto Crystal optimizado para ping alto", Category.COMBAT);
        register(range);
        register(place);
        register(break);
        register(rotate);
    }

    @Override
    protected void onTick() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1687 == null) return;

        long now = System.currentTimeMillis();

        // Break crystals (prioridad alta)
        if (break.get() && now - lastBreakTime > 50) {  // throttle para ping alto
            class_1511 crystal = findBestCrystalToBreak(mc);
            if (crystal != null) {
                if (rotate.get()) {
                    rotateTo(crystal);
                }
                mc.field_1724.method_6075(crystal); // attack
                lastBreakTime = now;
            }
        }

        // Place crystals
        if (place.get() && now - lastPlaceTime > 80) {
            class_2338 pos = findBestPlacePosition(mc);
            if (pos != null) {
                if (rotate.get()) {
                    rotateToPosition(pos);
                }
                // Lógica de colocar crystal (necesitas item en mano)
                lastPlaceTime = now;
            }
        }
    }

    private class_1511 findBestCrystalToBreak(class_310 mc) {
        class_1511 best = null;
        double bestDist = Double.MAX_VALUE;

        for (class_1297 entity : mc.field_1687.method_18112()) {
            if (entity instanceof class_1511 crystal) {
                double dist = mc.field_1724.method_19538().squaredDistanceTo(crystal.method_19538());
                if (dist < range.get() * range.get() && dist < bestDist) {
                    bestDist = dist;
                    best = crystal;
                }
            }
        }
        return best;
    }

    private class_2338 findBestPlacePosition(class_310 mc) {
        // Placeholder avanzado - busca posiciones cerca de enemigos
        // Implementación completa más adelante si quieres
        return null; // TODO: mejorar
    }

    private void rotateTo(class_1297 target) {
        // Rotación simple (puedes mejorar con GCD fix)
        double dx = target.getX() - class_310.method_1551().field_1724.getX();
        double dy = target.getEyeY() - class_310.method_1551().field_1724.getEyeY();
        double dz = target.getZ() - class_310.method_1551().field_1724.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));
        class_310.method_1551().field_1724.setYaw(yaw);
        class_310.method_1551().field_1724.setPitch(pitch);
    }

    private void rotateToPosition(class_2338 pos) {
        // Similar a rotateTo
    }
}
