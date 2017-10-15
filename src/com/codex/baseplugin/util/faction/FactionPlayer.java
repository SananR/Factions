package com.codex.baseplugin.util.faction;

import org.bukkit.entity.Player;

public class FactionPlayer {

	private FactionManager fm;
	private Player player;
	private Faction faction;
	
	public FactionPlayer(Player player) {
		this.player = player;
		this.fm = FactionManager.getInstance();
		this.faction = fm.getPlayerFaction(player);
	}
	
	public Faction getFaction() {
		return this.faction;
	}

	public Player getPlayer() {
		return this.player;
	}
	
}
