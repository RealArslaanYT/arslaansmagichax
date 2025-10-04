package net.justlearning.arslaan3102.arslaansmagichax;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.justlearning.arslaan3102.arslaansmagichax.commands.HackTP;
import net.justlearning.arslaan3102.arslaansmagichax.commands.ModuleToggleCommand;
import net.justlearning.arslaan3102.arslaansmagichax.modules.Module;
import net.justlearning.arslaan3102.arslaansmagichax.modules.ModuleManager;
import net.justlearning.arslaan3102.arslaansmagichax.modules.impl.Flight;
import net.justlearning.arslaan3102.arslaansmagichax.modules.impl.NoFall;
import net.justlearning.arslaan3102.arslaansmagichax.modules.impl.Waterwalk;

public class ArslaansMagicHaxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(HackTP::register);
        ClientCommandRegistrationCallback.EVENT.register(ModuleToggleCommand::register);

        ModuleManager.INSTANCE.register(new Flight());
        ModuleManager.INSTANCE.register(new NoFall());
        ModuleManager.INSTANCE.register(new Waterwalk());

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
                ModuleManager.tick();
            }
        );
    }
}
