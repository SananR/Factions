package com.codex.baseplugin.util.faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.codex.baseplugin.util.file.FileManager;

public class FactionManager {

	private List<Faction> allFactions = new ArrayList<Faction>();
	private FileManager fm = FileManager.getInstance();
	
	private static FactionManager instance = new FactionManager();
	
	public static FactionManager getInstance() {
		return instance;
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
			FactionPlayer fp = new FactionPlayer(player);
			if (faction.getMembers().contains(fp)) {
				return faction;
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
			for (FactionPlayer fp : faction.getMembers()) {
				members.add(fp.getUuid());
			}
			fm.getFactionData().set(faction.getName() + ".owner", faction.getOwner().getUuid());
			fm.getFactionData().set(faction.getName() + ".members", members);
			fm.saveFactionData();
		}
	}
	
	public void loadAllFactionsFromFile() {
		for (String faction : fm.getFactionData().getKeys(false)) {
			List<String> members = fm.getFactionData().getStringList(faction + ".members");
			List<FactionPlayer> facMembers = new ArrayList<FactionPlayer>();
			for (String member : members) {
				FactionPlayer fp = new FactionPlayer(member);
				facMembers.add(fp);
			}
			FactionPlayer fOwner = new FactionPlayer(fm.getFactionData().getString(faction + ".owner"));
			Faction newFac = new Faction(faction);
			newFac.setOwner(fOwner);
			newFac.setMembers(facMembers);
			registerFaction(newFac);
		}
	}
	
	
	
}
