package net.pl3x.bukkit.deathmessages;

import net.pl3x.bukkit.deathmessages.combat.CombatCache;
import net.pl3x.bukkit.deathmessages.command.CmdDeathMessages;
import net.pl3x.bukkit.deathmessages.configuration.Config;
import net.pl3x.bukkit.deathmessages.configuration.Messages;
import net.pl3x.bukkit.deathmessages.listener.CombatListener;
import net.pl3x.bukkit.deathmessages.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathMessages extends JavaPlugin {
    private static boolean hasPl3xBot = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("messages.yml", false);

        Config.reload();
        Messages.getConfig().reload();

        if (Bukkit.getPluginManager().isPluginEnabled("Pl3xBot")) {
            hasPl3xBot = true;
        }

        Bukkit.getPluginManager().registerEvents(new CombatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("deathmessages").setExecutor(new CmdDeathMessages(this));

        Logger.info(getName() + " v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        CombatCache.getCache().clear();

        Logger.info(getName() + " disabled.");
    }

    public static DeathMessages getPlugin() {
        return DeathMessages.getPlugin(DeathMessages.class);
    }

    public static boolean hasPl3xBot() {
        return hasPl3xBot;
    }
}
