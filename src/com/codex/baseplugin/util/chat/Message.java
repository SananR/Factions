package com.codex.baseplugin.util.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Message {

	NO_PERMISSION ("invalid-permissions"),
	INVALID_ARGS ("invalid-arguments"),
	INVALID_PLAYER("invalid-player"),
	ALREADY_IN_FACTION("already-in-faction"),
	FACTION_CREATE("create-faction"),
	NAME_TAKEN("name-taken"),
	NO_FACTION("no-faction"),
	FACTION_LEAVE("faction-leave"),
	FACTION_LEAVE_OWNER("faction-leave-owner"),
	FACTION_DISBAND("faction-disband"),
	SET_HOME("set-home"),
	HOME_NOT_SET("home-not-set"),
	HOME_CANCEL("home-cancel"),
	HOME_START("home-start"),
	HOME_TELEPORT("home-teleport"),
	FACTION_CHAT("faction-chat-format"),
	FACTION_CHAT_ELDER("faction-chat-format-elder"),
	FACTION_CHAT_LEADER("faction-chat-format-leader"),
	FACTION_CHAT_ON("faction-chat-on"),
	FACTION_CHAT_OFF("faction-chat-off"),
	NOT_OWNER("not-owner"),
	NAME_LONG("name-too-long"),
	NAME_SHORT("name-too-short"),
	ALREADY_CLAIMED("already-claimed"),
	ELDER_PROMOTE("elder-promote"),
	ELDER_PROMOTED("elder-promoted"),
	ELDER_PROMOTE_OWNER("elder-promote-owner"),
	LAND_CLAIM("land-claim"),
	LAND_UNCLAIM("land-unclaim"),
	UNCLAIM_NOT_OWNED("unclaim-not-owned");
	

    private static FileConfiguration cfg;
	private String path;
	
	Message(String path) {
        this.path = path;
    }

    public static void setFile(FileConfiguration config) {
    	cfg = config;
    }

    public List<String> getConfigMessageList() {
    	List<String> messages = new ArrayList<String>();
    	for (String message : cfg.getStringList("messages."+this.path)) {
    		messages.add(ChatColor.translateAlternateColorCodes('&', message));
    	}
    	return messages;
    }
    
	public String getConfigMessage() {
		String value = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages."+this.path));
		return value;
	}
}
