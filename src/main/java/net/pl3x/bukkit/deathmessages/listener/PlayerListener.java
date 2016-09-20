package net.pl3x.bukkit.deathmessages.listener;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pl3x.bukkit.chatapi.ComponentSender;
import net.pl3x.bukkit.deathmessages.DeathMessages;
import net.pl3x.bukkit.deathmessages.Logger;
import net.pl3x.bukkit.deathmessages.combat.Combat;
import net.pl3x.bukkit.deathmessages.combat.CombatCache;
import net.pl3x.bukkit.deathmessages.configuration.Messages;
import net.pl3x.bukkit.deathmessages.hook.Pl3xBotHook;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {
    private Class<?> nbtTagCompound;
    private Method nmsCopy;

    public PlayerListener() {
        String version = Bukkit.getServer().getClass().toString().split("\\.")[3];
        try {
            nbtTagCompound = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            nmsCopy = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack")
                    .getDeclaredMethod("asNMSCopy", ItemStack.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Logger.warn("Something went wrong detecting server version. Weapon names will not display correctly..");
            Logger.warn(e.getLocalizedMessage());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPreparingForDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getDeathMessage() != null) {
            Logger.debug("Another plugin has changed the death message.");
            return; // a plugin has changed the death message
        }

        Player player = event.getEntity();

        EntityDamageEvent lastDamageEvent = player.getLastDamageCause();
        EntityDamageEvent.DamageCause lastCause = lastDamageEvent.getCause();
        Combat combat = CombatCache.getCache().getCombat(player);

        String world = player.getWorld().getName();
        String message;
        LivingEntity attacker = null;
        ItemStack weapon = null;

        if (combat != null) {
            attacker = combat.getAttacker();
            weapon = combat.getWeapon();
        }

        if (lastCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (attacker instanceof Player) {
                message = Messages.getMessage("pvp", world);
            } else if (attacker instanceof Creeper) {
                message = Messages.getMessage("creeper", world);
            } else if (attacker instanceof EnderDragon) {
                message = Messages.getMessage("dragon", world);
            } else if (attacker instanceof Wither) {
                message = Messages.getMessage("wither", world);
            } else if (attacker instanceof Zombie) {
                message = Messages.getMessage("zombie", world);
            } else {
                message = Messages.getMessage("mob", world);
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            message = Messages.getMessage("explode", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.PROJECTILE) {
            message = Messages.getMessage("shot", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.STARVATION) {
            message = Messages.getMessage("starve", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.FALL) {
            message = Messages.getMessage("fall", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.DROWNING) {
            message = Messages.getMessage("drown", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.LAVA) {
            message = Messages.getMessage("lava", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.FIRE_TICK) {
            message = Messages.getMessage("burn", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.FIRE) {
            message = Messages.getMessage("fire", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.VOID) {
            message = Messages.getMessage("void", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
            message = Messages.getMessage("fly-into-wall", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.POISON) {
            message = Messages.getMessage("poison", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.MAGIC) {
            message = Messages.getMessage("magic.default", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.SUFFOCATION) {
            message = Messages.getMessage("suffocate", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.THORNS) {
            message = Messages.getMessage("thorns", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.SUICIDE) {
            message = Messages.getMessage("suicide", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.CONTACT) {
            message = Messages.getMessage("cactus", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            message = Messages.getMessage("explode", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
            message = Messages.getMessage("dragon", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.WITHER) {
            message = Messages.getMessage("wither", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
            message = Messages.getMessage("falling-block", world);
        } else if (lastCause == EntityDamageEvent.DamageCause.HOT_FLOOR) {
            message = Messages.getMessage("magma", world, combat != null);
        } else if (lastCause == EntityDamageEvent.DamageCause.LIGHTNING) {
            message = Messages.getMessage("lightning", world, combat != null);
        } else {
            // fallback to random default
            message = Messages.getMessage("default", null);
        }

        if (message == null || ChatColor.stripColor(message).isEmpty()) {
            message = player.getName() + " has died.";
        }

        BaseComponent[] components = TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&e" + message));

        List<BaseComponent> expandedComponents = new ArrayList<>();
        BaseComponent spaceComponent = new TextComponent(" ");
        for (BaseComponent component : components) {
            String text = ((TextComponent) component).getText();
            for (String part : text.split(" ")) {
                BaseComponent componentPart = component.duplicate();

                if (part.toLowerCase().equals("{player}")) {
                    part = player.getName();
                } else if (part.toLowerCase().equals("{attacker}")) {
                    part = attacker == null ? "something" : attacker instanceof Player ? attacker.getName() :
                            WordUtils.capitalize(attacker.getType().name().toLowerCase().replace("_", " "));
                } else if (part.toLowerCase().equals("{weapon}")) {
                    expandedComponents.add(getWeapon(componentPart, weapon));
                    expandedComponents.add(spaceComponent);
                    continue;
                }

                ((TextComponent) componentPart).setText(part);

                expandedComponents.add(componentPart);
                expandedComponents.add(spaceComponent);
            }
        }

        components = expandedComponents.toArray(new BaseComponent[0]);
        for (Player online : Bukkit.getOnlinePlayers()) {
            ComponentSender.sendMessage(online, components);
        }

        if (DeathMessages.hasPl3xBot()) {
            Pl3xBotHook.sendMessageToDiscord(TextComponent.toLegacyText(components));
        }

        event.setDeathMessage(null);
    }

    private BaseComponent getWeapon(BaseComponent component, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            ((TextComponent) component).setText("bare hands");
            return component;
        }

        try {
            Object nms = nmsCopy.invoke(null, item);
            Object tag = nbtTagCompound.newInstance();
            nms.getClass().getDeclaredMethod("save", nbtTagCompound).invoke(nms, tag);

            ((TextComponent) component).setText((String) nms.getClass()
                    .getDeclaredMethod("getName").invoke(nms, (Object[]) null));
            BaseComponent[] tooltip = TextComponent.fromLegacyText(tag.toString());
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, tooltip));
        } catch (Exception ignore) {
        }

        return component;
    }
}
