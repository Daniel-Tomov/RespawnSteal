package me.cageydinosaur.respawnsteal;

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
		if (!(plugin.ifRespawns(joiner))) {
			plugin.addInfo(joiner, 3);
			joiner.sendMessage(ChatColor.GREEN + "You have " + ChatColor.RED + "3" + ChatColor.GREEN
					+ " respawns. If you die you will " + ChatColor.RED + "lose one" + ChatColor.GREEN + " respawn. If you "
					+ ChatColor.RED + "kill" + ChatColor.GREEN + " other players, you will " + ChatColor.RED + "gain one"
					+ ChatColor.GREEN + " respawn. Once you lose all of your respawns, you will be put into "
					+ ChatColor.RED + "Spectator Mode" + ChatColor.GREEN + ". Have fun playing!");
		} else {
			int respawnAmt = plugin.getPlayerRespawns(joiner);
			joiner.sendMessage(ChatColor.GREEN + "Just a reminder, you have " + ChatColor.RED + respawnAmt
					+ ChatColor.GREEN + " respawns.");
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player recievingPlayer = e.getEntity();

		if (!(plugin.ifRespawns(recievingPlayer))) {
			plugin.addInfo(recievingPlayer, 3);
		}
		if (e.getEntity().getKiller() instanceof Player) {
			Player killer = e.getEntity().getKiller();
			int respawnAmt = plugin.getPlayerRespawns(killer) + 1;
			plugin.removeInfo(killer);
			plugin.addInfo(killer, respawnAmt);

			killer.sendMessage(ChatColor.GREEN + "You have killed " + ChatColor.RED + recievingPlayer.getDisplayName()
					+ ChatColor.GREEN + " and have received one respawn. Your total is " + ChatColor.RED + respawnAmt
					+ ChatColor.GREEN + " respawns");
		}

		int respawnAmt = plugin.getPlayerRespawns(recievingPlayer);
		if (respawnAmt == 0) {
			recievingPlayer.sendMessage(ChatColor.RED + "You are out of respawns.");
			recievingPlayer.setGameMode(GameMode.SPECTATOR);
		} else {
			respawnAmt = respawnAmt - 1;
			plugin.removeInfo(recievingPlayer);
			plugin.addInfo(recievingPlayer, respawnAmt);

			recievingPlayer.sendMessage(ChatColor.GREEN + "You have died" + ChatColor.GREEN + " and now have "
					+ ChatColor.RED + respawnAmt + ChatColor.GREEN + " respawns");

		}
	}

}
