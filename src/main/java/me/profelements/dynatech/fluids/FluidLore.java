package me.profelements.dynatech.fluids;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun5.api.items.LiveLore;
import me.profelements.dynatech.DynaTech;

/**
 * What a tank holds and how much, shown in each viewer's language.
 */
public final class FluidLore {

    private static final List<String> NAMED_FLUIDS = Arrays.asList("water", "lava", "milk", "potion");

    private FluidLore() {}

    /**
     * @param fluid a fluid key such as {@code water}, or a material name such as {@code STATIONARY_WATER}
     */
    public static void show(@Nonnull ItemMeta meta, @Nonnull String fluid, int amount, int capacity) {
        meta.setLore(null);
        LiveLore.set(meta, Arrays.asList(held(fluid), LiveLore.Line.of(DynaTech.getInstance(), "tank.amount", amount, capacity)));
    }

    public static void clear(@Nonnull ItemMeta meta) {
        meta.setLore(null);
        LiveLore.clear(meta);
    }

    private static LiveLore.Line held(String fluid) {
        String lower = fluid.toLowerCase(Locale.ROOT);
        for (String named : NAMED_FLUIDS) {
            if (lower.contains(named)) {
                return LiveLore.Line.of(DynaTech.getInstance(), "tank.held-" + named);
            }
        }
        return LiveLore.Line.of(DynaTech.getInstance(), "tank.held-other", fluid);
    }
}
