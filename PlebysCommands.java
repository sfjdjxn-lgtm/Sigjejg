package com.plebys.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.plebys.client.modules.Module;
import com.plebys.client.modules.ModuleManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

/**
 * Comandos puramente locales (no llegan al servidor) para prender/apagar
 * módulos sin necesitar todavía una GUI gráfica. Uso: ".plebys sprint"
 */
public final class PlebysCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("plebys")
                    .then(ClientCommandManager.argument("modulo", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            ModuleManager.INSTANCE.getModules()
                                    .forEach(m -> builder.suggest(m.name.toLowerCase()));
                            return builder.buildFuture();
                        })
                        .executes(ctx -> {
                            String moduleName = StringArgumentType.getString(ctx, "modulo");
                            ModuleManager.INSTANCE.get(moduleName).ifPresentOrElse(
                                PlebysCommands::toggleAndFeedback,
                                () -> feedback(Text.literal("§cMódulo '" + moduleName + "' no existe."))
                            );
                            return 1;
                        })
                    )
            )
        );
    }

    private static void toggleAndFeedback(Module module) {
        module.toggle();
        String state = module.isEnabled() ? "§aACTIVADO" : "§cDESACTIVADO";
        feedback(Text.literal("§7[§bPlebys§7] §f" + module.name + " " + state));
    }

    private static void feedback(Text text) {
        var mc = net.minecraft.client.MinecraftClient.getInstance();
        if (mc.player != null) {
            mc.player.sendMessage(text, false);
        }
    }
}
