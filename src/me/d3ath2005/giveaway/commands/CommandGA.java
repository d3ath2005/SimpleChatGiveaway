package me.d3ath2005.giveaway.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.d3ath2005.giveaway.MainClass;
import me.d3ath2005.giveaway.Utils;

public class CommandGA implements CommandExecutor {

	private MainClass main;

	public CommandGA(MainClass main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			Utils.unknownArgs(sender, main);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("scg.help")) {
					sender.sendMessage("§5/giveaway help §7- §5Shows the page you're looking at.");
					sender.sendMessage("§5/giveaway reload §7- §5Reloads the configuration file.");
					sender.sendMessage("§5/giveaway start §7- §5Starts a giveaway.");
				} else {
					Utils.noPerm(sender, main);
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("scg.reload")) {
					main.reloadConfig();
					Utils.configReloaded(sender, main);
				} else {
					Utils.noPerm(sender, main);
				}
			} else if (args[0].equalsIgnoreCase("start")) {
				if (sender.hasPermission("scg.start")) {
					List<String> players = new ArrayList<String>();
					for (Player p : Bukkit.getOnlinePlayers()) {
						players.add(p.getName());
					}
					/*
					 * File winnersFile = new File(main.getDataFolder(), "winners.yml");
					 * YamlConfiguration winnersConfig =
					 * YamlConfiguration.loadConfiguration(winnersFile);
					 */

					Utils.countdown(players, sender, main.getConfig(), main);
				} else {
					Utils.noPerm(sender, main);
				}
			} else {
				Utils.unknownArgs(sender, main);
			}
		} else {
			Utils.unknownArgs(sender, main);
		}
		return false;
	}
}