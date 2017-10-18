package com.codex.baseplugin.util.faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;

public class Faction {

	private String name;
	private FactionPlayer owner;
	private List<FactionPlayer> members;
	private List<FactionPlayer> elders;
	private Location home;
	private Inventory bank;
	private List<Chunk> claims;
	

	public Faction(String name) {
		this.name = name;
		this.members = new ArrayList<FactionPlayer>();
		this.elders = new ArrayList<FactionPlayer>();
		this.claims = new ArrayList<Chunk>();
		this.bank = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Faction Bank");
	}

	public boolean isElder(FactionPlayer player) {
		return this.elders.contains(player);
	}
	
	public List<Chunk> getClaims() {
		return claims;
	}
	
	public Inventory getBank() {
		return bank;
	}

	public Location getHome() {
		return home;
	}

	public String getName() {
		return name;
	}

	public FactionPlayer getOwner() {
		return owner;
	}
	
	public List<FactionPlayer> getElders() {
		return elders;
	}
	
	public List<FactionPlayer> getMembers() {
		return members;
	}

	public void setOwner(FactionPlayer owner) {
		this.owner = owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addElder(FactionPlayer player) {
		this.elders.add(player);
	}
	
	public void addMember(FactionPlayer player) {
		this.members.add(player);
	}

	public void removeElder(FactionPlayer player) {
		this.elders.remove(player);
	}
	
	public void removeMember(FactionPlayer player) {
		this.members.remove(player);
	}

	public void addClaim(Chunk claim) {
		this.claims.add(claim);
	}
	
	public void removeClaim(Chunk claim) {
		this.claims.remove(claim);
	}
	
	public void setClaims(List<Chunk> claims) {
		this.claims = claims;
	}
	
	public void setBank(Inventory bank) {
		this.bank = bank;
	}
	
	public void setHome(Location home) {
		this.home = home;
	}

	public void setElders(List<FactionPlayer> elders) {
		this.elders = elders;
	}
	
	public void setMembers(List<FactionPlayer> members) {
		this.members = members;
	}

}
