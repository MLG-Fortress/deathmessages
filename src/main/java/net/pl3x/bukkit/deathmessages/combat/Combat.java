package net.pl3x.bukkit.deathmessages.combat;

import net.pl3x.bukkit.deathmessages.DeathMessages;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Combat {
    private final LivingEntity attacker;
    private final ItemStack weapon;
    private final CombatTask task;

    public Combat(Player player, LivingEntity attacker) {
        this.attacker = attacker;
        this.weapon = attacker.getEquipment().getItemInMainHand();
        this.task = new CombatTask(player);
        task.runTaskLater(DeathMessages.getPlugin(), 100);
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public void cancel() {
        task.cancel();
    }
}
