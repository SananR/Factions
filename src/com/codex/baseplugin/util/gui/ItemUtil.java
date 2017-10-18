package com.codex.baseplugin.util.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.codex.baseplugin.Main;
import com.codex.baseplugin.util.chat.ChatUtil;

public class ItemUtil {

	private static FileConfiguration cfg = Main.plugin.getConfig();
	
	
	@SuppressWarnings("deprecation")
	public static ItemStack getHeadFromPlayer(Player player) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(player.getName());
        meta.setDisplayName(ChatColor.AQUA + player.getName());
        skull.setItemMeta(meta);
        return skull;
	}
	
	public static int getItemSlotFromConfig(String path) {
		int slot = cfg.getInt(path + ".slot");
		return slot;
	}
	
	public static ItemStack getItemFromConfig(String path) {
		String id = cfg.getString(path + ".id");
		String name = cfg.getString(path + ".name");
		List<String> cfglore = cfg.getStringList(path + ".lore");
		
		ItemStack item = fromId(id, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtil.color(name));
		List<String> lore = new ArrayList<String>();
		for (String line : cfglore) {
			lore.add(ChatUtil.color(line));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack fromId(String id, int amount) {
		if (id.contains(":")) {
		    String[] s = id.split(":");
		    int type = Integer.parseInt(s[0]); 
		    int data = Integer.parseInt(s[1]); 
		 
		    ItemStack item = new ItemStack(Material.getMaterial(type));
		    item.setDurability((short) data);
		 
		    return item;	
		}else {
			ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(id)));
			return item;
		}
	}
	
}
