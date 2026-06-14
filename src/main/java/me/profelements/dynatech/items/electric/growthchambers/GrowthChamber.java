package me.profelements.dynatech.items.electric.growthchambers;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.profelements.dynatech.items.abstracts.AbstractElectricMachine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class GrowthChamber extends AbstractElectricMachine {

    private static final int[] INPUT_SLOTS = new int[] { 19, 20 };
    private static final int[] OUTPUT_SLOTS = new int[] { 24, 25 };

    private static final int[] INPUT_BORDER_SLOTS = new int[] { 9, 10, 11, 12, 18, 21, 27, 28, 29, 30 };
    private static final int[] OUTPUT_BORDER_SLOTS = new int[] {14, 15, 16, 17, 23, 26, 32, 33, 34, 35 };
    private static final int[] BACKGROUND_SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44 }; 

    private static final ItemStack PROGRESS_ITEM = new ItemStack(MaterialCompat.safe(XMaterial.IRON_HOE));

    private ItemSetting<Boolean> exoticGardenIntegration = new ItemSetting<>(this, "exotic-garden-integration", true);

    public GrowthChamber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemSetting(exoticGardenIntegration);
    }

    @Override
    public void postRegister() {
        registerDefaultRecipes();
    } 


    protected void registerDefaultRecipes() {

        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.COCOA_BEANS)), new ItemStack(MaterialCompat.safe(XMaterial.COCOA_BEANS), 3));
        registerRecipe(15, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.MELON_SEEDS))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.MELON) , 1), new ItemStack(MaterialCompat.safe(XMaterial.MELON_SEEDS), 1)});
        registerRecipe(15, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.PUMPKIN_SEEDS))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.PUMPKIN) , 1), new ItemStack(MaterialCompat.safe(XMaterial.PUMPKIN_SEEDS), 1)});
        registerRecipe(15, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.BEETROOT_SEEDS))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.BEETROOT) , 3), new ItemStack(MaterialCompat.safe(XMaterial.BEETROOT_SEEDS), 2)});
        registerRecipe(12, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.WHEAT_SEEDS))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.WHEAT) , 3), new ItemStack(MaterialCompat.safe(XMaterial.WHEAT_SEEDS), 2)});
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.APPLE)), new ItemStack(MaterialCompat.safe(XMaterial.APPLE), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.BROWN_MUSHROOM)), new ItemStack(MaterialCompat.safe(XMaterial.BROWN_MUSHROOM), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.RED_MUSHROOM)), new ItemStack(MaterialCompat.safe(XMaterial.RED_MUSHROOM), 3));
        registerRecipe(9, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.DEAD_BUSH))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.DEAD_BUSH) , 3), new ItemStack(MaterialCompat.safe(XMaterial.STICK), 2)});
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.SHORT_GRASS)), new ItemStack(MaterialCompat.safe(XMaterial.SHORT_GRASS), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.TALL_GRASS)), new ItemStack(MaterialCompat.safe(XMaterial.TALL_GRASS), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.FERN)), new ItemStack(MaterialCompat.safe(XMaterial.FERN), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.LARGE_FERN)), new ItemStack(MaterialCompat.safe(XMaterial.LARGE_FERN), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.VINE)), new ItemStack(MaterialCompat.safe(XMaterial.VINE), 3));

    // Flowers
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.DANDELION)), new ItemStack(MaterialCompat.safe(XMaterial.DANDELION), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.POPPY)), new ItemStack(MaterialCompat.safe(XMaterial.POPPY), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.BLUE_ORCHID)), new ItemStack(MaterialCompat.safe(XMaterial.BLUE_ORCHID), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.ALLIUM)), new ItemStack(MaterialCompat.safe(XMaterial.ALLIUM), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.AZURE_BLUET)), new ItemStack(MaterialCompat.safe(XMaterial.AZURE_BLUET), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.RED_TULIP)), new ItemStack(MaterialCompat.safe(XMaterial.RED_TULIP), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.ORANGE_TULIP)), new ItemStack(MaterialCompat.safe(XMaterial.ORANGE_TULIP), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.WHITE_TULIP)), new ItemStack(MaterialCompat.safe(XMaterial.WHITE_TULIP), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.PINK_TULIP)), new ItemStack(MaterialCompat.safe(XMaterial.PINK_TULIP), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.OXEYE_DAISY)), new ItemStack(MaterialCompat.safe(XMaterial.OXEYE_DAISY), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.CORNFLOWER)), new ItemStack(MaterialCompat.safe(XMaterial.CORNFLOWER), 3));
        registerRecipe(9, new ItemStack(MaterialCompat.safe(XMaterial.LILY_OF_THE_VALLEY)), new ItemStack(MaterialCompat.safe(XMaterial.LILY_OF_THE_VALLEY), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.WITHER_ROSE)), new ItemStack(MaterialCompat.safe(XMaterial.WITHER_ROSE), 2));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.SUNFLOWER)), new ItemStack(MaterialCompat.safe(XMaterial.SUNFLOWER), 2));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.LILAC)), new ItemStack(MaterialCompat.safe(XMaterial.LILAC), 2));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.ROSE_BUSH)), new ItemStack(MaterialCompat.safe(XMaterial.ROSE_BUSH), 2));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.PEONY)), new ItemStack(MaterialCompat.safe(XMaterial.PEONY), 2));

        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.CARROT)), new ItemStack(MaterialCompat.safe(XMaterial.CARROT), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.POTATO)), new ItemStack(MaterialCompat.safe(XMaterial.POTATO), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.SWEET_BERRIES)), new ItemStack(MaterialCompat.safe(XMaterial.SWEET_BERRIES), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.SUGAR_CANE)), new ItemStack(MaterialCompat.safe(XMaterial.SUGAR_CANE), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.BAMBOO)), new ItemStack(MaterialCompat.safe(XMaterial.BAMBOO), 3));
        registerRecipe(12, new ItemStack(MaterialCompat.safe(XMaterial.CACTUS)), new ItemStack(MaterialCompat.safe(XMaterial.CACTUS), 3));

        registerRecipe(30, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.OAK_SAPLING))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.OAK_SAPLING) , 3), new ItemStack(MaterialCompat.safe(XMaterial.OAK_LOG), 6)});
        registerRecipe(30, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.BIRCH_SAPLING))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.BIRCH_SAPLING) , 3), new ItemStack(MaterialCompat.safe(XMaterial.BIRCH_LOG), 6)});
        registerRecipe(30, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.SPRUCE_SAPLING))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.SPRUCE_SAPLING) , 3), new ItemStack(MaterialCompat.safe(XMaterial.SPRUCE_LOG), 6)});
        registerRecipe(30, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.DARK_OAK_SAPLING))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.DARK_OAK_SAPLING) , 3), new ItemStack(MaterialCompat.safe(XMaterial.DARK_OAK_LOG), 6)});
        registerRecipe(30, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.JUNGLE_SAPLING))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.JUNGLE_SAPLING), 3), new ItemStack(MaterialCompat.safe(XMaterial.JUNGLE_LOG), 6)});
        registerRecipe(30, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.ACACIA_SAPLING))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.ACACIA_SAPLING), 3), new ItemStack(MaterialCompat.safe(XMaterial.ACACIA_LOG), 6)});

    }
    

    @Override
	public List<ItemStack> getDisplayRecipes() {
		List<ItemStack> display = new ArrayList<>(); 
        for (MachineRecipe recipe : recipes) {
           display.add(recipe.getInput()[0]);
           if (recipe.getOutput().length > 1) {
            display.add(recipe.getOutput()[1]);
           } else {
            display.add(recipe.getOutput()[0]);
           }
        }
        return display;
	}

	@Override
	protected void setupMenu(BlockMenuPreset preset) {
		for (int slot : BACKGROUND_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int slot : INPUT_BORDER_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int slot : OUTPUT_BORDER_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(getProgressSlot(), CustomItemStack.create(MaterialCompat.safe(XMaterial.BLACK_STAINED_GLASS_PANE), " "), ChestMenuUtils.getEmptyClickHandler());
        
        for (int slot : getOutputSlots()) {
            preset.addMenuClickHandler(slot,new ChestMenu.AdvancedMenuClickHandler() {
                @Override
                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return MaterialCompat.isAir(cursor.getType());
                }

                @Override
                public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
                    return false;
                }
            });
        }
	}

	@Override
	protected int[] getInputSlots() {
		return INPUT_SLOTS;
	}

	@Override
	protected int[] getOutputSlots() {
		return OUTPUT_SLOTS;
	}

	@Override
	protected ItemStack getProgressBar() {
		return PROGRESS_ITEM;
	}

}

