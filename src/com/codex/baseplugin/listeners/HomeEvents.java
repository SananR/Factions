package com.codex.baseplugin.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.codex.baseplugin.util.chat.Message;

public class HomeEvents implements Listener {
	
	public static HashMap<Player, BukkitRunnable> teleporting = new HashMap<Player, BukkitRunnable>();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockY() != event.getFrom().getBlockY() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
			if (!teleporting.keySet().contains(player)) return;
			teleporting.get(player).cancel();
			teleporting.remove(player);
			player.sendMessage(Message.HOME_CANCEL.getConfigMessage());
			return;
		}
	}

}
