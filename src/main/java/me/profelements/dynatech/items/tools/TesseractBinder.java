package me.profelements.dynatech.items.tools;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import me.profelements.dynatech.compat.Pdc;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.profelements.dynatech.items.electric.transfer.Tesseract;
import me.profelements.dynatech.registries.Items;
import me.profelements.dynatech.utils.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class TesseractBinder extends SlimefunItem {
    public TesseractBinder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemHandler(bindTesseract());
    }

    private ItemUseHandler bindTesseract() {
        return e -> {
            e.cancel();

            Optional<Block> block = e.getClickedBlock();
            Optional<SlimefunItem> sfBlock = e.getSlimefunBlock();
            if (block.isPresent() && sfBlock.isPresent()) {
                Location blockLocation = block.get().getLocation();
                SlimefunItem sfItem = sfBlock.get();
                ItemStack item = e.getItem();
                Boolean hasPermision = Slimefun.getProtectionManager().hasPermission(e.getPlayer(), blockLocation,
                        Interaction.INTERACT_BLOCK);

                if (e.getPlayer().isSneaking()) {
                    String locString = Pdc.getString(item.getItemMeta(), Tesseract.WIRELESS_LOCATION_KEY);
                    if (item != null && hasPermision
                            && BlockStorage.checkID(blockLocation).equals(Items.TESSERACT.stack().getItemId())
                            && item.hasItemMeta() && locString != null) {
                        BlockStorage.addBlockInfo(blockLocation, "tesseract-pair-location", locString);
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                new TextComponent(ChatColor.WHITE + Messages.get(e.getPlayer(),
                                        "tesseract.connected", "%item% connected!",
                                        line -> line.replace("%item%", tesseractName(e.getPlayer())))));
                    }
                } else if (Boolean.TRUE.equals(hasPermision)
                        && sfItem.getId().equals(Items.TESSERACT.stack().getItemId()) && blockLocation != null) {
                    ItemMeta im = item.getItemMeta();
                    String locString = Tesseract.locationToString(blockLocation);

                    Pdc.setString(im, Tesseract.WIRELESS_LOCATION_KEY, locString);
                    item.setItemMeta(im);
                    Tesseract.setItemLore(item, blockLocation);
                }

            }
        };
    }

    /**
     * The Tesseract's display name in the viewer's own language, so the action bar never names a
     * translated block in English.
     *
     * @implNote The name keeps its own colour codes and the line's colour is restored after it,
     *           because the highlight sits at the start of the sentence rather than the end.
     */
    @Nonnull
    private static String tesseractName(@Nonnull Player player) {
        SlimefunItem tesseract = SlimefunItem.getByItem(Items.TESSERACT.stack());
        return (tesseract != null
                ? Slimefun.getItemTranslationService().getName(player, tesseract)
                : "Tesseract") + ChatColor.WHITE;
    }
}
