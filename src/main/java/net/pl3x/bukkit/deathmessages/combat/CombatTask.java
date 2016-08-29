package net.pl3x.bukkit.deathmessages.combat;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class CombatTask extends BukkitRunnable {
    private Player player;

    public CombatTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (player == null || !player.isOnline()) {
            cancel();
            return;
        }
        CombatCache.getCache().removeCombat(player);
    }

    @Override
    public void cancel() {
        super.cancel();
        player = null;
    }
}
