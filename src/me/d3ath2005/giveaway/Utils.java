package me.d3ath2005.giveaway;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Utils {

	public static void countdown(List<String> list, CommandSender sender, FileConfiguration configFile,
			/* FileConfiguration winnersFile, */ MainClass main) {
		if (list.size() > 0) {
			new BukkitRunnable() {
				int timer = configFile.getInt("timer");

				@Override
				public void run() {
					String countdown;
					if (timer == 0) {
						Player p = Bukkit.getPlayer(list.get(ThreadLocalRandom.current().nextInt(list.size())));
						String playerchoose = configFile.getString("player-chosen").replace("%p", p.getName());
						Bukkit.broadcastMessage(color((playerchoose)));

						List<String> cmds = configFile.getStringList("Commands");
						if (!cmds.isEmpty()) {
							cmds.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									s.replace("%p", p.getName())));
						}

						if (configFile.getBoolean("send-title")) {
							String title = configFile.getString("title-msg").replace("%p", p.getName());
							String subtitle = configFile.getString("subtitle-msg").replace("%p", p.getName());
							int fadeIn = 30;
							int stay = 40;
							int fadeOut = 30;
							for (Player online : Bukkit.getOnlinePlayers()) {
								online.sendTitle(color(title), color(subtitle), fadeIn, stay, fadeOut);
							}
						}

						/*
						 * if (configFile.getBoolean("save-winners")) { Date d = new Date();
						 * winnersFile.set(p.getName(), "hey"); }
						 */

						cancel();
					} else if (timer == 1) {
						countdown = configFile.getString("countdown").replace("%t", String.valueOf(timer))
								.replace("seconds", "second").replace("secs", "sec");
						Bukkit.broadcastMessage(color(countdown));
						timer--;
					} else {
						countdown = configFile.getString("countdown").replace("%t", String.valueOf(timer));
						Bukkit.broadcastMessage(color(countdown));
						timer--;
					}

				}
			}.runTaskTimer(main, 0, 20);
		} else {
			String notenoughplayers = configFile.getString("not-enough-players");
			sender.sendMessage(color(notenoughplayers));
		}
	}

	public static void generateFile(MainClass main, String fileName) {
		File file = new File(main.getDataFolder(), fileName);

		if (!(file.getParentFile().exists())) {
			file.getParentFile().mkdirs();
		}
		if (!(file.exists())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("[SimpleChatGiveaway] There's a problem in file creation \"" + fileName + "\"");
				System.out.println("Exception Stacktrace:");
				e.printStackTrace();
				System.out.println("-----------------------");
			}
		}
	}

	public static void noPerm(CommandSender sender, MainClass main) {
		String noperm = main.getConfig().getString("no-perm");
		sender.sendMessage(color(noperm));
	}

	public static void unknownArgs(CommandSender sender, MainClass main) {
		String unknownargs = main.getConfig().getString("unknown-args");
		sender.sendMessage(color(unknownargs));
	}

	public static void configReloaded(CommandSender sender, MainClass main) {
		String configreloaded = main.getConfig().getString("config-reloaded");
		sender.sendMessage(color(configreloaded));
	}

	public static boolean hasDepend(Plugin plugin, MainClass main) {
		if (Bukkit.getPluginManager().isPluginEnabled("TitleAPI")) {
			return true;
		} else {
			Bukkit.getPluginManager().disablePlugin(plugin);
			throw new RuntimeException("SimpleChatGiveaway can't start, you must have TitleAPI installed.");
		}
	}

	private static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}