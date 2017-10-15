package com.codex.baseplugin.util.faction;

import java.util.ArrayList;
import java.util.List;

public class Faction {

	String name;
	FactionPlayer owner;
	List<FactionPlayer> members;
	
	public Faction (String name) {
		this.name = name;
		this.members = new ArrayList<FactionPlayer>();
	}
	
	public String getName() {
		return name;
	}
	public FactionPlayer getOwner() {
		return owner;
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
	public void setMembers(List<FactionPlayer> members) {
		this.members = members;
	}
	public void addMember(FactionPlayer player) {
		this.members.add(player);
	}
	public void removeMember(FactionPlayer player) {
		this.members.remove(player);
	}
	
	
}
