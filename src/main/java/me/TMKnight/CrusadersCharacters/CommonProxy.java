package me.TMKnight.CrusadersCharacters;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import me.TMKnight.CrusadersCharacters.events.PlayerEvents;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void register() {
		registerEvents();
	}

	public void registerEvents() {
		EventBus bus = FMLCommonHandler.instance().bus();
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		bus.register(new PlayerEvents());
	}
}
