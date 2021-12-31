package me.cageydinosaur.respawnsteal;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {

	Main plugin;

	public Cmd(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("respawnsteal"))) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		} else if (sender.hasPermission("respawnsteal")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "Usage:");
				if (sender.hasPermission("respawnsteal.add"))
					sender.sendMessage(ChatColor.GREEN + "/respawnsteal add <player> - gives player 1 respawn");
				if (sender.hasPermission("respawnsteal.take"))
					sender.sendMessage(ChatColor.GREEN + "/respawnsteal take <player> - takes one respawn from player");
				if (!(sender.hasPermission("respawnsteal.take")) && !(sender.hasPermission("respawnsteal.add"))) {
				}
				return true;
			} else if (args.length > 0) {

				if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("respawnsteal.reload")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that!");
						return true;
					}
					plugin.reloadConfig();
					sender.sendMessage("Reloaded the config");
					return true;

				}else if (args[0].equalsIgnoreCase("rr")) {
					if (!sender.hasPermission("respawnsteal.reloadrespawns")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that!");
						return true;
					}
					plugin.addRespawnsToList();
					
					sender.sendMessage("Added respawns to list");
					return true;

				} else if (args[0].equalsIgnoreCase("rc")) {
					if (!sender.hasPermission("respawnsteal.reloadrespawnsconfig")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that!");
						return true;
					}
					plugin.addRespawnsToConfig();					
					sender.sendMessage("Added respawns to config");
					return true;

				} else if (args[0].equalsIgnoreCase("add")) {
					if (!(sender.hasPermission("respawnsteal.add"))) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that!");
						return true;
					}
					if (args.length == 1) {
						sender.sendMessage(ChatColor.RED + "You must specify a player name");
						return true;
					} else if (args.length == 2) {
						Player recievingPlayer = Bukkit.getPlayer(args[1]);
						if (recievingPlayer == null) {
							sender.sendMessage(ChatColor.RED + "That player is not online");
							return true;
						}
						
						int respawnAmt = plugin.getPlayerRespawns(recievingPlayer) + 1;
						plugin.removeInfo(recievingPlayer);
						plugin.addInfo(recievingPlayer, respawnAmt);
						
						sender.sendMessage(ChatColor.RED + recievingPlayer.getDisplayName() + ChatColor.GREEN + " now has " + ChatColor.RED
								+ respawnAmt + ChatColor.GREEN + " respawns");

						return true;
					}

				} else if (args[0].equalsIgnoreCase("take")) {
					if (!(sender.hasPermission("respawnsteal.take"))) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that!");
						return true;
					}
					if (args.length == 1) {
						sender.sendMessage(ChatColor.RED + "You must specify a player name");
						return true;
					} else if (args.length == 2) {
						Player recievingPlayer = Bukkit.getPlayer(args[1]);
						if (recievingPlayer == null) {
							sender.sendMessage(ChatColor.RED + "That player is not online");
							return true;
						}
						
						int respawnAmt = plugin.getPlayerRespawns(recievingPlayer) - 1;
						plugin.removeInfo(recievingPlayer);
						plugin.addInfo(recievingPlayer, respawnAmt);
						
						sender.sendMessage(ChatColor.RED + recievingPlayer.getDisplayName() + ChatColor.GREEN + " now has " + ChatColor.RED
								+ respawnAmt + ChatColor.GREEN + " respawns");

						return true;
					}

				}
			}
			return true;
		}
		return false;
	}
}