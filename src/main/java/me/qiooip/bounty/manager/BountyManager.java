package me.qiooip.bounty.manager;

import me.qiooip.bounty.config.Config;
import me.qiooip.bounty.config.ConfigFile;
import me.qiooip.bounty.config.Language;
import me.qiooip.bounty.integration.Vault;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BountyManager {

    private ConfigFile bountyFile;
    private Map<UUID, BountyData> bounties;

    public BountyManager() {
        this.bountyFile = new ConfigFile("bounties.yml");

        this.bounties = new HashMap<>();

        this.loadBountyData();
    }

    public void onDisable() {
        this.saveBountyData();
        this.bounties.clear();
    }

    private void loadBountyData() {
        this.bountyFile.getKeys(false).forEach(targetUuid -> {
            UUID target = UUID.fromString(targetUuid);
            String targetName = this.bountyFile.getString(targetUuid + ".name");

            Map<UUID, Integer> appliers = new HashMap<>();

            ConfigurationSection section = this.bountyFile.getConfigurationSection(targetUuid + ".appliers");
            section.getKeys(false).forEach(applierUuid ->
                appliers.put(UUID.fromString(applierUuid), section.getInt(applierUuid))
            );

            BountyData data = new BountyData(target, targetName, appliers);

            this.bounties.put(target, data);
        });
    }

    private void saveBountyData() {
        this.bounties.forEach((uuidTarget, bountyData) -> {
            this.bountyFile.set(uuidTarget.toString() + ".name", bountyData.getTargetName());

            bountyData.getAppliers().forEach((uuidApplier, integer) ->
                this.bountyFile.set(uuidTarget.toString() + ".appliers." + uuidApplier.toString(), integer)
            );
        });

        this.bountyFile.save();
    }

    public BountyData getBountyData(UUID target) {
        return this.bounties.get(target);
    }

    public void addBounty(Player target, Player applier, int bounty) {
        if(bounty < Config.MINIMAL_BOUNTY_AMOUNT) {
            applier.sendMessage(Language.MINIMAL_AMOUNT_REQUIRED.replace("<amount>",
            String.valueOf(Config.MINIMAL_BOUNTY_AMOUNT)));
            return;
        }

        if(!Vault.chargeMoney(applier, bounty)) {
            applier.sendMessage(Language.NOT_ENOUGH_MONEY);
            return;
        }

        this.bounties.putIfAbsent(target.getUniqueId(), new BountyData(target.getUniqueId(),
        target.getName(), applier.getUniqueId(), 0));

        BountyData data = this.getBountyData(target.getUniqueId());
        data.addApplier(applier.getUniqueId(), bounty);

        applier.sendMessage(Language.BOUNTY_ADDED.replace("<amount>", String.valueOf(bounty))
        .replace("<target>", target.getName()));
    }

    public void removeBounty(Player target, Player applier) {
        BountyData data = this.getBountyData(target.getUniqueId());

        if((data == null) || !data.containsApplier(applier.getUniqueId())) {
            applier.sendMessage(Language.BOUNTY_NOT_SET_YET.replace("<target>", target.getName()));
            return;
        }

        int bounty = data.removeApplier(applier.getUniqueId());

        Vault.addMoney(applier, bounty);

        applier.sendMessage(Language.BOUNTY_REMOVED.replace("<target>", target.getName()));

        if(data.getAppliers().isEmpty()) {
            this.bounties.remove(target.getUniqueId());

            this.bountyFile.set(target.getUniqueId().toString(), null);
            this.bountyFile.save();
        }
    }

    public void onTargetDeath(Player target) {
        this.bounties.remove(target.getUniqueId());

        this.bountyFile.set(target.getUniqueId().toString(), null);
        this.bountyFile.save();
    }

    public void listBounties(Player player) {
        if(this.bounties.isEmpty()) {
            player.sendMessage(Language.BOUNTY_LIST_EMPTY);
            return;
        }

        player.sendMessage(Language.BOUNTY_LIST_HEADER);

        this.bounties.values().stream().sorted(Comparator.comparing(BountyData::getAmount))
        .limit(10).forEach(bounty ->
            player.sendMessage(Language.BOUNTY_LIST_FORMAT
            .replace("<target>", bounty.getTargetName())
            .replace("<amount>", String.valueOf(bounty.getAmount())))
        );

        player.sendMessage(Language.BOUNTY_LIST_FOOTER);
    }
}
