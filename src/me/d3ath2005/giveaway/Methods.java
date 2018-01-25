package me.d3ath2005.giveaway;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Methods {
	private MainClass main;

	public Methods(MainClass main) {
		this.main = main;
	}

	public void countdown(List<String> list, CommandSender sender) {
		if (list.size() > 0) {
			new BukkitRunnable() {
				int timer = main.getConfig().getInt("timer");

				@Override
				public void run() {
					String countdown;
					if (timer == 0) {
						Player p = Bukkit.getPlayer(list.get(ThreadLocalRandom.current().nextInt(list.size())));
						String playerchoose = main.getConfig().getString("player-chosen").replace("%p", p.getName());
						Bukkit.broadcastMessage(color((playerchoose)));

						List<String> cmds = main.getConfig().getStringList("Commands");
						if (!cmds.isEmpty()) {
							cmds.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									s.replace("%p", p.getName())));
						}

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
	}

	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}