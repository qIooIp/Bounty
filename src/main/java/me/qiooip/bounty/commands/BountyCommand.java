package me.qiooip.bounty.commands;

import me.qiooip.bounty.Bounty;
import me.qiooip.bounty.config.Language;
import me.qiooip.bounty.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Language.FOR_PLAYERS_ONLY);
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 1) {

            this.handleLengthOne(player, args[0]);

        } else if(args.length == 2) {

            this.handleLengthTwo(player, args[0], args[1]);

        } else if(args.length == 3) {

            this.handleLengthThree(player, args[0], args[1], args[2]);

        } else {

            this.sendUsage(player);

        }

        return true;
    }

    private void handleLengthOne(Player sender, String arg1) {
        if(arg1.equalsIgnoreCase("help")) {

            this.sendUsage(sender);

        } else if(arg1.equalsIgnoreCase("list")) {

            Bounty.getInstance().getBountyManager().listBounties(sender);

        } else {
            this.sendUsage(sender);
        }
    }

    private void handleLengthTwo(Player sender, String arg1, String arg2) {
        if(arg1.equalsIgnoreCase("remove")) {

            Player target = Bukkit.getPlayer(arg2);

            if(target == null) {
                sender.sendMessage(Language.PLAYER_NOT_ONLINE.replace("<player>", arg2));
                return;
            }

            Bounty.getInstance().getBountyManager().removeBounty(target, sender);

        } else {
            this.sendUsage(sender);
        }
    }

    private void handleLengthThree(Player sender, String arg1, String arg2, String arg3) {
        if(arg1.equalsIgnoreCase("add")) {

            Player target = Bukkit.getPlayer(arg2);

            if(target == null) {
                sender.sendMessage(Language.PLAYER_NOT_ONLINE.replace("<player>", arg2));
                return;
            }

            if(!StringUtils.isNumber(arg3)) {
                sender.sendMessage(Language.INVALID_NUMBER);
                return;
            }

            Bounty.getInstance().getBountyManager().addBounty(target, sender, Integer.valueOf(arg3));

        } else {
            this.sendUsage(sender);
        }
    }

    private void sendUsage(Player sender) {
        Language.BOUNTY_COMMAND_USAGE.forEach(sender::sendMessage);
    }
}
