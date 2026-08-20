package me.profelements.dynatech.items.electric.transfer;

import io.github.thebusybiscuit.slimefun5.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import me.profelements.dynatech.compat.Pdc;
import io.github.thebusybiscuit.slimefun5.libraries.dough.inventory.InvUtils;
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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class WirelessItemOutput extends SlimefunItem implements EnergyNetComponent {

    protected static final String WIRELESS_LOCATION_KEY = "dynatech:wireless-input-location";
    private final int capacity;

    public WirelessItemOutput(ItemGroup itemGroup, int capacity, SlimefunItemStack item, RecipeType recipeType,
            ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.capacity = capacity;

        addItemHandler(onBlockBreak(), onBlockPlace(), onRightClick());

        new BlockMenuPreset(Items.Keys.WIRELESS_ITEM_OUTPUT.asSlimefunId(), "Wireless Item Output") {
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
                WirelessItemOutput.this.tick(block);

            }

        });
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

                    if (sfBlock != null && sfBlock.getId().equals(Items.WIRELESS_ITEM_INPUT.stack().getItemId())
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

                if (item.getType() == Items.WIRELESS_ITEM_OUTPUT.stack().item().getType() && item.hasItemMeta()
                        && locationString != null) {
                    BlockStorage.addBlockInfo(blockLoc, "wireless-input-location", locationString);

                }
            }

        };
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
        String wirelessLocation = BlockStorage.getLocationInfo(b.getLocation(), "wireless-input-location");
        if (wirelessLocation != null) {
            sendItemsFromInput(b, wirelessLocation);

        }
    }

    private void sendItemsFromInput(Block b, String wirelessLocation) {
        Location wirelessItemInput = stringToLocation(wirelessLocation);

        if (!wirelessItemInput.getWorld().isChunkLoaded(wirelessItemInput.getBlockX() >> 4,
                wirelessItemInput.getBlockZ() >> 4)) {
            return;
        }

        if (BlockStorage.checkID(wirelessItemInput) != null
                && BlockStorage.checkID(wirelessItemInput).equals(Items.WIRELESS_ITEM_INPUT.stack().getItemId())) {
            BlockMenu input = BlockStorage.getInventory(wirelessItemInput);
            BlockMenu output = BlockStorage.getInventory(b);
            updateKnowledgePane(output, getCharge(b.getLocation()));

            for (int i : getOutputSlots()) {
                if (getCharge(wirelessItemInput) < getEnergyConsumption()
                        || getCharge(b.getLocation()) < getEnergyConsumption()) {
                    return;
                }
                ItemStack itemStack = input.getItemInSlot(i);

                if (itemStack != null && itemStack.getType() != MaterialCompat.safe(XMaterial.AIR)
                        && InvUtils.fitAll(output.toInventory(), new ItemStack[] { itemStack }, getOutputSlots())) {
                    removeCharge(wirelessItemInput, getEnergyConsumption());
                    removeCharge(b.getLocation(), getEnergyConsumption());
                    // Async ticker zeroes a remote input menu and writes the output menu, both player-viewable; run on main thread while watched so a viewer's click can't dupe.
                    BlockStorage.mutateInventorySafely(() -> {
                        output.pushItem(itemStack, getOutputSlots());
                        itemStack.setAmount(0);
                    }, wirelessItemInput, b.getLocation());
                }
            }

        }

    }

    private void updateKnowledgePane(BlockMenu menu, int currentCharge) {
        ItemStack knowledgePane = menu.getItemInSlot(4);
        ItemMeta im = knowledgePane.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();

        lore.clear();
        lore.add(" ");
        lore.add(ChatColor.WHITE + "Current Power: " + currentCharge);
        lore.add(ChatColor.WHITE + "Current Status: " + ChatColor.RED + "CONNECTED");
        knowledgePane.setType(MaterialCompat.safe(XMaterial.RED_STAINED_GLASS_PANE));

        im.setLore(lore);
        knowledgePane.setItemMeta(im);
    }

    public void constructMenu(BlockMenuPreset preset) {
        preset.drawBackground(ChestMenuUtils.getOutputSlotTexture(), getBorder());
        preset.addItem(
                4, CustomItemStack.create(MaterialCompat.safe(XMaterial.PURPLE_STAINED_GLASS_PANE), "&fKnowledge Pane",
                        "&fCurrent Power: Unknown", "&fCurrent Status: NOT CONNECTED"),
                ChestMenuUtils.getEmptyClickHandler());
    }

    public int[] getBorder() {
        return new int[] { 0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53 };
    }

    public int[] getOutputSlots() {
        return new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
                32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44 };
    }

    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public int getEnergyConsumption() {
        return 8;
    }

    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    private void setItemLore(ItemStack item, Location l) {
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains("Location: ")) {
                lore.remove(i);
            }
        }

        lore.add(ChatColor.WHITE + "Location: " + l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY()
                + " " + l.getBlockZ());

        im.setLore(lore);
        item.setItemMeta(im);

    }

    private String locationToString(Location l) {
        return l.getWorld().getName() + ";" + l.getBlockX() + ";" + l.getBlockY() + ";" + l.getBlockZ();
    }

    private static final Location stringToLocation(String locString) {
        String[] locComponents = locString.split(";");
        return new Location(Bukkit.getWorld(locComponents[0]), Double.parseDouble(locComponents[1]),
                Double.parseDouble(locComponents[2]), Double.parseDouble(locComponents[3]));
    }

}

