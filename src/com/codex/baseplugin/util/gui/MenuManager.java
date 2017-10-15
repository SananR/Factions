package com.codex.baseplugin.util.gui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.codex.baseplugin.Main;

public class MenuManager {
	
	private static FileConfiguration cfg = Main.plugin.getConfig();
	
	public static void openBaseFactionMenu(Player player) {
		int size = cfg.getInt("inventories.base-menu.size");
		String name = cfg.getString("inventories.base-menu.name");
		Inventory inventory = Bukkit.createInventory(null, size, name);
		
		for (String key : cfg.getConfigurationSection("inventories.base-menu.items").getKeys(false)) {
			String path = "inventories.base-menu.items." + key;
			ItemStack item = ItemUtil.getItemFromConfig(path);
			inventory.setItem(ItemUtil.getItemSlotFromConfig(path), item);
		}
		
		player.closeInventory();
		player.openInventory(inventory);
	}
	
	
}
