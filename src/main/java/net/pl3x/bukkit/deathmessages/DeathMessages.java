package net.pl3x.bukkit.deathmessages;

import net.pl3x.bukkit.deathmessages.command.CmdDeathMessages;
import net.pl3x.bukkit.deathmessages.configuration.Config;
import net.pl3x.bukkit.deathmessages.listener.CombatListener;
import net.pl3x.bukkit.deathmessages.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathMessages extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.reload();

        Bukkit.getPluginManager().registerEvents(new CombatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("deathmessages").setExecutor(new CmdDeathMessages());

        Logger.info(getName() + " v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        Logger.info(getName() + " disabled.");
    }

    public static DeathMessages getPlugin() {
        return DeathMessages.getPlugin(DeathMessages.class);
    }
}
