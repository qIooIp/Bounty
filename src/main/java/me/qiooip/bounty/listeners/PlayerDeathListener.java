package me.qiooip.bounty.listeners;

import me.qiooip.bounty.Bounty;
import me.qiooip.bounty.config.Language;
import me.qiooip.bounty.integration.Vault;
import me.qiooip.bounty.manager.BountyData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();

        Player killer = target.getKiller();
        if(killer == null) return;

        BountyData data = Bounty.getInstance().getBountyManager().getBountyData(target.getUniqueId());
        if(data == null) return;

        Vault.addMoney(killer, data.getAmount());
        killer.sendMessage(Language.DEATH_MESSAGE.replace("<target>", target.getName())
        .replace("<amount>", String.valueOf(data.getAmount())));

        Bounty.getInstance().getBountyManager().onTargetDeath(target);
    }
}
