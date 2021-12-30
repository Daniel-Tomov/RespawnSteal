package me.cageydinosaur.respawnsteal;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class DataManager {
	private Main plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	
	public DataManager(Main plugin) {
		this.plugin = plugin;
	}
	
	public void reloadConfig() {
		if (this.configFile == null) {
			new File(this.plugin.getDataFolder(), "respawns.yml")
		}
	}
}
