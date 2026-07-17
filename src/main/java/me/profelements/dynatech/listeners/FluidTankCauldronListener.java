package me.profelements.dynatech.listeners;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import me.profelements.dynatech.fluids.FluidTank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;

/**
 * Isolated cauldron handler for {@link FluidTank}. {@link CauldronLevelChangeEvent} only exists on
 * MC 1.13+; keeping it out of {@code FluidTank} itself lets the tank register its bucket/consume
 * handlers on 1.8-1.12 while this one is registered behind a {@code Class.forName} guard.
 */
public class FluidTankCauldronListener implements Listener {

    private final FluidTank tank;

    public FluidTankCauldronListener(FluidTank tank) {
        this.tank = tank;
    }

    @EventHandler
    public void onCauldronInteract(CauldronLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            SlimefunItem sf = SlimefunItem.getByItem(p.getEquipment().getItemInHand());
            if (sf != null && tank.getId().equals(sf.getId())) {
                event.setCancelled(true);
            }
        }
    }
}
