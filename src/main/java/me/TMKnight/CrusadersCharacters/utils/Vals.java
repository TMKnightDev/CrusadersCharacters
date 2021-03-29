package me.TMKnight.CrusadersCharacters.utils;

import me.TMKnight.CrusadersCharacters.config.MainConfig;
import net.minecraftforge.common.config.Configuration;

public class Vals {
	public static final String MODID = "crusaderscharacters";
	public static final String VERSION = "0.1";
	public static final String NAME = "Crusaders Characters";
	public static final String Persisted = "PlayerPersisted";

	public static String prefix() {
		return MainConfig.getConfig().getString("Chat Prefix", Configuration.CATEGORY_GENERAL, "", "");
	}

	public static float tpMult() {
		return MainConfig.getConfig().getFloat("TP Multiplier", Configuration.CATEGORY_GENERAL, 1.0f, 0, 10000, "");
	}
}
