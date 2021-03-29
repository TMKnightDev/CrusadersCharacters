package me.TMKnight.CrusadersCharacters.config;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.TMKnight.CrusadersCharacters.utils.Vals;
import net.minecraftforge.common.config.Configuration;

public class MainConfig {

	public static Configuration config;

	static File cfgPath;
	static File cfgFile;

	public static void init() throws IOException {
		createFiles();
		addDefaults();
	}

	public static void createFiles() throws IOException {
		cfgPath = new File("config");
		if (!cfgPath.exists()) {
			cfgPath.mkdirs();
		}
		cfgFile = new File(cfgPath, Vals.MODID + ".cfg");
		if (!cfgFile.exists()) {
			cfgFile.createNewFile();
		}
	}

	public static void addDefaults() {
		if (config == null) {
			config = new Configuration(cfgFile);
			if (!config.hasKey(Configuration.CATEGORY_GENERAL, "Chat Prefix")) {
				config.getString("Chat Prefix", Configuration.CATEGORY_GENERAL, "&7[&cCrusaders&7] &f> &a",
						"How the mod messages you in chat.");
			}
			if (!config.hasKey(Configuration.CATEGORY_GENERAL, "TP Multiplier")) {
				config.getFloat("TP Multiplier", Configuration.CATEGORY_GENERAL, 1.0f, 0, 10000,
						"TP Multiplier referring to /jrmctp. Goes from 0 - 10000. Uses floats.");
			}
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Vals.MODID)) {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	public static Configuration getConfig() {
		return config;
	}
}
