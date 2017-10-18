package com.codex.baseplugin.util.gui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.codex.baseplugin.Main;
import com.codex.baseplugin.util.chat.ChatUtil;
import com.codex.baseplugin.util.faction.Faction;
import com.codex.baseplugin.util.faction.FactionPlayer;

public class MenuManager {
	
	private static FileConfiguration cfg = Main.plugin.getConfig();
	
	public static Inventory getMembersInventory(Faction faction) {
		int size = cfg.getInt("inventories.members.size");
		String name = cfg.getString("inventories.members.name");
		Inventory inventory = Bukkit.createInventory(null, size, ChatUtil.color(name));
		for (FactionPlayer member : faction.getMembers()) {
			inventory.addItem(ItemUtil.getHeadFromPlayer(member.getPlayer()));
		}
		return inventory;
	}
	
	public static Inventory getBaseFactionMenu() {
		int size = cfg.getInt("inventories.base-menu.size");
		String name = cfg.getString("inventories.base-menu.name");
		Inventory inventory = Bukkit.createInventory(null, size, ChatUtil.color(name));
		
		for (String key : cfg.getConfigurationSection("inventories.base-menu.items").getKeys(false)) {
			String path = "inventories.base-menu.items." + key;
			ItemStack item = ItemUtil.getItemFromConfig(path);
			inventory.setItem(ItemUtil.getItemSlotFromConfig(path), item);
		}
		
		return inventory;
	}
	
	
}
