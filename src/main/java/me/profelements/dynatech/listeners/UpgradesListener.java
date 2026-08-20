package me.profelements.dynatech.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.events.AsyncMachineOperationFinishEvent;
import io.github.thebusybiscuit.slimefun5.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.items.tools.AutoOutputUpgrade;
import me.profelements.dynatech.registries.Items;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class UpgradesListener implements Listener {

    public UpgradesListener(DynaTech plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMachineFinish(AsyncMachineOperationFinishEvent e) {
        if (!(e.getOperation() instanceof CraftingOperation)) {
            return;
        }

        checkInputUpgrade(e);

        Location l = e.getPosition().toLocation();
        String upgrades = BlockStorage.getLocationInfo(l, "upgrades");

        if (upgrades == null) {
            return;
        }

        int upgradeIdx = upgrades.indexOf("{id:auto_output");
        if (upgradeIdx == -1) {
            return;
        }

        int upgradeIdx2 = upgrades.indexOf("}", upgradeIdx);

        String upgradeString = upgrades.substring(upgradeIdx, upgradeIdx2 + 1);

        if (upgrades != null && upgrades.contains("id:auto_output")) {
            int index = upgradeString.indexOf("face:");
            int index2 = upgradeString.indexOf("}");
            BlockFace face = AutoOutputUpgrade.stringToBlockFace(upgradeString.substring(index, index2));
            // DynaTech.getInstance().getLogger().info(face.toString());
            if (e.getProcessor().getOwner() instanceof AContainer
                    && e.getOperation() instanceof CraftingOperation && ((CraftingOperation) e.getOperation()).isFinished()) {
                AContainer cont = (AContainer) e.getProcessor().getOwner();
                CraftingOperation op = (CraftingOperation) e.getOperation();
                int[] outputSlots = cont.getOutputSlots();
                ItemStack[] outputItems = op.getResults();

                if (l.getBlock().getRelative(face).getType().equals(MaterialCompat.safe(XMaterial.CHEST))) {
                    DynaTech.runSync(() -> depositOutputIntoChest(l, face, outputSlots, outputItems));
                }
            }
        }
    }

    /**
     * @implNote Fires on the async machine ticker thread, so the whole move runs in ONE main-thread
     *           task: a clone is added to the chest first ({@code addItem} mutates the shared recipe
     *           stacks, so never pass those), then only the accepted amount is consumed. The old flow
     *           consumed here and deposited a tick later, letting a player who pulled the output in
     *           between get it twice while a full chest ate it entirely.
     */
    private static void depositOutputIntoChest(Location l, BlockFace face, int[] outputSlots, ItemStack[] outputItems) {
        BlockState state = l.getBlock().getRelative(face).getState();
        if (!(state instanceof Chest)) {
            return;
        }

        BlockMenu menu = BlockStorage.getInventory(l);
        if (menu == null) {
            return;
        }

        Chest chest = (Chest) state;
        Inventory inv = chest.getBlockInventory();
        boolean moved = false;

        for (int slot : outputSlots) {
            ItemStack item = menu.getItemInSlot(slot);
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            boolean matchesResult = false;
            for (ItemStack outputItem : outputItems) {
                if (SlimefunUtils.isItemSimilar(item, outputItem, true)) {
                    matchesResult = true;
                    break;
                }
            }
            if (!matchesResult) {
                continue;
            }

            int available = item.getAmount();
            int leftover = 0;
            for (ItemStack rest : inv.addItem(item.clone()).values()) {
                leftover += rest.getAmount();
            }

            int accepted = available - leftover;
            if (accepted > 0) {
                menu.consumeItem(slot, accepted);
                moved = true;
            }
        }

        if (moved) {
            chest.update(true, false);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Location l = e.getBlock().getLocation();
        String upgrades = BlockStorage.getLocationInfo(l, "upgrades");

        if (upgrades != null && upgrades.contains("auto_output")) {
            l.getWorld().dropItemNaturally(l, Items.AUTO_OUTPUT_UPGRADE.stack().item());
        }

        if (upgrades != null && upgrades.contains("auto_input")) {
            l.getWorld().dropItemNaturally(l, Items.AUTO_INPUT_UPGRADE.stack().item());
        }
    }

    private static void checkInputUpgrade(AsyncMachineOperationFinishEvent e) {
        Location l = e.getPosition().toLocation();
        String upgrades = BlockStorage.getLocationInfo(l, "upgrades");

        if (upgrades == null) {
            return;
        }

        int upgradeIdx = upgrades.indexOf("{id:auto_input");
        if (upgradeIdx == -1) {
            return;
        }

        int upgradeIdx2 = upgrades.indexOf("}", upgradeIdx);

        String upgradeString = upgrades.substring(upgradeIdx, upgradeIdx2 + 1);

        if (upgradeString.contains("id:auto_input")) {
            int index = upgradeString.indexOf("face:");
            int index2 = upgradeString.indexOf("}");
            BlockFace face = AutoOutputUpgrade.stringToBlockFace(upgradeString.substring(index, index2));
            if (face == BlockFace.SELF) {
                return;
            }

            DynaTech.runSync(() -> {
                BlockState state = l.getBlock().getRelative(face).getState();
                if (state instanceof Chest && e.getProcessor().getOwner() instanceof AContainer) {
                    Chest chest = (Chest) state;
                    AContainer acont = (AContainer) e.getProcessor().getOwner();
                    BlockMenu inv = BlockStorage.getInventory(l);
                    int[] slots = acont.getInputSlots();
                    for (int slot : slots) {
                        Inventory chsInv = chest.getBlockInventory();
                        ItemStack inputStack = inv.getItemInSlot(slot);
                        for (ItemStack stack : chsInv.getContents()) {
                            if (inputStack == null && stack != null
                                    || inputStack != null && stack != null && stack.isSimilar(inputStack)) {
                                int chsAmount = stack.getAmount();

                                if (inputStack == null) {

                                    inv.pushItem(stack, acont.getInputSlots());
                                    chsInv.remove(stack);
                                } else {
                                    if (inputStack.getAmount() == inputStack.getMaxStackSize()) {
                                        return;
                                    } else {
                                        int diff = inputStack.getMaxStackSize() - inputStack.getAmount();
                                        if (diff >= chsAmount) {

                                            inputStack.setAmount(inputStack.getAmount() + chsAmount);
                                            chsInv.remove(stack);
                                        } else {

                                            inputStack.setAmount(inputStack.getAmount() + diff);
                                            stack.setAmount(chsAmount - diff);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            });
        }
    }
}

