package net.justlearning.arslaan3102.arslaansmagichax.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.justlearning.arslaan3102.arslaansmagichax.modules.ModuleManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class ModuleToggleCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(
                ClientCommandManager.literal("module")
                        .then(
                                ClientCommandManager.literal("toggle")
                                        .then(
                                                ClientCommandManager.argument("moduleName", StringArgumentType.word())
                                                        .suggests(new ModuleSuggestionProvider())
                                                        .executes(ModuleToggleCommand::run)
                                        )
                        )
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        String moduleName = StringArgumentType.getString(context, "moduleName");

        ModuleManager.getAllModules().stream()
                .filter(m -> m.getName().equalsIgnoreCase(moduleName))
                .findFirst()
                .ifPresentOrElse(module -> {
                    module.toggle();
                    context.getSource().sendFeedback(Text.literal(
                            "Toggled module " + module.getName() + " to " + (module.isEnabled() ? "ON" : "OFF")
                    ));
                }, () -> context.getSource().sendError(Text.literal("Module not found: " + moduleName)));

        return 1;
    }
}
