package me.profelements.dynatech.items.electric.transfer;

import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun5.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import me.profelements.dynatech.compat.Pdc;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.registries.Items;
import me.profelements.dynatech.utils.EnergyUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WirelessEnergyPoint extends SlimefunItem implements EnergyNetProvider {

    private static final String WIRELESS_LOCATION_KEY = "dynatech:wireless-location";
    private final int capacity;
    private final int energyRate;

    @ParametersAreNonnullByDefault
    public WirelessEnergyPoint(ItemGroup itemGroup, int capacity, int energyRate, SlimefunItemStack item,
            RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.capacity = capacity;
        this.energyRate = energyRate;

        addItemHandler(onRightClick(), onBlockPlace(), onBlockBreak());
    }

    @Override
    public int getGeneratedOutput(Location l, Config data) {
        String wirelessBankLocation = BlockStorage.getLocationInfo(l, "wireless-location");

        int chargedNeeded = getCapacity() - getCharge(l);

        if (chargedNeeded != 0 && wirelessBankLocation != null) {
            Location wirelessEnergyBank = stringToLocation(wirelessBankLocation);

            if (!wirelessEnergyBank.getWorld().isChunkLoaded(wirelessEnergyBank.getBlockX() >> 4,
                    wirelessEnergyBank.getBlockZ() >> 4)) {
                return 0;
            }

            if (BlockStorage.checkID(wirelessEnergyBank) != null && BlockStorage.checkID(wirelessEnergyBank)
                    .equals(Items.WIRELESS_ENERGY_BANK.stack().getItemId())) {

                String energyCharge = BlockStorage.getLocationInfo(l, "energy-charge");
                if (energyCharge == null) {
                    BlockStorage.addBlockInfo(l, "energy-charge", String.valueOf(0));
                }

                EnergyUtils.moveEnergyFromTo(new BlockPosition(wirelessEnergyBank), new BlockPosition(l),
                        getEnergyRate(), getCapacity());
            }
            return 0;
        }
        return 0;
    }

    private ItemHandler onRightClick() {
        return new ItemUseHandler() {

            @Override
            public void onRightClick(PlayerRightClickEvent event) {

                Optional<Block> blockClicked = event.getClickedBlock();
                Optional<SlimefunItem> sfBlockClicked = event.getSlimefunBlock();
                if (blockClicked.isPresent() && sfBlockClicked.isPresent()) {
                    Location blockLoc = blockClicked.get().getLocation();
                    SlimefunItem sfBlock = sfBlockClicked.get();
                    ItemStack item = event.getItem();

                    if (sfBlock != null
                            && Slimefun.getProtectionManager().hasPermission(event.getPlayer(), blockLoc,
                                    Interaction.INTERACT_BLOCK)
                            && sfBlock.getId().equals(Items.WIRELESS_ENERGY_BANK.stack().getItemId())
                            && blockLoc != null) {
                        event.cancel();
                        ItemMeta im = item.getItemMeta();
                        String locationString = locationToString(blockLoc);

                        Pdc.setString(im, WIRELESS_LOCATION_KEY, locationString);
                        item.setItemMeta(im);
                        setItemLore(item, blockLoc);
                    }
                }
            }
        };
    }

    private ItemHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent event) {

                Location blockLoc = event.getBlockPlaced().getLocation();
                ItemStack item = event.getItemInHand();
                String locationString = Pdc.getString(item.getItemMeta(), WIRELESS_LOCATION_KEY);

                if (item.getType() == Items.WIRELESS_ENERGY_POINT.stack().item().getType() && item.hasItemMeta()
                        && locationString != null) {
                    BlockStorage.addBlockInfo(blockLoc, "wireless-location", locationString);

                }
            }

        };
    }

    private ItemHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {

            @Override
            public void onPlayerBreak(BlockBreakEvent event, ItemStack block, List<ItemStack> drops) {
                BlockStorage.clearBlockInfo(event.getBlock().getLocation());
            }

        };
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public int getEnergyRate() {
        return energyRate;
    }

    private void setItemLore(ItemStack item, Location l) {
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains("Location: ")) {
                lore.remove(i);
                break;
            }
        }

        lore.add(ChatColor.WHITE + "Location: " + l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY()
                        + " " + l.getBlockZ());

        im.setLore(lore);
        item.setItemMeta(im);

    }

    private String locationToString(Location l) {
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    private Location stringToLocation(String str) {
        String[] locComponents = str.split(":");
        return new Location(Bukkit.getWorld(locComponents[0]), Double.parseDouble(locComponents[1]),
                Double.parseDouble(locComponents[2]), Double.parseDouble(locComponents[3]));
    }

}

