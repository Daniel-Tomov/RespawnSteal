package me.cageydinosaur.respawnsteal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
	private Main plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	
	public DataManager(Main plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}
	
	public void reloadCustomConfig() {
		if (this.configFile == null) {
			new File(this.plugin.getDataFolder(), "respawns.yml");
		}
		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
		
		InputStream defaultStream = this.plugin.getResource("respawns.yml");
		
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		if (this.dataConfig == null) {
			reloadCustomConfig();
		}
		return this.dataConfig;
	}
	
	public void saveConfig() {
		if (this.dataConfig == null || this.configFile == null) {
			return;
		}
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
		}
	}
	
	public void saveDefaultConfig() {
		if (this.configFile == null){
			this.configFile = new File(this.plugin.getDataFolder(), "respawn.yml");
		}
		
		if(!this.configFile.exists()) {
			this.plugin.saveResource("respawn.yml", false);
		}
	}
}


