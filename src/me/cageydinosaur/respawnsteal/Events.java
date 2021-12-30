package me.cageydinosaur.respawnsteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

	Main plugin;

	public Events(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player joiner = e.getPlayer();
		e.setJoinMessage("hey");
		Respawns respawns = Respawns.getInfo(plugin.respawnList, joiner);
		if (Respawns.ifRespawns(plugin.respawnList, joiner)) {
			plugin.respawnList.add(new Respawns(joiner, 3));
			Bukkit.broadcastMessage("added");
		} else
			Bukkit.broadcastMessage("not");
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player recievingPlayer = e.getEntity();
		recievingPlayer.sendMessage("yee");
		if (e.getEntity().getKiller() instanceof Player) {
			Player killer = e.getEntity().getKiller();
			Respawns respawn = Respawns.getInfo(plugin.respawnList, killer);

			int respawnAmt = respawn.getPlayerRespawns() + 1;
			plugin.respawnList.remove(respawn);
			plugin.respawnList.add(new Respawns(killer, respawnAmt));

			killer.sendMessage(ChatColor.GREEN + "You have killed " + ChatColor.RED + recievingPlayer.getDisplayName()
					+ ChatColor.GREEN + " and have received one respawn. Your total is " + ChatColor.RED + respawnAmt
					+ ChatColor.GREEN + " respawns");
		}

		Respawns respawn = Respawns.getInfo(plugin.respawnList, recievingPlayer);
		if (respawn == null) {
			plugin.respawnList.add(new Respawns(recievingPlayer, 3));
			Bukkit.broadcastMessage("2");
		}
		if (respawn.getPlayerRespawns() == 0) {
			recievingPlayer.sendMessage(ChatColor.RED + "You are out of respawns.");
			recievingPlayer.setGameMode(GameMode.SPECTATOR);
		} else {
			int respawnAmt = respawn.getPlayerRespawns() - 1;
			plugin.respawnList.remove(respawn);
			plugin.respawnList.add(new Respawns(recievingPlayer, respawnAmt));

			recievingPlayer.sendMessage(
					ChatColor.GREEN + "You now have " + ChatColor.RED + respawnAmt + ChatColor.GREEN + " respawns");

		}
	}

}
