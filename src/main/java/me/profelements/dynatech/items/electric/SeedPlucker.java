package me.profelements.dynatech.items.electric;

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

public class SeedPlucker extends AbstractElectricMachine {
        
    private static final int[] INPUT_SLOTS = new int[] { 19, 20 };
    private static final int[] OUTPUT_SLOTS = new int[] { 24, 25 };

    private static final int[] INPUT_BORDER_SLOTS = new int[] { 9, 10, 11, 12, 18, 21, 27, 28, 29, 30 };
    private static final int[] OUTPUT_BORDER_SLOTS = new int[] {14, 15, 16, 17, 23, 26, 32, 33, 34, 35 };
    private static final int[] BACKGROUND_SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44 }; 

    private static final ItemStack PROGRESS_ITEM = new ItemStack(MaterialCompat.safe(XMaterial.IRON_HOE));

    private final ItemSetting<Boolean> exoticGardenIntegration = new ItemSetting<>(this, "exotic-garden-integration", true);

    public SeedPlucker(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemSetting(exoticGardenIntegration);
    }

    public void registerDefaultRecipes() {
        recipes.add(new MachineRecipe(10, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.CHORUS_FRUIT), 4)},  new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.CHORUS_FLOWER))}));
        recipes.add(new MachineRecipe(10, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.WHEAT))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.WHEAT_SEEDS))}));
        recipes.add(new MachineRecipe(10, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.BEETROOT))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.BEETROOT_SEEDS))}));
        recipes.add(new MachineRecipe(10, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.PUMPKIN))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.PUMPKIN_SEEDS))}));
        recipes.add(new MachineRecipe(10, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.MELON_SLICE))}, new ItemStack[] {new ItemStack(MaterialCompat.safe(XMaterial.MELON_SEEDS))})); 
    }
    
    @Override
	protected ItemStack getProgressBar() {
		return PROGRESS_ITEM;
	}

    @Override
    public void postRegister() {
        super.postRegister();
        registerDefaultRecipes();
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
	public List<ItemStack> getDisplayRecipes() {
		List<ItemStack> display = new ArrayList<>(); 
        
        

        for (MachineRecipe recipe : recipes) {
                
            if (recipe.getInput().length != 1) {
                break;
            }
            
            display.add(recipe.getInput()[0]);
            display.add(recipe.getOutput()[0]); 
        }
        
        return display;
	}

           
}

