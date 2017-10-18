package com.codex.baseplugin.util.faction;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FactionPlayer {

	private FactionManager fm;
	private Player player;
	private Faction faction;
	private String uuid;

	public FactionPlayer(String uuid) {
		this.player = Bukkit.getServer().getPlayer(UUID.fromString(uuid));
		this.fm = FactionManager.getInstance();
		this.faction = fm.getPlayerFaction(player);
		this.uuid = uuid;
	}

	public FactionPlayer(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId().toString();
		this.fm = FactionManager.getInstance();
		this.faction = fm.getPlayerFaction(player);
	}

	public Faction getFaction() {
		return this.faction;
	}

	public Player getPlayer() {
		if (player == null) {
			this.player = Bukkit.getServer().getPlayer(UUID.fromString(uuid));
		}
		return this.player;
	}

	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!FactionPlayer.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final FactionPlayer other = (FactionPlayer) obj;
		if ((this.uuid == null) ? (other.uuid != null) : !this.uuid.equals(other.uuid)) {
			return false;
		}
		return true;
	}
}
