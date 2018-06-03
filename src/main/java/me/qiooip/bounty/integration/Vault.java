package me.qiooip.bounty.integration;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Economy econ;

    public static boolean setupEconomy() {
        if(Bukkit.getPluginManager().getPlugin("Vault") == null) return false;

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if(rsp == null) return false;

        econ = rsp.getProvider();
        return econ != null;
    }

    public static boolean chargeMoney(Player player, int amount) {
        return econ.has(player.getName(), amount) && econ.withdrawPlayer(player.getName(), amount).transactionSuccess();
    }

    public static void addMoney(Player player, int amount) {
        econ.depositPlayer(player.getName(), amount);
    }
}
