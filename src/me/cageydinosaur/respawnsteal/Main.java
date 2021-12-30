package me.cageydinosaur.respawnsteal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private File customConfigFile;
	private FileConfiguration customConfig;

	public ArrayList<Respawns> respawnList = new ArrayList<Respawns>();

	public void onEnable() {
		getCommand("respawnsteal").setExecutor(new Cmd(this));
		getCommand("respawnsteal").setTabCompleter(new TabCompletion(this));
		this.getServer().getPluginManager().registerEvents(new Events(this), this);
		
		this.saveDefaultConfig();
		
		this.createCustomConfig();
		this.addRespawnsToList();

	}

	public void onDisable() {
		this.addRespawnsToConfig();
	}

	public String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public void addRespawnsToConfig() {
		ArrayList<String> list = new ArrayList<String>();
		// String[] list = null;
		list.clear();
		this.customConfig.set("respawns", "");
		this.saveCustomConfig();
		for (Respawns r : this.respawnList) {
			if (this.respawnList.size() > 0) {
				Player playerName = r.getPlayerName();
				int points = r.getPlayerRespawns();
				list.add(playerName.getDisplayName() + ", " + Integer.toString(points));
			}
		}
		this.getCustomConfig().set("respawns", list);
		this.saveCustomConfig();
	}

	@SuppressWarnings("deprecation")
	public void addRespawnsToList() {
		this.respawnList.clear();
		for (String i : this.customConfig.getStringList("respawns")) {
			String[] split = i.split(", ", 2);
			Bukkit.broadcastMessage(split[0]);
			Bukkit.broadcastMessage(split[1]);
			Player playerName = Bukkit.getPlayer(split[0]);
			int amtOfRespawns = Integer.parseInt(split[1]);
			this.respawnList.add(new Respawns(playerName, amtOfRespawns));
		}
	}

	public void clearConfig() {
		this.getCustomConfig().set("respawns", "");
		this.saveCustomConfig();
	}


	public FileConfiguration getCustomConfig() {
		return this.customConfig;
	}

	public void saveCustomConfig() {
		try {
			this.customConfig.save(customConfigFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createCustomConfig() {
		customConfigFile = new File(getDataFolder(), "respawns.yml");
		if (!customConfigFile.exists()) {
			customConfigFile.getParentFile().mkdirs();
			saveResource("respawns.yml", false);
		}

		customConfig = new YamlConfiguration();
		try {
			customConfig.load(customConfigFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}