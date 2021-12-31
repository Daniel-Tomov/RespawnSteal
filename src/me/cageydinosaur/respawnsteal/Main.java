package me.cageydinosaur.respawnsteal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private File customConfigFile;
	private FileConfiguration customConfig;

	public ArrayList<String> respawnList = new ArrayList<String>();

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

	public String getPlayerName() {
		String playerName = "hey";
		for (String i : this.respawnList) {
			String[] split = i.split(", ", 2);
			playerName = split[0];
		}
		return playerName;
	}

	public void addInfo(Player player, int respawnsL) {
		this.respawnList.add(player.getDisplayName() + ", " + Integer.toString(respawnsL));
		player.sendMessage("done");
	}

	public void removeInfo(Player player) {
		for (String i : respawnList) {
			String[] split = i.split(", ", 2);
			if (split[0].equals(player.getDisplayName())) {
				Bukkit.getLogger().severe(i);
				Bukkit.getLogger().severe(Integer.toString(respawnList.indexOf(split[0] + ", " + split[1])));
				if (respawnList.remove(split[0] + ", " + split[1])) Bukkit.getLogger().severe("yes");
				Bukkit.getLogger().severe("removed");
				return;
			}
		}
	}

	public boolean ifRespawns(Player player) {
		for (String i : this.respawnList) {
			String[] split = i.split(", ", 2);
			if (Bukkit.getPlayer(split[0]) == player) {
				return true;
			}
		}
		return false;
	}

	public int getPlayerRespawns(Player player) {
		int respawnsLeft = 3;
		for (String i : this.respawnList) {
			String[] split = i.split(", ", 2);
			if (Bukkit.getPlayer(split[0]) == player) {
				respawnsLeft = Integer.parseInt(split[1]);
				return respawnsLeft;
			}
		}
		return respawnsLeft;
	}

	public void addRespawnsToConfig() {
		ArrayList<String> list = new ArrayList<String>();
		// String[] list = null;
		list.clear();
		/* this.clearConfig(); */
		for (String r : this.respawnList) {
			list.add(r);
		}
		this.getCustomConfig().set("respawns", list);
		this.saveCustomConfig();
	}

	@SuppressWarnings("deprecation")
	public void addRespawnsToList() {
		this.respawnList.clear();
		for (String i : this.customConfig.getStringList("respawns")) {
			String[] split = i.split(", ", 2);
			OfflinePlayer playerName = Bukkit.getOfflinePlayer(split[0]);
			int amtOfRespawns = Integer.parseInt(split[1]);

			this.getLogger().severe(playerName.getName());
			this.getLogger().severe(split[0]);
			this.getLogger().severe(Integer.toString(amtOfRespawns));

			this.respawnList.add(i);

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
			saveResource("respawns.yml", true);
		}

		customConfig = new YamlConfiguration();
		try {
			customConfig.load(customConfigFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}