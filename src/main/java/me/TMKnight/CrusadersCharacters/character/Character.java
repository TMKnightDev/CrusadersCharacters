package me.TMKnight.CrusadersCharacters.character;

import java.util.Arrays;

import me.TMKnight.CrusadersCharacters.utils.Methods;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Character {

	// i like to kick gum and chew ass
	// and im all outta ass

	public EntityPlayer player;
	private InventoryPlayer inv;
	private InventoryEnderChest ec;
	private NBTTagCompound ForgeData;
	private NBTTagCompound PlayerPersisted;
	private NBTTagCompound Characters;

	static final String persChar = "PlayerPersistedChar_", invChar = "InventoryChar_", ecChar = "EnderChestChar_";

	public Character(EntityPlayer player) {
		this.player = player;
		inv = player.inventory;
		ec = player.getInventoryEnderChest();
		ForgeData = player.getEntityData();
		PlayerPersisted = ForgeData.getCompoundTag("PlayerPersisted");
		createTags();
	}

	private void createTags() {
		if (!ForgeData.hasKey("CharacterInfo")) {
			NBTTagCompound tag = new NBTTagCompound();
			ForgeData.setTag("CharacterInfo", tag);

			Characters = ForgeData.getCompoundTag("CharacterInfo");
			Characters.setInteger("unlocked", 0);
			Characters.setInteger("selected", 0);
			Characters.setString("created", "1,0,0,0,0");
			Methods.sendMessage("Character Data Created!", player);
		}
		Characters = ForgeData.getCompoundTag("CharacterInfo");
		int selected = Characters.getInteger("selected");

		for (int i = 0; i < 4; i++) {
			if (!Characters.hasKey(persChar + i)) {
				Characters.setTag(persChar + i, new NBTTagCompound());
				if (selected == i) {
					Characters.setTag(persChar + i, PlayerPersisted);
				}
			}
			if (!Characters.hasKey(invChar + i)) {
				Characters.setTag(invChar + i, new NBTTagList());
				if (selected == i) {
					inv.writeToNBT((NBTTagList) Characters.getTag(invChar + i));
				}
			}
			if (!Characters.hasKey(ecChar + i)) {
				Characters.setTag(ecChar + i, new NBTTagList());
				if (selected == i) {
					Characters.setTag(ecChar + i, ec.saveInventoryToNBT());
				}
			}
		}
	}

	public String[] getCharacterInfo() {
		int unlocked = Characters.getInteger("unlocked");
		int selected = Characters.getInteger("selected");
		String created = "1";

		for (int i = 1; i < 4; i++) {
			if (isCreated(i)) {
				created = created + ", " + (i + 1);
			}
		}
		String[] dat = { selected + "", unlocked + "", created };
		return dat;
	}

	public void unlockCharacter(int charInt) {
		int unlocked = Characters.getInteger("unlocked");

		if (charInt == unlocked) {
			Methods.sendMessage("Characters 1 to " + (charInt + 1) + " are Already Unlocked!", player);
		}

		if (charInt > unlocked) {
			Characters.setInteger("unlocked", charInt);
		} else {
			Characters.setInteger("unlocked", charInt);
			for (int i = charInt + 1; i < unlocked; i++) {
				delCharacter(i);
			}
		}
		Methods.sendMessage("Characters 1 to " + (charInt + 1) + " Unlocked!", player);
	}

	public void createCharacter(int charInt) {
		int unlocked = Characters.getInteger("unlocked");
		int selected = Characters.getInteger("selected");

		// Can't create if you're already that character!
		if (selected == charInt) {
			Methods.sendMessage("Character " + (charInt + 1) + " is Selected!", player);
			return;
		}

		if (unlocked < charInt) {
			Methods.sendMessage("Character " + (charInt + 1) + " isn't Unlocked!", player);
			return;
		}
		if (isCreated(charInt)) {
			Methods.sendMessage("Character " + (charInt + 1) + " is Already Created!", player);
			return;
		}

		// Create an Inventory compound.
		Characters.setTag(invChar + charInt, new NBTTagList());

		// Write current Inventory to old/selected Inventory save.
		inv.writeToNBT((NBTTagList) Characters.getTag(invChar + selected));

		// Write current EnderChest to old/selected EnderChest save.
		Characters.setTag(ecChar + selected, ec.saveInventoryToNBT());

		setCreated(charInt, true);
		Methods.sendMessage("Character " + (charInt + 1) + " Created!", player);
	}

	public void selectCharacter(int charInt) {
		int unlocked = Characters.getInteger("unlocked");
		int selected = Characters.getInteger("selected");

		// Can't select if you're already that character!
		if (selected == charInt) {
			Methods.sendMessage("Character " + (charInt + 1) + " is Already Selected!", player);
			return;
		}

		if (unlocked < charInt) {
			Methods.sendMessage("Character " + (charInt + 1) + " isn't Unlocked!", player);
			return;
		}
		if (!isCreated(charInt)) {
			Methods.sendMessage("Character " + (charInt + 1) + " isn't Created!", player);
			return;
		}

		Characters.setInteger("selected", charInt);

		// Save and load PlayerPersisted
		Characters.setTag(persChar + selected, PlayerPersisted);
		ForgeData.setTag("PlayerPersisted", Characters.getCompoundTag(persChar + charInt));

		// Save and load Inventory
		inv.writeToNBT((NBTTagList) Characters.getTag(invChar + selected));
		inv.clearInventory(null, -1);
		inv.readFromNBT((NBTTagList) Characters.getTag(invChar + charInt));

		// Save and load EnderChest
		Characters.setTag(ecChar + selected, ec.saveInventoryToNBT());
		for (int i = 0; i < ec.getSizeInventory(); i++) {
			ec.setInventorySlotContents(i, null);
		}
		ec.loadInventoryFromNBT((NBTTagList) Characters.getTag(ecChar + charInt));

		Methods.sendMessage("Character " + (charInt + 1) + " Selected!", player);
	}

	public void delCharacter(int charInt) {
		int selected = Characters.getInteger("selected");

		// Can't delete if you're already that character!
		if (selected == charInt) {
			Methods.sendMessage("Character " + (charInt + 1) + " is Selected!", player);
			return;
		}

		int unlocked = Characters.getInteger("unlocked");

		if (unlocked < charInt) {
			Methods.sendMessage("Character " + (charInt + 1) + " isn't Unlocked!", player);
			return;
		}
		if (!isCreated(charInt)) {
			Methods.sendMessage("Character " + (charInt + 1) + " isn't Created!", player);
			return;
		}

		Characters.setTag(persChar + charInt, new NBTTagCompound());
		Characters.setTag(invChar + charInt, new NBTTagList());
		Characters.setTag(ecChar + charInt, new NBTTagList());

		setCreated(charInt, false);
		Methods.sendMessage("Character " + (charInt + 1) + " Deleted!", player);
	}

	public boolean isCreated(int charInt) {
		String[] created = Characters.getString("created").split(",");
		if (created[charInt].equals("1")) {
			return true;
		}
		return false;
	}

	// Rewrite created array.
	private void setCreated(int charInt, boolean bool) {
		String[] created = Characters.getString("created").split(",");
		created[charInt] = bool ? "1" : "0";
		String createdStr = Arrays.toString(created).replace(" ", "").replace("[", "").replace("]", "");
		Characters.setString("created", createdStr);
	}
}
