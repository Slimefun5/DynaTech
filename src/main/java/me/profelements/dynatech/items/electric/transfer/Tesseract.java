package me.profelements.dynatech.items.electric.transfer;

import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.registries.Items;
import me.profelements.dynatech.utils.EnergyUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class Tesseract extends SlimefunItem implements EnergyNetProvider {
    public static final String WIRELESS_LOCATION_KEY = "dynatech:tesseract-pair-location";
    private final int capacity;
    private final int energyRate;

    public Tesseract(ItemGroup itemGroup, int capacity, int energyRate, SlimefunItemStack item, RecipeType recipeType,
            ItemStack[] recipe, ItemStack output) {
        super(itemGroup, item, recipeType, recipe, output);

        this.capacity = capacity;
        this.energyRate = energyRate;

        addItemHandler(onBlockBreak());

        new BlockMenuPreset(Items.Keys.TESSERACT.asSlimefunId(), "Tesseract") {

            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public boolean canOpen(Block b, Player p) {
                return p.hasPermission("slimefun.inventory.bypass") || Slimefun.getProtectionManager().hasPermission(p,
                        b.getLocation(), Interaction.INTERACT_BLOCK);

            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.INSERT) {
                    return getInputSlots();
                } else {
                    return getOutputSlots();
                }
            }
        };
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block block, SlimefunItem sfItem, Config data) {
                Tesseract.this.tick(block);

            }

        });
    }

    private ItemHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {

            @Override
            public void onPlayerBreak(BlockBreakEvent event, ItemStack block, List<ItemStack> drops) {
                BlockMenu inv = BlockStorage.getInventory(event.getBlock());

                if (inv != null) {
                    inv.dropItems(event.getBlock().getLocation(), getInputSlots());
                    inv.dropItems(event.getBlock().getLocation(), getOutputSlots());

                }

                BlockStorage.clearBlockInfo(event.getBlock().getLocation());
            }

        };
    }

    protected void tick(Block b) {
        String wirelessLocation = BlockStorage.getLocationInfo(b.getLocation(), "tesseract-pair-location");
        if (wirelessLocation != null) {
            sendItemsAndCharge(b, wirelessLocation);

        }
    }

    private void sendItemsAndCharge(Block b, String wirelessLocation) {
        Location tesseractPair = stringToLocation(wirelessLocation);

        if (!tesseractPair.getWorld().isChunkLoaded(tesseractPair.getBlockX() >> 4, tesseractPair.getBlockZ() >> 4)) {
            return;
        }

        if (BlockStorage.checkID(tesseractPair) != null
                && BlockStorage.checkID(tesseractPair).equals(Items.TESSERACT.stack().getItemId())) {

            BlockMenu toMenu = BlockStorage.getInventory(b.getLocation());

            if (toMenu == null) {
                return;
            }

            updateKnowledgePane(toMenu, getCharge(b.getLocation()));
            // Moves items from the paired tesseract's menu (foreign, remote) into this one off the async
            // ticker thread; run it on the main thread while either menu is watched so it can't race a
            // viewer's clicks (dupe), inline otherwise (async fast path).
            BlockStorage.mutateInventorySafely(
                () -> EnergyUtils.moveInventoryFromTo(new BlockPosition(tesseractPair), new BlockPosition(b), getInputSlots(), getOutputSlots()),
                tesseractPair, b.getLocation());
        }

    }

    @Override
    public int getGeneratedOutput(Location l, Config data) {
        String tesseractPairLocation = BlockStorage.getLocationInfo(l, "tesseract-pair-location");

        int chargedNeeded = getCapacity() - getCharge(l);

        if (chargedNeeded != 0 && tesseractPairLocation != null) {
            Location tesseractPair = stringToLocation(tesseractPairLocation);

            if (!tesseractPair.getWorld().isChunkLoaded(tesseractPair.getBlockX() >> 4,
                    tesseractPair.getBlockZ() >> 4)) {
                return 0;
            }

            if (BlockStorage.checkID(tesseractPair) != null
                    && BlockStorage.checkID(tesseractPair).equals(Items.TESSERACT.stack().getItemId())) {

                return EnergyUtils.moveEnergyFromTo(new BlockPosition(tesseractPair), new BlockPosition(l),
                        getEnergyRate(), getCapacity());
            }

            return 0;
        }

        return 0;
    }

    private void updateKnowledgePane(BlockMenu menu, int currentCharge) {
        if (menu == null) {
            return;
        }

        ItemStack knowledgePane = menu.getItemInSlot(4);
        ItemMeta im = knowledgePane.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();

        lore.clear();
        lore.add(" ");
        lore.add(ChatColor.WHITE + "Current Power: " + currentCharge);
        lore.add(ChatColor.WHITE + "Current Status: " + ChatColor.RED + "CONNECTED");

        im.setLore(lore);
        knowledgePane.setItemMeta(im);

        ItemStack coloredPane = new ItemStack(MaterialCompat.safe(XMaterial.RED_STAINED_GLASS_PANE));
        coloredPane.setItemMeta(im);
        menu.replaceExistingItem(4, coloredPane);
    }

    // Boilerplate for machines.
    public void constructMenu(BlockMenuPreset preset) {
        preset.drawBackground(ChestMenuUtils.getBackground(), getBorder());
        preset.drawBackground(ChestMenuUtils.getInputSlotTexture(), getInputBorder());
        preset.drawBackground(ChestMenuUtils.getOutputSlotTexture(), getOutputBorder());
        preset.addItem(
                4, CustomItemStack.create(MaterialCompat.safe(XMaterial.PURPLE_STAINED_GLASS_PANE), "&fKnowledge Pane",
                        "&fCurrent Power: Unknown", "&fCurrent Status: NOT CONNECTED"),
                ChestMenuUtils.getEmptyClickHandler());
    }

    public int[] getBorder() {
        return new int[] { 13, 22, 31, 49, 40 };
    }

    public int[] getInputBorder() {
        return new int[] { 0, 1, 2, 3, 45, 46, 47, 48 };
    }

    public int[] getOutputBorder() {
        return new int[] { 5, 6, 7, 8, 50, 51, 52, 53 };
    }

    public int[] getInputSlots() {
        return new int[] { 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 38, 39 };
    }

    public int[] getOutputSlots() {
        return new int[] { 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44 };
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public int getEnergyRate() {
        return energyRate;
    }

    public static void setItemLore(ItemStack item, Location l) {
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

    public static String locationToString(Location l) {
        return l.getWorld().getName() + ";" + l.getBlockX() + ";" + l.getBlockY() + ";" + l.getBlockZ();
    }

    public static final Location stringToLocation(String locString) {
        String[] locComponents = locString.split(";");
        return new Location(Bukkit.getWorld(locComponents[0]), Double.parseDouble(locComponents[1]),
                Double.parseDouble(locComponents[2]), Double.parseDouble(locComponents[3]));
    }
}

