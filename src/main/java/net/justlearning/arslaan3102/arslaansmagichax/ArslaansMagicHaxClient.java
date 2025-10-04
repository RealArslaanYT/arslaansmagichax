package net.justlearning.arslaan3102.arslaansmagichax;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.justlearning.arslaan3102.arslaansmagichax.commands.*;
import net.justlearning.arslaan3102.arslaansmagichax.modules.ModuleManager;
import net.justlearning.arslaan3102.arslaansmagichax.modules.impl.*;

public class ArslaansMagicHaxClient implements ClientModInitializer {
    public static Flight flight = new Flight();
    public static NoFall noFall = new NoFall();
    public static Waterwalk waterwalk = new Waterwalk();
    public static NoAttackCooldown noAttackCooldown = new NoAttackCooldown();
    public static GhostScaffold ghostScaffold = new GhostScaffold();

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(HackTP::register);
        ClientCommandRegistrationCallback.EVENT.register(ModuleToggleCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(HackXP::register);
        ClientCommandRegistrationCallback.EVENT.register(HackSetblock::register);
        ClientCommandRegistrationCallback.EVENT.register(HackGive::register);
        ClientCommandRegistrationCallback.EVENT.register(CrashCommand::register); // crashes the game for the funni

        ModuleManager.INSTANCE.register(flight);
        ModuleManager.INSTANCE.register(noFall);
        ModuleManager.INSTANCE.register(waterwalk);
        ModuleManager.INSTANCE.register(noAttackCooldown);
        ModuleManager.INSTANCE.register(ghostScaffold);

        ModuleManager.INSTANCE.registerHUD();

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
                ModuleManager.tick();
            }
        );
    }
}
