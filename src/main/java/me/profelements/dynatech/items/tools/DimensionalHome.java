package me.profelements.dynatech.items.tools;

import io.github.thebusybiscuit.slimefun5.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.libraries.dough.config.Config;
import me.profelements.dynatech.compat.Pdc;
import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.registries.Items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.List;

public class DimensionalHome extends SlimefunItem {

    private static final String CHUNK_KEY = "dynatech:chunk-key";
    private static final World DIM_HOME_WORLD = Bukkit.getServer().getWorld("dimensionalhome");
    private static final Config CURRENT_HIGHEST_CHUNK_ID = new Config("plugins/DynaTech/current-chunk-highest.yml");
    private int id = CURRENT_HIGHEST_CHUNK_ID.getInt("current-chunk-highest-id");

    public DimensionalHome(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        addItemHandler(onRightClick());
    }

    public ItemUseHandler onRightClick() {
        return new ItemUseHandler() {
            @Override
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();

                Player p = e.getPlayer();
                ItemStack item = e.getItem();
                int chunkKey = Pdc.getInt(item.getItemMeta(), CHUNK_KEY);

                if (SlimefunUtils.isItemSimilar(item, Items.DIMENSIONAL_HOME.stack().item(), true)) {
                    if (chunkKey > 0) {
                        if (p.getLocation().getWorld() != DIM_HOME_WORLD) {
                            Location dimHomeLocation = new Location(DIM_HOME_WORLD, 16 * chunkKey + 8d, 65, 8);
                            p.teleport(dimHomeLocation);
                        } else {
                            if (p.getBedSpawnLocation() != null) {
                                p.teleport(p.getBedSpawnLocation());
                            } else {
                                p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                            }
                        }
                    } else {
                        // Setup ChunkKey
                        updateLore(item);
                    }
                }
            }
        };
    }

    private void updateLore(@Nonnull ItemStack item) {
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();

        for (int line = 0; line < lore.size(); line++) {
            if (lore.get(line).contains("CHUNK ID: <id>")) {
                id++;
                lore.set(line, lore.get(line).replace("<id>", String.valueOf(id)));
                Pdc.setInt(im, CHUNK_KEY, id);

                // THIS IS PROBABLY BAD AND A BAD WAY TO KEEP AN CHUNK ID
                CURRENT_HIGHEST_CHUNK_ID.setValue("current-chunk-highest-id", id);
                CURRENT_HIGHEST_CHUNK_ID.save();
            }

        }

        im.setLore(lore);
        item.setItemMeta(im);
    }

}

