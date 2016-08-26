package net.pl3x.bukkit.deathmessages.command;

import net.pl3x.bukkit.deathmessages.DeathMessages;
import net.pl3x.bukkit.deathmessages.configuration.Config;
import net.pl3x.bukkit.deathmessages.configuration.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CmdDeathMessages implements TabExecutor {
    private final DeathMessages plugin;

    public CmdDeathMessages(DeathMessages plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            if ("reload".startsWith(args[0].toLowerCase())) {
                result.add("reload");
            }
        }
        return result;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.deathmessages")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission for that command!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + plugin.getName() + " v" + plugin.getDescription().getVersion() + "");
            return false;
        }

        Config.reload();
        Messages.getConfig().reload();

        sender.sendMessage(ChatColor.LIGHT_PURPLE + plugin.getName() + " v" + plugin.getDescription().getVersion() + " reloaded.");
        return true;
    }
}
