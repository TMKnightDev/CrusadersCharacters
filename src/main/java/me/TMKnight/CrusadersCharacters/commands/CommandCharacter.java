package me.TMKnight.CrusadersCharacters.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import me.TMKnight.CrusadersCharacters.character.Character;
import me.TMKnight.CrusadersCharacters.utils.Methods;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandCharacter extends CommandBase {

	private final List<String> aliases;

	public CommandCharacter() {
		aliases = new ArrayList<String>();
		aliases.add("char");
		aliases.add("characters");
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public String getCommandName() {
		return "character";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		int length = args.length;
		if (sender instanceof EntityPlayer) {
			EntityPlayer senderPl = (EntityPlayer) sender;
			if (length >= 1) {
				args[0] = args[0].toLowerCase();
				String[] arg0Arr = { "select", "unlock", "create", "delete", "info" };

				int i = 1;
				for (String arg : arg0Arr) {
					if (arg.equals(args[0])) {
						break;
					}
					if (i == arg0Arr.length) {
						Methods.sendMessage("Invalid Arguments! /character (select | unlock | create | delete | info)",
								senderPl);
						return;
					}
					i++;
				}

				if (args[0].equals("info")) {
					if (length < 2) {
						Character character = new Character(senderPl);

						String[] dat = character.getCharacterInfo();
						Methods.sendMessage("Character " + (Integer.parseInt(dat[0]) + 1) + " Selected", senderPl);
						Methods.sendMessage("Characters 1 to " + (Integer.parseInt(dat[1]) + 1) + " Unlocked",
								senderPl);
						Methods.sendMessage("Characters " + dat[2] + " Created", senderPl);
					} else {
						String playerName = args[1];
						EntityPlayer target = Methods.getPlayerFromName(playerName);
						if (target == null) {
							Methods.sendMessage("Invalid Player!", senderPl);
							return;
						}
						Character character = new Character(target);
						String[] dat = character.getCharacterInfo();
						Methods.sendMessage(playerName + ": Character " + (Integer.parseInt(dat[0]) + 1) + " Selected",
								senderPl);
						Methods.sendMessage(
								playerName + ": Characters 1 to " + (Integer.parseInt(dat[1]) + 1) + " Unlocked",
								senderPl);
						Methods.sendMessage(playerName + ": Characters " + dat[2] + " Created", senderPl);
					}
				}

				if (args[0].equals("select")) {
					if (length < 2) {
						Methods.sendMessage("Not Enough Arguments! (Character 1-5)", senderPl);
						return;
					}
					if (!NumberUtils.isDigits(args[1])) {
						Methods.sendMessage("Invalid Arguments! (Character 1-5)", senderPl);
						return;
					}

					int[] arg1Arr = { 1, 2, 3, 4, 5 };

					if (Methods.isValid(args, arg1Arr, "Invalid Arguments! (Character 1-5)", senderPl)) {
						Character character = new Character(senderPl);
						character.selectCharacter(Integer.parseInt(args[1]) - 1);
					}
				}

				if (args[0].equals("unlock")) {
					if (!Methods.isOp(senderPl)) {
						Methods.sendMessage("Not Enough Permissions! (Needs OP)", senderPl);
						return;
					}
					if (length < 2) {
						Methods.sendMessage("Not Enough Arguments! (Character 1-5)", senderPl);
						return;
					}
					if (!NumberUtils.isDigits(args[1])) {
						Methods.sendMessage("Invalid Arguments! (Character 1-5)", senderPl);
						return;
					}

					int[] arg1Arr = { 1, 2, 3, 4, 5 };

					if (Methods.isValid(args, arg1Arr, "Invalid Arguments! (Character 1-5)", senderPl)) {
						if (args.length > 2) {
							String playerName = args[2];
							EntityPlayer target = Methods.getPlayerFromName(playerName);
							if (target == null) {
								Methods.sendMessage("Invalid Player!", senderPl);
								return;
							}
							Character character = new Character(target);
							character.unlockCharacter(Integer.parseInt(args[1]) - 1);
						} else {
							Character character = new Character(senderPl);
							character.unlockCharacter(Integer.parseInt(args[1]) - 1);
						}
					}
				}

				if (args[0].equals("create")) {
					if (length < 2) {
						Methods.sendMessage("Not Enough Arguments! (Character 1-5)", senderPl);
						return;
					}
					if (!NumberUtils.isDigits(args[1])) {
						Methods.sendMessage("Invalid Arguments! (Character 1-5)", senderPl);
						return;
					}

					int[] arg1Arr = { 1, 2, 3, 4, 5 };

					if (Methods.isValid(args, arg1Arr, "Invalid Arguments! (Character 1-5)", senderPl)) {
						Character character = new Character(senderPl);
						character.createCharacter(Integer.parseInt(args[1]) - 1);
					}
				}

				if (args[0].equals("delete")) {
					if (length < 2) {
						Methods.sendMessage("Not Enough Arguments! (Character 1-5)", senderPl);
						return;
					}
					if (!NumberUtils.isDigits(args[1])) {
						Methods.sendMessage("Invalid Arguments! (Character 1-5)", senderPl);
						return;
					}

					int[] arg1Arr = { 1, 2, 3, 4, 5 };

					if (Methods.isValid(args, arg1Arr, "Invalid Arguments! (Character 1-5)", senderPl)) {
						Character character = new Character(senderPl);
						character.delCharacter(Integer.parseInt(args[1]) - 1);
					}
				}
			} else {
				Methods.sendMessage("Invalid Arguments! /character (select | unlock | create | delete)", senderPl);
			}
		} else if (length >= 1) {
			args[0] = args[0].toLowerCase();
			if (args[0].equals("unlock")) {
				if (length < 2) {
					return;
				}
				if (!NumberUtils.isDigits(args[1])) {
					return;
				}

				int[] arg1Arr = { 1, 2, 3, 4, 5 };

				if (Methods.isValid(args, arg1Arr, "Invalid Arguments! (Character 1-5)", null)) {
					if (args.length > 2) {
						String playerName = args[2];
						EntityPlayer target = Methods.getPlayerFromName(playerName);
						if (target == null) {
							return;
						}
						Character character = new Character(target);
						character.unlockCharacter(Integer.parseInt(args[1]) - 1);
					}
				}
			}
		}
	}
}
