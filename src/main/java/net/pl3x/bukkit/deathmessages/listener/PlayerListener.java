package net.pl3x.bukkit.deathmessages.listener;

import net.pl3x.bukkit.deathmessages.combat.Combat;
import net.pl3x.bukkit.deathmessages.combat.CombatCache;
import net.pl3x.bukkit.deathmessages.configuration.Messages;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        EntityDamageEvent lastDamageEvent = player.getLastDamageCause();
        EntityDamageEvent.DamageCause lastCause = lastDamageEvent.getCause();
        Combat combat = CombatCache.getCache().getCombat(player);

        String message = Messages.getMessage("default");
        LivingEntity attacker = null;
        ItemStack weapon = null;

        if (combat != null) {
            attacker = combat.getAttacker();
            weapon = combat.getWeapon();
        }

        if (lastCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (combat != null) {
                if (attacker instanceof Player) {
                    message = Messages.getMessage("pvp");
                } else if (attacker instanceof Creeper) {
                    message = Messages.getMessage("creeper");
                } else if (attacker instanceof EnderDragon) {
                    message = Messages.getMessage("dragon");
                } else if (attacker instanceof Wither) {
                    message = Messages.getMessage("wither");
                } else if (attacker instanceof Zombie) {
                    message = Messages.getMessage("zombie");
                } else {
                    message = Messages.getMessage("mob");
                }
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (combat == null) {
                message = Messages.getMessage("explode.default");
            } else {
                message = Messages.getMessage("explode.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.PROJECTILE) {
            message = Messages.getMessage("shot");
        } else if (lastCause == EntityDamageEvent.DamageCause.STARVATION) {
            message = Messages.getMessage("starve");
        } else if (lastCause == EntityDamageEvent.DamageCause.FALL) {
            if (combat == null) {
                message = Messages.getMessage("fall.default");
            } else {
                message = Messages.getMessage("fall.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.DROWNING) {
            if (combat == null) {
                message = Messages.getMessage("drown.default");
            } else {
                message = Messages.getMessage("drown.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.LAVA) {
            if (combat == null) {
                message = Messages.getMessage("lava.default");
            } else {
                message = Messages.getMessage("lava.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.FIRE_TICK) {
            if (combat == null) {
                message = Messages.getMessage("burn.default");
            } else {
                message = Messages.getMessage("burn.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.FIRE) {
            if (combat == null) {
                message = Messages.getMessage("fire.default");
            } else {
                message = Messages.getMessage("fire.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.VOID) {
            if (combat == null) {
                message = Messages.getMessage("void.default");
            } else {
                message = Messages.getMessage("void.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.POISON) {
            if (combat == null) {
                message = Messages.getMessage("poison.default");
            } else {
                message = Messages.getMessage("poison.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.MAGIC) {
            if (combat == null) {
                message = Messages.getMessage("magic.default");
            } else {
                message = Messages.getMessage("magic.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.SUFFOCATION) {
            message = Messages.getMessage("suffocate");
        } else if (lastCause == EntityDamageEvent.DamageCause.THORNS) {
            message = Messages.getMessage("thorns");
        } else if (lastCause == EntityDamageEvent.DamageCause.SUICIDE) {
            message = Messages.getMessage("suicide");
        } else if (lastCause == EntityDamageEvent.DamageCause.CONTACT) {
            if (combat == null) {
                message = Messages.getMessage("cactus.default");
            } else {
                message = Messages.getMessage("cactus.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            if (combat == null) {
                message = Messages.getMessage("explode.default");
            } else {
                message = Messages.getMessage("explode.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
            message = Messages.getMessage("dragon");
        } else if (lastCause == EntityDamageEvent.DamageCause.WITHER) {
            message = Messages.getMessage("wither");
        } else if (lastCause == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
            message = Messages.getMessage("falling-block");
        } else if (lastCause == EntityDamageEvent.DamageCause.HOT_FLOOR) {
            if (combat == null) {
                message = Messages.getMessage("magma.default");
            } else {
                message = Messages.getMessage("magma.combat");
            }
        } else if (lastCause == EntityDamageEvent.DamageCause.LIGHTNING) {
            if (combat == null) {
                message = Messages.getMessage("lightning.default");
            } else {
                message = Messages.getMessage("lightning.combat");
            }
        } else {
            // fallback to random default
            message = Messages.getMessage("default");
        }

        setDeathMessage(event, message
                .replace("{player}", player.getName())
                .replace("{attacker}", getAttacker(attacker))
                .replace("{weapon}", getWeapon(weapon)));
    }

    private void setDeathMessage(PlayerDeathEvent event, String message) {
        event.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&3" + message));
    }

    private String getAttacker(LivingEntity attacker) {
        if (attacker == null) {
            return "";
        }
        return WordUtils.capitalize(attacker.getType().name().toLowerCase());
    }

    private String getWeapon(ItemStack item) {
        if (item == null) {
            return "";
        }
        return "";
    }
}
