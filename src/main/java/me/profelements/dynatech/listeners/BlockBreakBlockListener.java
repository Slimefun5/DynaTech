package me.profelements.dynatech.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class BlockBreakBlockListener implements Listener {

    public BlockBreakBlockListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
