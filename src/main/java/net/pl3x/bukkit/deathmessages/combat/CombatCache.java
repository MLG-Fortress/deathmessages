package net.pl3x.bukkit.deathmessages.combat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CombatCache {
    private static CombatCache cache;

    public static CombatCache getCache() {
        if (cache == null) {
            cache = new CombatCache();
        }
        return cache;
    }

    private final Map<Player, Combat> inCombat = new HashMap<>();

    public Combat getCombat(Player player) {
        return inCombat.get(player);
    }

    public void setCombat(Player player, LivingEntity attacker) {
        Combat combat = inCombat.get(player);
        if (combat != null) {
            combat.cancel();
        }
        inCombat.put(player, new Combat(player, attacker));
    }

    public void removeCombat(Player player) {
        Combat combat = inCombat.remove(player);
        if (combat != null) {
            combat.cancel();
        }
    }

    public void clear() {
        inCombat.clear();
    }
}
