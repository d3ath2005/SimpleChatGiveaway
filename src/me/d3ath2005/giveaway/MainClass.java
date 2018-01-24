package me.d3ath2005.giveaway;

import org.bukkit.plugin.java.JavaPlugin;

import me.d3ath2005.giveaway.commands.CommandGA;

public class MainClass extends JavaPlugin {
	public void onEnable() {
		getLogger().info("SCG has been enabled.");
		generateConfig();
		registerCmds();
	}

	public void onDisable() {
		getLogger().info("SCG has been disabled.");
	}

	private void generateConfig() {
		saveDefaultConfig();
	}

	private void registerCmds() {
		getCommand("giveaway").setExecutor(new CommandGA(this));
	}
}