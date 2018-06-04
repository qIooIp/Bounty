package me.qiooip.bounty;

import lombok.Getter;
import me.qiooip.bounty.commands.BountyCommand;
import me.qiooip.bounty.config.Config;
import me.qiooip.bounty.config.ConfigFile;
import me.qiooip.bounty.config.Language;
import me.qiooip.bounty.integration.Vault;
import me.qiooip.bounty.listeners.PlayerDeathListener;
import me.qiooip.bounty.manager.BountyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Bounty extends JavaPlugin {

    @Getter private static Bounty instance;

    private ConfigFile config;
    private BountyManager bountyManager;

    @Override
    public void onEnable() {
        if(!Vault.setupEconomy()) {
            getLogger().severe("Bounty - Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        instance = this;

        this.config = new ConfigFile("config.yml");
        new Config();
        new Language();

        this.bountyManager = new BountyManager();

        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);

        getCommand("bounty").setExecutor(new BountyCommand());
    }

    @Override
    public void onDisable() {
        this.bountyManager.onDisable();
    }
}
