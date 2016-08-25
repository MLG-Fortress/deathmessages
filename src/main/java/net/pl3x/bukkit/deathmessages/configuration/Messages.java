package net.pl3x.bukkit.deathmessages.configuration;

import net.pl3x.bukkit.deathmessages.DeathMessages;
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

    public static String getMessage(String key) {
        return getConfig().getRandom(key);
    }

    private final File file;
    private final Object saveLock = new Object();
    private final Random random;

    private Messages() {
        super();
        this.random = new Random();
        this.file = new File(DeathMessages.getPlugin().getDataFolder(), "messages.yml");
        load();
    }

    private void load() {
        synchronized (saveLock) {
            try {
                this.load(file);
            } catch (Exception ignore) {
            }
        }
    }

    public String getRandom(String key) {
        List<String> list = getStringList(key);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', list.get(random.nextInt(list.size())));
    }
}
