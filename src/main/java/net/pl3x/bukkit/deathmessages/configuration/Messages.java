package net.pl3x.bukkit.deathmessages.configuration;

import net.pl3x.bukkit.deathmessages.DeathMessages;
import net.pl3x.bukkit.deathmessages.Logger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Messages extends YamlConfiguration {
    private static Messages config;

    public static Messages getConfig() {
        if (config == null) {
            config = new Messages();
        }
        return config;
    }

    public static String getMessage(String key, String world) {
        return getMessage(key, world, false);
    }

    public static String getMessage(String key, String world, boolean combat) {
        if (world != null && !world.isEmpty()) {
            String msg = getConfig().getRandom(key + "." + world + (combat ? ".combat" : ".default"));
            if (msg != null && !msg.isEmpty()) {
                return msg;
            }
            Logger.debug("No message entry with key " + key + " for world " + world);
        }
        return getConfig().getRandom(key + (combat ? ".combat" : ".default"));
    }

    private final File file;
    private final Object saveLock = new Object();
    private final Random random;

    private Messages() {
        super();
        this.random = new Random();
        this.file = new File(DeathMessages.getPlugin().getDataFolder(), "messages.yml");
        reload();
    }

    public void reload() {
        synchronized (saveLock) {
            try {
                this.load(file);
            } catch (Exception ignore) {
            }
        }
    }

    private String getRandom(String key) {
        List<String> list = getStringList(key);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', list.get(random.nextInt(list.size())));
    }
}
