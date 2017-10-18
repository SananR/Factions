package com.codex.baseplugin.util.faction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.codex.baseplugin.util.file.FileManager;
import com.codex.baseplugin.util.inventory.InventoryUtil;

import net.md_5.bungee.api.ChatColor;

public class FactionManager {

	private List<Faction> allFactions = new ArrayList<Faction>();
	private FileManager fm = FileManager.getInstance();
	
	private static FactionManager instance = new FactionManager();
	
	public static FactionManager getInstance() {
		return instance;
	}
	
	public List<Faction> getAllFactions() {
		return this.allFactions;
	}
	
	public Faction getFactionAtLocation(Location loc) {
		for (Faction faction : allFactions) {
			for (Chunk claim : faction.getClaims()) {
				if (loc.getChunk().equals(claim)) {
					return faction;
				}
			}
		}
		return null;
	}
	
	public Faction getFactionByName(String name) {
		for (Faction faction : allFactions) {
			if (faction.getName().equalsIgnoreCase(name)) {
				return faction;
			}
		}
		return null;
	}
	
	public Faction getPlayerFaction(Player player) {
		for (Faction faction : allFactions) {
			for (FactionPlayer fp : faction.getMembers()) {
				if (fp.getPlayer().getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
					return faction;
				}
			}
		}
		return null;
	}
	
	public void registerFaction(Faction faction) {
		this.allFactions.add(faction);
	}
	
	public void unregisterFaction(Faction faction) {
		this.allFactions.remove(faction);
	}
	
	public void saveAllFactionsToFile() {
		for (Faction faction : allFactions) {
			List<String> members = new ArrayList<String>();
			List<String> elders = new ArrayList<String>();
			for (FactionPlayer fp : faction.getMembers()) {
				if (fp.getPlayer() == null) {
					members.add(fp.getOfflinePlayer().getUniqueId().toString());
				}else {
					members.add(fp.getPlayer().getUniqueId().toString());
				}
			}
			for (FactionPlayer fp : faction.getElders()) {
				if (fp.getPlayer() == null) {
					elders.add(fp.getOfflinePlayer().getUniqueId().toString());
				}else {
					elders.add(fp.getPlayer().getUniqueId().toString());
				}
			}
			if (faction.getHome() != null) {
				fm.getFactionData().set(faction.getName() + ".home.world", faction.getHome().getWorld().getName());
				fm.getFactionData().set(faction.getName() + ".home.x", faction.getHome().getX());
				fm.getFactionData().set(faction.getName() + ".home.y", faction.getHome().getY());
				fm.getFactionData().set(faction.getName() + ".home.z", faction.getHome().getZ());	
			}
			for (int i=0; i < faction.getClaims().size(); i++) {
				Chunk claim = faction.getClaims().get(i);
				fm.getFactionData().set(faction.getName() + ".claims."+i + ".world", claim.getWorld().getName());
				fm.getFactionData().set(faction.getName() + ".claims."+i + ".x", claim.getX());
				fm.getFactionData().set(faction.getName() + ".claims."+i + ".z", claim.getZ());
			}
			fm.getFactionData().set(faction.getName() + ".bank", InventoryUtil.toBase64(faction.getBank()));
			fm.getFactionData().set(faction.getName() + ".owner", faction.getOwner().getPlayer().getUniqueId().toString());
			fm.getFactionData().set(faction.getName() + ".members", members);
			fm.getFactionData().set(faction.getName() + ".elders", elders);
			fm.saveFactionData();
		}
	}
	
	public void loadAllFactionsFromFile() throws IOException {
		for (String faction : fm.getFactionData().getKeys(false)) {
			List<String> members = fm.getFactionData().getStringList(faction + ".members");
			List<String> elders = fm.getFactionData().getStringList(faction + ".elders");
			List<FactionPlayer> facMembers = new ArrayList<FactionPlayer>();
			List<FactionPlayer> facElders = new ArrayList<FactionPlayer>();
			for (String member : members) {
				FactionPlayer fp = new FactionPlayer(member);
				facMembers.add(fp);
			}
			for (String elder : elders) {
				FactionPlayer fp = new FactionPlayer(elder);
				facElders.add(fp);
			}
			FactionPlayer fOwner = new FactionPlayer(fm.getFactionData().getString(faction + ".owner"));
			Location homeLoc = null;
			Inventory bank = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Faction Bank");
			List<Chunk> claims = new ArrayList<Chunk>();
			bank.setContents(InventoryUtil.fromBase64(fm.getFactionData().getString(faction+".bank")).getContents());
			if (fm.getFactionData().getConfigurationSection(faction+".home") != null) {
				World world = Bukkit.getWorld(fm.getFactionData().getString(faction+".home.world"));
				double x = fm.getFactionData().getDouble(faction+".home.x");
				double y = fm.getFactionData().getDouble(faction+".home.y");
				double z = fm.getFactionData().getDouble(faction+".home.z");

				homeLoc = new Location(world,x,y,z);
			}
			if (!(fm.getFactionData().getConfigurationSection(faction+".claims") == null)) {
				for (String claimKey : fm.getFactionData().getConfigurationSection(faction+".claims").getKeys(false)) {
					World world = Bukkit.getWorld(fm.getFactionData().getString(faction+".claims."+claimKey+".world"));
					int x = fm.getFactionData().getInt(faction+".claims."+claimKey+".x");
					int z = fm.getFactionData().getInt(faction+".claims."+claimKey+".z");
					Chunk chunk = world.getChunkAt(x, z);
					claims.add(chunk);
				}
			}
			Faction newFac = new Faction(faction);
			newFac.setOwner(fOwner);
			newFac.setMembers(facMembers);
			newFac.setElders(facElders);
			newFac.setHome(homeLoc);
			newFac.setBank(bank);
			newFac.setClaims(claims);
			registerFaction(newFac);
			fm.getFactionData().set(faction, null);
			fm.saveFactionData();
		}
	}
	
	
	
}
