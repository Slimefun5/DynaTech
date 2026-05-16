package me.profelements.dynatech.items.electric;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.exoticgarden.ExoticGardenRecipeTypes;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.items.autocrafters.SlimefunAutoCrafter;

public class KitchenAutoCrafter extends SlimefunAutoCrafter {
    
    @ParametersAreNonnullByDefault
	public KitchenAutoCrafter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(itemGroup, item, recipeType, recipe, ExoticGardenRecipeTypes.KITCHEN);
	}
}

