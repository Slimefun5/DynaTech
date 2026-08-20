package me.profelements.dynatech;

import org.bukkit.Color;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;

import me.profelements.dynatech.utils.Liquid;
import me.profelements.dynatech.utils.LiquidRegistry;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class DynaTechLiquids {

    public static void registerLiquids(LiquidRegistry registry) {


        Liquid.init()
                .setKey(new NamespacedKey(NamespacedKey.MINECRAFT, "water"))
                .setName("Water")
                .setColor(Color.BLUE)
                .setLiquidMaterial(MaterialCompat.safe(XMaterial.WATER))
                .setStorageMaterial(MaterialCompat.safe(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE))
                .register(registry);

        Liquid.init()
                .setKey(new NamespacedKey(NamespacedKey.MINECRAFT, "lava"))
                .setName("Lava")
                .setColor(Color.ORANGE)
                .setLiquidMaterial(MaterialCompat.safe(XMaterial.LAVA))
                .setStorageMaterial(MaterialCompat.safe(XMaterial.ORANGE_STAINED_GLASS_PANE))
                .register(registry);

        Liquid.init()
                .setKey(new NamespacedKey(NamespacedKey.MINECRAFT, "honey"))
                .setName("Honey")
                .setColor(Color.YELLOW)
                .setLiquidMaterial(MaterialCompat.safe(XMaterial.LAVA))
                .setStorageMaterial(MaterialCompat.safe(XMaterial.YELLOW_STAINED_GLASS_PANE))
                .register(registry);

        Liquid.init()
                .setKey(new NamespacedKey(NamespacedKey.MINECRAFT, "potion"))
                .setName("Potion")
                .setColor(Color.WHITE)
                .setLiquidMaterial(MaterialCompat.safe(XMaterial.WATER))
                .setStorageMaterial(MaterialCompat.safe(XMaterial.WHITE_STAINED_GLASS_PANE))
                .register(registry);

        Liquid.init()
                .setKey(new NamespacedKey(NamespacedKey.MINECRAFT, "milk"))
                .setName("Milk")
                .setColor(Color.WHITE)
                .setLiquidMaterial(MaterialCompat.safe(XMaterial.WATER))
                .setStorageMaterial(MaterialCompat.safe(XMaterial.WHITE_STAINED_GLASS_PANE))
                .register(registry);
    }
}
