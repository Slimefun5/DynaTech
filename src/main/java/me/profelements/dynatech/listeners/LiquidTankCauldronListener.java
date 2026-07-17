package me.profelements.dynatech.listeners;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import me.profelements.dynatech.items.tools.LiquidTank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Isolated cauldron handler for {@link LiquidTank}. {@link CauldronLevelChangeEvent} only exists on
 * MC 1.13+; keeping it out of {@code LiquidTank} itself lets the tank register its other handlers on
 * 1.8-1.12 while this one is registered behind a {@code Class.forName} guard.
 */
public class LiquidTankCauldronListener implements Listener {

    private final LiquidTank tank;

    public LiquidTankCauldronListener(LiquidTank tank) {
        this.tank = tank;
    }

    @EventHandler
    public void onCauldronFill(CauldronLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            ItemStack item = player.getInventory().getItemInHand();
            if (tank.isItem(item) && tank.canUse(player, true) && SlimefunItem.getByItem(item) instanceof LiquidTank) {
                e.setCancelled(true);
            }
        }
    }
}
