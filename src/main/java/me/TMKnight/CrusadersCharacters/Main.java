package me.TMKnight.CrusadersCharacters;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;

import java.io.IOException;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import me.TMKnight.CrusadersCharacters.commands.CommandCharacter;
import me.TMKnight.CrusadersCharacters.config.MainConfig;
import me.TMKnight.CrusadersCharacters.utils.Vals;

@Mod(modid = Vals.MODID, name = Vals.NAME, version = Vals.VERSION, acceptableRemoteVersions = "*")
public class Main {

	@SidedProxy(serverSide = "me.TMKnight.CrusadersCharacters.CommonProxy")
	public static CommonProxy proxy;

	@Instance(Vals.MODID)
	public static Main modInstance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException {
		MainConfig.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.register();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	@EventHandler
	public void load(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandCharacter());
	}
}
