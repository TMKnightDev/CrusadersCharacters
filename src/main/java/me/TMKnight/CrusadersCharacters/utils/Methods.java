package me.TMKnight.CrusadersCharacters.utils;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

@SuppressWarnings("unchecked")
public class Methods {

	static final EnumChatFormatting[] formats = { EnumChatFormatting.BLACK, EnumChatFormatting.DARK_BLUE,
			EnumChatFormatting.DARK_GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.DARK_RED,
			EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.GOLD, EnumChatFormatting.GRAY,
			EnumChatFormatting.DARK_GRAY, EnumChatFormatting.BLUE, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA,
			EnumChatFormatting.RED, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.YELLOW,
			EnumChatFormatting.WHITE, EnumChatFormatting.OBFUSCATED, EnumChatFormatting.BOLD,
			EnumChatFormatting.STRIKETHROUGH, EnumChatFormatting.UNDERLINE, EnumChatFormatting.ITALIC,
			EnumChatFormatting.RESET };
	static final char[] charMats = { '0', '2', '3', '4', '5', '6', '7', '8', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
			'k', 'l', 'm', 'n', 'o', 'r' };

	public static boolean isValid(String[] args, int[] args1, String msg, EntityPlayer player) {
		for (int arg : args1) {
			if (arg == Integer.parseInt(args[1])) {
				return true;
			}
		}
		if (player != null) {
			Methods.sendMessage(msg, player);
		}
		return false;
	}

	public static boolean isOp(EntityPlayer player) {
		if (FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager()
				.func_152596_g(player.getGameProfile())) {
			return true;
		} else {
			return false;
		}
	}

	public static EntityPlayer getPlayerFromName(String name) {
		List<EntityPlayerMP> allPlayers = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		for (EntityPlayerMP playerMP : allPlayers) {
			if (playerMP.getCommandSenderName().equals(name)) {
				return (EntityPlayer) playerMP;
			}
		}
		return null;
	}

	public static EnumChatFormatting getCol(char character) {
		int i = 0;
		for (char charIt : charMats) {
			if (charIt == character) {
				return formats[i];
			}
			i++;
		}
		return EnumChatFormatting.WHITE;
	}

	public static String replaceColors(String message) {
		char[] list = message.toCharArray();
		String msg = "";
		String style = "";

		if (message.contains("&")) {
			for (int i = 0; i < list.length; i++) {
				if (list[i] == '&') {
					style += getCol(list[i + 1]);
				} else if (i != 0 && list[i - 1] != '&') {
					msg = msg + style + list[i];
				}
			}
		} else {
			msg = message;
		}
		return msg;
	}

	// Sends Message to Player Accounting for Section Sign
	public static void sendMessage(String message, EntityPlayer player) {
		player.addChatMessage(new ChatComponentTranslation(replaceColors(Vals.prefix() + " " + message)));
	}
}
