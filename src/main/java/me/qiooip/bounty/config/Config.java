package me.qiooip.bounty.config;

import me.qiooip.bounty.Bounty;

public class Config {

    public static int MINIMAL_BOUNTY_AMOUNT;

    public Config() {
        ConfigFile config = Bounty.getInstance().getConfig();

        MINIMAL_BOUNTY_AMOUNT = config.getInt("MINIMAL_BOUNTY_AMOUNT");
    }
}
