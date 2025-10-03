package net.justlearning.arslaan3102.arslaansmagichax;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArslaansMagicHax implements ModInitializer {
	public static final String MOD_ID = "arslaansmagichax";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("mod initialized :)");
	}
}