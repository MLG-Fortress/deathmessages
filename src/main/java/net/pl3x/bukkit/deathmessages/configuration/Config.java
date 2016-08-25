package net.pl3x.bukkit.deathmessages.configuration;

import net.pl3x.bukkit.deathmessages.DeathMessages;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static boolean COLOR_LOGS = true;
    public static boolean DEBUG_MODE = false;

    public static void reload() {
        DeathMessages plugin = DeathMessages.getPlugin();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        COLOR_LOGS = config.getBoolean("color-logs", true);
        DEBUG_MODE = config.getBoolean("debug-mode", false);
    }
}
