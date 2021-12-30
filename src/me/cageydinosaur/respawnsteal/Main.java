package me.cageydinosaur.respawnsteal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	public ArrayList<Respawns> respawnsLeft = new ArrayList<Respawns>();

	private File customConfigFile;
	private FileConfiguration customConfig;
	
	public void onEnable() {


		getCommand("respawnsteal").setExecutor(new Commands(this));
		getCommand("respawnsteal").setTabCompleter(new TabCompletion(this));
		this.saveDefaultConfig();
		this.createCustomConfig();
		this.getServer().getPluginManager().registerEvents(new Events(this), this);
	}

	public void onDisable() {
		addRespawnsToConfig();
		
	}
	
	public void addRespawnsToConfig() {
		ArrayList<String> list = new ArrayList<String>();
		// String[] list = null;
		list.clear();
		this.getCustomConfig().set("respawns", "");
		this.saveCustomConfig();
		for (Respawns r : this.respawnsLeft) {
			/*
			Friends f = this.friendsList.get(i);
			Player friender = f.getFriender();
			Player friended = f.getFriended();
			list.add(friender.getDisplayName() + ", " + friended.getDisplayName());
			*/
			if (this.respawnsLeft.size() > 0) {
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
		for (String i : this.getCustomConfig().getStringList("respawns")) {
			String[] split = i.split(", ", 2);
			Bukkit.broadcastMessage(split[0]);
			Bukkit.broadcastMessage(split[1]);
			Player playerName = Bukkit.getPlayer(split[0]);
			int amtOfRespawns = Integer.parseInt(split[1]);
			this.respawnsLeft.add(new Respawns(playerName, amtOfRespawns));
		}
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

	public String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public void reloadTheConfig() {
		this.reloadConfig();
	}
}
