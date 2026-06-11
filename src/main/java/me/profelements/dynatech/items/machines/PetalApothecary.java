package me.profelements.dynatech.items.machines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun5.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockUseHandler;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.profelements.dynatech.registries.RecipeTypes;
import me.profelements.dynatech.registries.Registries;
import me.profelements.dynatech.utils.Recipe;

public class PetalApothecary extends SlimefunItem {

    protected static final HashMap<BlockPosition, List<ItemStack>> RECIPE_ITEMS = new HashMap<>();

    public PetalApothecary(ItemGroup itemGroup, SlimefunItemStack item) {
        super(itemGroup, item);

        addItemHandler(onUse(), new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block arg0, SlimefunItem arg1, Config arg2) {
                tickBlock(arg0);
            }

        });
    }

    private static BlockUseHandler onUse() {
        return new BlockUseHandler() {

            @Override
            public void onRightClick(PlayerRightClickEvent event) {

                if (event.getClickedBlock().get().getBlockData() instanceof Levelled) {
                    Levelled lvl = (Levelled) event.getClickedBlock().get().getBlockData();
                    event.getPlayer().sendMessage("Level of cauldron = " + lvl.getLevel());

                    List<ItemStack> items = RECIPE_ITEMS
                            .getOrDefault(new BlockPosition(event.getClickedBlock().get()), new ArrayList<>());

                    event.getPlayer().sendMessage("entries size: " + RECIPE_ITEMS.size());

                    for (ItemStack item : items) {
                        event.getPlayer().sendMessage(item.hasItemMeta() ? item.getItemMeta().getDisplayName() : item.getType().name());
                    }
                }

            }
        };

    }

    private void tickBlock(Block block) {

        if (!(block.getBlockData() instanceof Levelled)) {
            return;
        }
        Levelled lvl = (Levelled) block.getBlockData();

        int levelAfterRecipeConsume = lvl.getLevel() - 1;

        List<ItemStack> maybeRecipeContents = getMaybeRecipes(block);

        Optional<Recipe> maybeRecipe = Registries.RECIPES.getEntries().stream().filter((recipe) -> {
            boolean sameLength = recipe.getInput().length == maybeRecipeContents.size();
            boolean recipeTypeEqual = recipe.getRecipeType().equals(RecipeTypes.PETAL_APOTHECARY);
            boolean containsItems = Arrays.stream(recipe.getInput()).allMatch((itemStack) -> {
                return maybeRecipeContents.contains(itemStack);
            });

            return sameLength && recipeTypeEqual && containsItems;
        }).findFirst();

        if (maybeRecipe.isPresent()) {
            Recipe recipe = maybeRecipe.get();

            Arrays.stream(recipe.getOutput()).forEach((item) -> {
                block.getWorld().dropItemNaturally(block.getLocation().add(0, 1, 0), item);
            });

            if (levelAfterRecipeConsume >= 0) {
                lvl.setLevel(levelAfterRecipeConsume);
                block.setBlockData(lvl);
            } else {
                block.setType(Material.CAULDRON);
            }

            RECIPE_ITEMS.put(new BlockPosition(block), new ArrayList<>());
        }
    }

    private List<ItemStack> getMaybeRecipes(Block block) {
        BlockPosition pos = new BlockPosition(block);
        Collection<Item> items = block.getWorld().getNearbyEntities(block.getLocation(), 1.d, 1.d, 1.d).stream()
                .filter(e -> e instanceof Item).map(e -> (Item) e).collect(java.util.stream.Collectors.toList());
        List<ItemStack> itemList = RECIPE_ITEMS.getOrDefault(pos, new ArrayList<>());

        Optional<Item> maybeRecipeItem = items.stream().filter((item) -> {
            return Registries.RECIPES.getEntries().stream().anyMatch((recipe) -> {
                boolean recipeTypeEqual = recipe.getRecipeType().equals(RecipeTypes.PETAL_APOTHECARY);
                boolean containsItems = Arrays.stream(recipe.getInput()).anyMatch((itemStack) -> {
                    return itemStack != null && itemStack.isSimilar(item.getItemStack());
                });

                return recipeTypeEqual && containsItems;
            });
        }).findFirst();

        if (maybeRecipeItem.isPresent())

        {
            Item itemToRemove = maybeRecipeItem.get();

            itemList.add(itemToRemove.getItemStack());
            RECIPE_ITEMS.put(pos, itemList);

            itemToRemove.setPickupDelay(10000000);
            itemToRemove.remove();
        }
        return itemList;
    }
}

