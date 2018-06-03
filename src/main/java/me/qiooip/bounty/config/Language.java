package me.qiooip.bounty.config;

import me.qiooip.bounty.Bounty;

import java.util.List;

public class Language {

    public static String BOUNTY_LIST_EMPTY;
    public static String BOUNTY_LIST_HEADER;
    public static String BOUNTY_LIST_FORMAT;
    public static String BOUNTY_LIST_FOOTER;

    public static String FOR_PLAYERS_ONLY;
    public static String INVALID_NUMBER;
    public static String PLAYER_NOT_ONLINE;
    public static String MINIMAL_AMOUNT_REQUIRED;
    public static String NOT_ENOUGH_MONEY;
    public static String BOUNTY_NOT_SET_YET;
    public static String BOUNTY_ADDED;
    public static String BOUNTY_REMOVED;
    public static String DEATH_MESSAGE;
    public static List<String> BOUNTY_COMMAND_USAGE;

    public Language() {
        ConfigFile config = Bounty.getInstance().getConfig();

        BOUNTY_LIST_EMPTY = config.getString("BOUTNY_LIST_EMPTY");
        BOUNTY_LIST_HEADER = config.getString("BOUNTY_LIST_HEADER");
        BOUNTY_LIST_FORMAT = config.getString("BOUNTY_LIST_FORMAT");
        BOUNTY_LIST_FOOTER = config.getString("BOUNTY_LIST_FOOTER");

        FOR_PLAYERS_ONLY = config.getString("FOR_PLAYERS_ONLY");
        INVALID_NUMBER = config.getString("INVALID_NUMBER");
        PLAYER_NOT_ONLINE = config.getString("PLAYER_NOT_ONLINE");
        MINIMAL_AMOUNT_REQUIRED = config.getString("MINIMAL_AMOUNT_REQUIRED");
        NOT_ENOUGH_MONEY = config.getString("NOT_ENOUGH_MONEY");
        BOUNTY_NOT_SET_YET = config.getString("BOUNTY_NOT_SET_YET");
        BOUNTY_ADDED = config.getString("BOUNTY_ADDED");
        BOUNTY_REMOVED = config.getString("BOUNTY_REMOVED");
        DEATH_MESSAGE = config.getString("DEATH_MESSAGE");
        BOUNTY_COMMAND_USAGE = config.getStringList("BOUNTY_COMMAND_USAGE");
    }
}
