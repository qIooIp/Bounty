package me.qiooip.bounty.manager;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class BountyData {

    private UUID target;
    private String targetName;
    private Map<UUID, Integer> appliers;

    BountyData(UUID target, String targetName, UUID applier, Integer amount) {
        this.target = target;
        this.targetName = targetName;
        this.appliers = new HashMap<>();
        this.appliers.put(applier, amount);
    }

    BountyData(UUID target, String targetName, Map<UUID, Integer> appliers) {
        this.target = target;
        this.targetName = targetName;
        this.appliers = appliers;
    }

    boolean containsApplier(UUID applier) {
        return this.appliers.containsKey(applier);
    }

    public int getAmount() {
        return this.appliers.values().stream().reduce(0, Integer::sum);
    }

    void addApplier(UUID applier, Integer amount) {
        this.appliers.putIfAbsent(applier, 0);
        this.appliers.put(applier, this.appliers.get(applier) + amount);
    }

    int removeApplier(UUID appliers) {
        return this.appliers.remove(appliers);
    }
}
