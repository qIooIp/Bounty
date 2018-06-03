package me.qiooip.bounty.manager;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BountyData {

    @Getter private UUID target;
    @Getter private String targetName;
    @Getter private Map<UUID, Integer> appliers;

    public BountyData(UUID target, String targetName, UUID applier, Integer amount) {
        this.target = target;
        this.targetName = targetName;
        this.appliers = new HashMap<>();
        this.appliers.put(applier, amount);
    }

    public BountyData(UUID target, String targetName, Map<UUID, Integer> appliers) {
        this.target = target;
        this.targetName = targetName;
        this.appliers = appliers;
    }

    public boolean containsApplier(UUID applier) {
        return this.appliers.containsKey(applier);
    }

    public int getAmount() {
        return this.appliers.values().stream().reduce(0, Integer::sum);
    }

    public void addApplier(UUID applier, Integer amount) {
        this.appliers.putIfAbsent(applier, 0);
        this.appliers.put(applier, this.appliers.get(applier) + amount);
    }

    public int removeApplier(UUID appliers) {
        return this.appliers.remove(appliers);
    }
}
