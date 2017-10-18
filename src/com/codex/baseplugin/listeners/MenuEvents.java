package com.codex.baseplugin.listeners;

import org.bukkit.Material; 
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.codex.baseplugin.util.chat.Message;
import com.codex.baseplugin.util.faction.Faction;
import com.codex.baseplugin.util.faction.FactionPlayer;
import com.codex.baseplugin.util.gui.ItemUtil;
import com.codex.baseplugin.util.gui.MenuManager;
public class MenuEvents implements Listener {
	
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		FactionPlayer fPlayer = new FactionPlayer(player);
		
		ItemStack clicked = event.getCurrentItem();
		ItemStack members = ItemUtil.getItemFromConfig("inventories.base-menu.items.members");
		ItemStack info = ItemUtil.getItemFromConfig("inventories.base-menu.items.info");
		ItemStack bank = ItemUtil.getItemFromConfig("inventories.base-menu.items.bank");
		ItemStack leave = ItemUtil.getItemFromConfig("inventories.base-menu.items.leave");
		
		
		if (clicked == null || clicked.getType().equals(Material.AIR)) return;
		
		String name = clicked.getItemMeta().getDisplayName();
		
		if (name == null) return;
		if (!event.getInventory().getTitle().equals(MenuManager.getBaseFactionMenu().getTitle()) && !event.getInventory().getTitle().equals(MenuManager.getMembersInventory(fPlayer.getFaction()).getTitle())) return;
		
		event.setCancelled(true);
		
		if (name.equalsIgnoreCase(members.getItemMeta().getDisplayName())) {
			player.closeInventory();
			player.openInventory(MenuManager.getMembersInventory(fPlayer.getFaction()));
			return;
		}
		else if (name.equalsIgnoreCase(info.getItemMeta().getDisplayName())) {
			player.closeInventory();
			//TODO Info
		}
		else if (name.equalsIgnoreCase(bank.getItemMeta().getDisplayName())) {
			player.closeInventory();
			player.openInventory(fPlayer.getFaction().getBank());
			return;
		}
		else if (name.equalsIgnoreCase(leave.getItemMeta().getDisplayName())) {
			player.closeInventory();
			Faction faction = fPlayer.getFaction();
			if (faction.getOwner().equals(fPlayer)) {
				player.sendMessage(Message.FACTION_LEAVE_OWNER.getConfigMessage());
				return;
			}
			faction.removeMember(fPlayer);
			player.sendMessage(Message.FACTION_LEAVE.getConfigMessage());
			return;
		}
		
		
	}

}
