package com.codex.baseplugin.commands;

import org.bukkit.command.Command; 
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			int length = args.length;
			if (length <= 0) {
				MenuManager.openBaseFactionMenu(player);
				return true;
			}
			else {
				String action = args[0];
				
				if (action.equalsIgnoreCase("save")) {
					fm.saveAllFactionsToFile();
					player.sendMessage(ChatColor.RED + "Saved all, did you get yeeted?");
					return true;
				}
				if (action.equalsIgnoreCase("test")) {
					FactionPlayer fPlayer = new FactionPlayer(player);
					if (fPlayer.getFaction() == null) {
						player.sendMessage(ChatColor.RED + "No faction");
						return true;
					}
					player.sendMessage(ChatColor.RED + fPlayer.getFaction().getName() + " is your faction");
					player.sendMessage(ChatColor.RED + fPlayer.getFaction().getOwner().getPlayer().getName() + " is your owner!");
					return true;
				}
				if (action.equalsIgnoreCase("create")) {
					FactionPlayer fPlayer = new FactionPlayer(player);
					if (args.length != 2) {
						player.sendMessage(Message.INVALID_ARGS.getConfigMessage().replaceAll("%suggestion%", "/b create [name]"));
						return true;
					}
					String name = args[1];
					if (fPlayer.getFaction() != null) {
						player.sendMessage(Message.ALREADY_IN_FACTION.getConfigMessage());
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
				
			}
			
		}
		return true;
	}

	
	
}
