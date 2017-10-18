package com.codex.baseplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.codex.baseplugin.Main;
import com.codex.baseplugin.listeners.FactionChatEvents;
import com.codex.baseplugin.listeners.HomeEvents;
import com.codex.baseplugin.util.chat.Message;
import com.codex.baseplugin.util.faction.Faction;
import com.codex.baseplugin.util.faction.FactionManager;
import com.codex.baseplugin.util.faction.FactionPlayer;
import com.codex.baseplugin.util.gui.MenuManager;

import net.md_5.bungee.api.ChatColor;

public class FactionCommand implements CommandExecutor {

	private FactionManager fm = FactionManager.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equals("b")) {
			if (!(sender instanceof Player)) {
				return true;
			}
			Player player = (Player)sender;
			FactionPlayer fPlayer = new FactionPlayer(player);
			int length = args.length;
			if (length <= 0) {
				if (fPlayer.getFaction() == null) {
					player.sendMessage(Message.NO_FACTION.getConfigMessage());
					return true;
				}
				player.closeInventory();
				player.openInventory(MenuManager.getBaseFactionMenu());
				return true;
			}
			else {
				String action = args[0];
				
				if (action.equalsIgnoreCase("test")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(ChatColor.RED + "No faction");
						return true;
					}
					player.sendMessage(ChatColor.RED + fPlayer.getFaction().getName() + " is your faction");
					player.sendMessage(ChatColor.RED + fPlayer.getFaction().getOwner().getPlayer().getName() + " is your owner!");
					return true;
				}
				if (action.equalsIgnoreCase("create")) {
					if (args.length != 2) {
						player.sendMessage(Message.INVALID_ARGS.getConfigMessage().replaceAll("%suggestion%", "/b create [name]"));
						return true;
					}
					String name = args[1];
					if (fPlayer.getFaction() != null) {
						player.sendMessage(Message.ALREADY_IN_FACTION.getConfigMessage());
						return true;
					}
					if (name.length() < 3) {
						player.sendMessage(Message.NAME_SHORT.getConfigMessage());
						return true;
					}
					if (name.length() > 10) {
						player.sendMessage(Message.NAME_LONG.getConfigMessage());
						return true;
					}
					if (fm.getFactionByName(name) != null) {
						player.sendMessage(Message.NAME_TAKEN.getConfigMessage());
						return true;
					}
					for (String line : Message.FACTION_CREATE.getConfigMessageList()) {
						player.sendMessage(line.replace("%faction%", name));
					}
					Faction faction = new Faction(name);
					faction.addMember(fPlayer);
					faction.setOwner(fPlayer);
					fm.registerFaction(faction);
					return true;
				}
				if (action.equalsIgnoreCase("leave")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					if (faction.getOwner().equals(fPlayer)) {
						player.sendMessage(Message.FACTION_LEAVE_OWNER.getConfigMessage());
						return true;
					}
					faction.removeMember(fPlayer);
					player.sendMessage(Message.FACTION_LEAVE.getConfigMessage());
					return true;
				}
				if (action.equalsIgnoreCase("disband")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					if (!faction.getOwner().equals(fPlayer)) {
						player.sendMessage(Message.NOT_OWNER.getConfigMessage());
						return true;
					}
					fm.unregisterFaction(faction);
					for (String line : Message.FACTION_DISBAND.getConfigMessageList()) {
						player.sendMessage(line.replaceAll("%faction%", faction.getName()));
					}
					return true;
				}
				if (action.equalsIgnoreCase("sethome")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					if (!faction.getOwner().equals(fPlayer)) {
						player.sendMessage(Message.NOT_OWNER.getConfigMessage());
						return true;
					}
					Location loc = player.getLocation();
					faction.setHome(loc);
					player.sendMessage(Message.SET_HOME.getConfigMessage());
					return true;
				}
				if (action.equalsIgnoreCase("home")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					Location home = faction.getHome();
					if (home == null) {
						player.sendMessage(Message.HOME_NOT_SET.getConfigMessage());
						return true;
					}
					player.sendMessage(Message.HOME_START.getConfigMessage());
					BukkitRunnable task = new BukkitRunnable() {
					    @Override
					    public void run() {
		                	if (HomeEvents.teleporting.keySet().contains(player)) {
		                		player.sendMessage(Message.HOME_TELEPORT.getConfigMessage());
		                		player.teleport(home);
		                		HomeEvents.teleporting.remove(player);
		                		this.cancel();
		                		return;
		                	} else {
		                		this.cancel();
		                	}
					    }
					};
					HomeEvents.teleporting.put(player, task);
					task.runTaskLater(Main.plugin, 20 * 6);
					return true;
				}
				if (action.equalsIgnoreCase("chat")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					if (FactionChatEvents.factionChat.contains(player)) {
						player.sendMessage(Message.FACTION_CHAT_OFF.getConfigMessage());
						FactionChatEvents.factionChat.remove(player);
						return true;
					} else {
						player.sendMessage(Message.FACTION_CHAT_ON.getConfigMessage());
						FactionChatEvents.factionChat.add(player);
						return true;
					}
				}
				if (action.equalsIgnoreCase("bank")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					player.closeInventory();
					player.openInventory(faction.getBank());
					return true;
				}
				if (action.equalsIgnoreCase("claim")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					if (!faction.getOwner().equals(fPlayer)) {
						player.sendMessage(Message.NOT_OWNER.getConfigMessage());
						return true;
					}
					if (fm.getFactionAtLocation(player.getLocation()) != null) {
						player.sendMessage(Message.ALREADY_CLAIMED.getConfigMessage().replaceAll("%faction%", fm.getFactionAtLocation(player.getLocation()).getName()));
						return true;
					}
					player.sendMessage(Message.LAND_CLAIM.getConfigMessage());
					faction.addClaim(player.getLocation().getChunk());
					return true;
				}
				if (action.equalsIgnoreCase("unclaim")) {
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					if (!faction.getOwner().equals(fPlayer)) {
						player.sendMessage(Message.NOT_OWNER.getConfigMessage());
						return true;
					}
					if (fm.getFactionAtLocation(player.getLocation()) == null || !fm.getFactionAtLocation(player.getLocation()).getName().equals(fPlayer.getFaction().getName())) {
						player.sendMessage(Message.UNCLAIM_NOT_OWNED.getConfigMessage());
						return true;
					}
					player.sendMessage(Message.LAND_UNCLAIM.getConfigMessage());
					faction.removeClaim(player.getLocation().getChunk());
					return true;
				}
				if (action.equalsIgnoreCase("elder")) {
					if (args.length != 2) {
						player.sendMessage(Message.INVALID_ARGS.getConfigMessage().replaceAll("%suggestion%", "/b elder [name]"));
						return true;
					}
					if (fPlayer.getFaction() == null) {
						player.sendMessage(Message.NO_FACTION.getConfigMessage());
						return true;
					}
					Faction faction = fPlayer.getFaction();
					if (!faction.getOwner().equals(fPlayer)) {
						player.sendMessage(Message.NOT_OWNER.getConfigMessage());
						return true;
					}
					if (Bukkit.getServer().getPlayer(args[1]) == null) {
						player.sendMessage(Message.INVALID_ARGS.getConfigMessage());
						return true;
					}
					Player target = Bukkit.getServer().getPlayer(args[1]);
					FactionPlayer fTarget = new FactionPlayer(target);
					if (faction.getOwner().equals(fTarget)) {
						player.sendMessage(Message.ELDER_PROMOTE_OWNER.getConfigMessage());
						return true;
					}
					player.sendMessage(Message.ELDER_PROMOTE.getConfigMessage().replaceAll("%player%", target.getName()));
					target.sendMessage(Message.ELDER_PROMOTED.getConfigMessage().replaceAll("%player%", player.getName()));
					faction.addElder(fTarget);
					return true;
				}
				
				
			}
			
		}
		return true;
	}

	
	
}
