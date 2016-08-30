package net.pl3x.bukkit.deathmessages.listener;

import net.pl3x.bukkit.deathmessages.combat.CombatCache;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.projectiles.ProjectileSource;

public class CombatListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        CombatCache.getCache().removeCombat(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        CombatCache.getCache().removeCombat(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        CombatCache.getCache().removeCombat(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return; // not a player
        }

        Entity damager = event.getDamager();
        if (damager instanceof Projectile) {
            ProjectileSource shooter = ((Projectile) damager).getShooter();
            if (!(shooter instanceof LivingEntity)) {
                return; // shot by non living (dispenser?)
            }
            // damaged player starts combat
            LivingEntity attacker = (LivingEntity) shooter;
            CombatCache.getCache().setCombat((Player) entity, attacker);
            if (shooter instanceof Player) {
                // player shooter starts combat
                CombatCache.getCache().setCombat((Player) attacker, (Player) entity);
            }
            return; // done
        }

        if (!(damager instanceof LivingEntity)) {
            return; // not damaged by living entity (void, falling block, lava, etc)
        }

        // damaged player starts combat
        CombatCache.getCache().setCombat((Player) entity, (LivingEntity) damager);

        if (damager instanceof Player) {
            // damager player starts combat
            CombatCache.getCache().setCombat((Player) damager, (Player) entity);
        }
    }
}
