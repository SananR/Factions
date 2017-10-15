package com.codex.baseplugin.util.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Message {

	NO_PERMISSION ("invalid-permissions"),
	INVALID_ARGS ("invalid-arguments"),
	ALREADY_IN_FACTION("already-in-faction"),
	FACTION_CREATE("create-faction");
	

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
