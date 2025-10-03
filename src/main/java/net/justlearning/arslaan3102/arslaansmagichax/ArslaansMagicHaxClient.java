package net.justlearning.arslaan3102.arslaansmagichax;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.justlearning.arslaan3102.arslaansmagichax.commands.HackTP;

public class ArslaansMagicHaxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(HackTP::register);
    }
}
