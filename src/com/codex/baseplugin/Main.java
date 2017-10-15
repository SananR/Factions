package com.codex.baseplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.codex.baseplugin.commands.FactionCommand;
import com.codex.baseplugin.util.chat.Message;
import com.codex.baseplugin.util.faction.FactionManager;
import com.codex.baseplugin.util.file.FileManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public static Plugin plugin;
	
	private FileManager fm = FileManager.getInstance();
	private FactionManager facM = FactionManager.getInstance();
	
	public void onEnable() {
		plugin = this;
		fm.setup(this);
		saveDefaultConfig();
		Message.setFile(getConfig());
		setupCommands();
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Factions] - Loading Faction information from file...");
		facM.loadAllFactionsFromFile();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Factions] - Faction data successfully loaded.");
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Factions] - Saving Faction information to file...");
		facM.loadAllFactionsFromFile();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Factions] - Faction data successfully saved.");
	}
	
	private void setupCommands() {
		getCommand("b").setExecutor(new FactionCommand());
	}
	
	
	
}
