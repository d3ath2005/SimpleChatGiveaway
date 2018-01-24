package me.d3ath2005.giveaway.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.d3ath2005.giveaway.MainClass;

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

			if (players.size() > 0) {
				new BukkitRunnable() {
					int timer = main.getConfig().getInt("timer");

					@Override
					public void run() {
						String countdown;
						if (timer == 0) {
							Player p = Bukkit
									.getPlayer(players.get(ThreadLocalRandom.current().nextInt(players.size())));
							String playerchoose = main.getConfig().getString("player-chosen").replace("%p",
									p.getName());
							Bukkit.broadcastMessage(color((playerchoose)));

							List<String> cmds = main.getConfig().getStringList("Commands");
							cmds.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									s.replace("%p", p.getName())));

							cancel();
						} else if (timer == 1) {
							countdown = main.getConfig().getString("countdown").replace("%t", String.valueOf(timer))
									.replace("seconds", "second").replace("secs", "sec");
							Bukkit.broadcastMessage(color(countdown));
							timer--;
						} else {
							countdown = main.getConfig().getString("countdown").replace("%t", String.valueOf(timer));
							Bukkit.broadcastMessage(color(countdown));
							timer--;
						}

					}
				}.runTaskTimer(main, 0, 20);
			} else {
				String notenoughplayers = main.getConfig().getString("not-enough-players");
				sender.sendMessage(color(notenoughplayers));
			}

		} else

		{
			String noperm = main.getConfig().getString("no-perm");
			sender.sendMessage(color(noperm));
		}

		return false;
	}

	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}