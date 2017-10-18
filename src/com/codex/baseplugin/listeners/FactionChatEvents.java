package com.codex.baseplugin.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.codex.baseplugin.util.chat.Message;
import com.codex.baseplugin.util.faction.Faction;
import com.codex.baseplugin.util.faction.FactionPlayer;

public class FactionChatEvents implements Listener {
	
	public static List<Player> factionChat = new ArrayList<Player>();
	
	@EventHandler
	public void onFactionChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (factionChat.contains(player)) {
			event.setCancelled(true);
			FactionPlayer fPlayer = new FactionPlayer(player);
			Faction faction = fPlayer.getFaction();
			for (FactionPlayer member : faction.getMembers()) {
				if (faction.getOwner().equals(fPlayer)) {
					member.getPlayer().sendMessage(Message.FACTION_CHAT_LEADER.getConfigMessage().replaceAll("%player%", player.getName()).replaceAll("%message%", event.getMessage()));	
				}
				else if (faction.isElder(fPlayer)) {
					member.getPlayer().sendMessage(Message.FACTION_CHAT_ELDER.getConfigMessage().replaceAll("%player%", player.getName()).replaceAll("%message%", event.getMessage()));	
				} else {
					member.getPlayer().sendMessage(Message.FACTION_CHAT.getConfigMessage().replaceAll("%player%", player.getName()).replaceAll("%message%", event.getMessage()));	
				}
			}
			return;
		}
	}

}
