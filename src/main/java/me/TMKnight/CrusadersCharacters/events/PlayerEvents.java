package me.TMKnight.CrusadersCharacters.events;

import org.apache.commons.lang3.math.NumberUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.TMKnight.CrusadersCharacters.utils.Vals;
import net.minecraftforge.event.CommandEvent;

public class PlayerEvents {

	@SubscribeEvent
	public void jrmcTPOver(CommandEvent event) {
		if (event.command.getCommandName().equals("jrmctp")) {
			String[] args = event.parameters;

			if (args.length >= 1) {
				if (NumberUtils.isDigits(args[0])) {
					float mult = Vals.tpMult();
					int tp = Integer.parseInt(args[0]);
					if (tp > 0) {
						int newTP = (int) (tp * mult);

						event.parameters[0] = newTP + "";
					}
				}
			}
		}
	}
}
