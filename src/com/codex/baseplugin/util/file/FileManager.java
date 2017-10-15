package com.codex.baseplugin.util.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {

	private static FileManager instance = new FileManager();
	Plugin p;

	FileConfiguration FactionData;
	File FactionDataFile;

	public static FileManager getInstance() {
		return instance;
	}

	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}

		FactionDataFile = new File(p.getDataFolder(), "FactionData.yml");

		if (!FactionDataFile.exists()) {
			try {
				this.FactionDataFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create FactionData.yml!");
			}
		}

		FactionData = YamlConfiguration.loadConfiguration(this.FactionDataFile);

	}

	public FileConfiguration getFactionData() {
		return FactionData;
	}
	
	public void saveFactionData() {
		try {
			FactionData.save(FactionDataFile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save FactionData.yml!");
		}
	}
	
}
