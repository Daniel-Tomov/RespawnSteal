package me.cageydinosaur.respawnsteal;

import java.util.ArrayList;

import org.bukkit.entity.Player;


public class Respawns {

	Main plugin;

	public Respawns(Main plugin) {
		this.plugin = plugin;
	}

	private Player playerName;
	int respawnsLeft = 3;

	public Respawns(Player playerName, int respawnsL) {
		this.playerName = playerName;
		this.respawnsLeft = respawnsL;
	}

	public Player getPlayerName() {
		return playerName;
	}

	public int getPlayerRespawns() {
		return respawnsLeft;
	}

	public static Respawns getInfo(ArrayList<Respawns> list, Player playerName) {
		for (Respawns r : list) {
			if (r.getPlayerName() == playerName) {
				return r;
			}
		}
		return null;
	}

	public static boolean ifRespawns(ArrayList<Respawns> list, Player playerName) {
		for (int i = 0; i < list.size(); i++) {
			Respawns r = list.get(i);
			if (r.getPlayerName() == playerName) {
				return true;
			}
		}
		return false;
	}

}