package me.profelements.dynatech.items.tools;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.items.backpacks.SlimefunBackpack;
import org.bukkit.inventory.ItemStack;

public class InventoryFilter extends SlimefunBackpack {

    public InventoryFilter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(9, itemGroup, item, recipeType, recipe);
    }
    
}

