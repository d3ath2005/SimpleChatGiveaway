package me.d3ath2005.giveaway;

import org.bukkit.plugin.java.JavaPlugin;

import me.d3ath2005.giveaway.commands.CommandGA;

public class MainClass extends JavaPlugin {
	@SuppressWarnings("unused")
	public void onEnable() {
		//if (Utils.hasDepend(this, this)) {
			getLogger().info("Enabled");
			/* Utils.generateFile(this, "winners.yml"); */
			Metrics metrics = new Metrics(this);
			generateConfig();
			registerCmds();
		//} else {
//
		//}
	}

	public void onDisable() {
		getLogger().info("Disabled");
	}

	private void generateConfig() {
		saveDefaultConfig();
	}

	private void registerCmds() {
		getCommand("giveaway").setExecutor(new CommandGA(this));
	}
}