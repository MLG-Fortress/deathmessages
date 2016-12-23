package net.pl3x.bukkit.deathmessages.hook;

import net.pl3x.bukkit.pl3xbot.Pl3xBot;

public class Pl3xBotHook {
    public static void sendMessageToDiscord(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Pl3xBot.getPlugin().sendToDiscord("*" + message.trim() + "*");
    }
}
