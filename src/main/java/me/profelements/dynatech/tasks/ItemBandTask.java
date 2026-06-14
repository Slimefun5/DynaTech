package me.profelements.dynatech.tasks;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import me.profelements.dynatech.compat.Pdc;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.items.misc.ItemBand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.EntityCompat;
import me.profelements.dynatech.utils.MaterialCompat;

public class ItemBandTask implements Runnable {

    //The value if not null will be a SlIMEFUN_ID that is an Item

    public ItemBandTask() {}

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.isValid() || p.isDead()) {
                continue;
            }
            for (ItemStack item : p.getEquipment().getArmorContents()) {
                testItemBand(p, item);
            }
            testItemBand(p, p.getEquipment().getItemInMainHand());
        }
    }

    private static void testItemBand(@Nonnull Player p, @Nullable ItemStack item) {
        if (item != null && item.getType() != MaterialCompat.safe(XMaterial.AIR) && item.hasItemMeta()) {
            String id = Pdc.getString(item.getItemMeta(), ItemBand.KEY);

            if (id != null) {
                SlimefunItem sfItem = SlimefunItem.getById(id);

                if (sfItem instanceof ItemBand) {
                    ItemBand itemBand = (ItemBand) sfItem;

                    DynaTech.runSync(() -> {
                        for (PotionEffect pe : itemBand.getPotionEffects()) {
                            if (pe.getType() == PotionEffectType.HEALTH_BOOST)
                            {
                                double health = p.getHealth();
                                p.addPotionEffect(pe);
                                double maxHealth = EntityCompat.maxHealth(p);
                                if (health > maxHealth) {
                                    p.setHealth(maxHealth);
                                } else {
                                    p.setHealth(health);
                                }
                                
                            } else {
                                p.addPotionEffect(pe);
                            }
                        }
                    });
                }
            }
        }
    }

}

