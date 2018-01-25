package me.d3ath2005.giveaway.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.d3ath2005.giveaway.MainClass;
import me.d3ath2005.giveaway.Methods;

public class CommandGA implements CommandExecutor {

	private MainClass main;

	public CommandGA(MainClass main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("scg.start")) {
			List<String> players = new ArrayList<String>();
			for (Player p : Bukkit.getOnlinePlayers()) {
				players.add(p.getName());
			}

			Methods m = new Methods(main);
			m.countdown(players, sender);

		} else {
			String noperm = main.getConfig().getString("no-perm");
			sender.sendMessage(color(noperm));
		}

		return false;
	}

	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}