package me.profelements.dynatech.items.backpacks;

import io.github.thebusybiscuit.slimefun5.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.items.backpacks.SlimefunBackpack;
import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class PicnicBasket extends SlimefunBackpack {
        
    protected static Map<ItemStack, Pair<Integer, Float>> foods = new HashMap<>();

    private final List<Material> defaultBlacklist = new ArrayList<>();

    private final ItemSetting<List<String>> blacklistedMaterials = new ItemSetting<>(this, "blacklisted-materials", toStringList(getDefaultBlacklist()));

    public PicnicBasket(int size, ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(size, itemGroup, item, recipeType, recipe);

        /*Maybe use Material.getMaterial() and send a set of strings*/

        addItemSetting(blacklistedMaterials);
    }


    @Override
    public void postRegister() {
        registerDefaultFoods();
    }


    @Override
    public boolean isItemAllowed(@Nonnull ItemStack item, @Nullable SlimefunItem itemAsSlimefunItem) {
        for (ItemStack stack : getFoods().keySet()) {
                if (SlimefunUtils.isItemSimilar(stack, item, false, false)) {
                    return true; 
                }
            }
        return false;
    }

    private List<Material> getDefaultBlacklist() {
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.PUFFERFISH));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.POISONOUS_POTATO));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.SPIDER_EYE));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.CHORUS_FRUIT));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.ENCHANTED_GOLDEN_APPLE));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.GOLDEN_APPLE));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.ROTTEN_FLESH));

        //Returns Stuff, maybe will figure this out later.
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.SUSPICIOUS_STEW));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.MUSHROOM_STEW));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.RABBIT_STEW));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.BEETROOT_SOUP));
        defaultBlacklist.add(MaterialCompat.safe(XMaterial.HONEY_BOTTLE));

        return defaultBlacklist;
    }

    private List<String> toStringList(List<Material> mats) {
        List<String> materials = new ArrayList<>();

        for (Material mat : mats) {
            materials.add(mat.toString());
        }

        return materials;
    }

    @Nonnull
    public static Map<ItemStack, Pair<Integer, Float>> getFoods() {
        return foods;
    }
 
    private static void registerDefaultFoods() {
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.APPLE)), new Pair<>(4, 3F)); 
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.MELON_SLICE)), new Pair<>(2, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.SWEET_BERRIES)), new Pair<>(2, 1F));

        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.CARROT)), new Pair<>(3, 3F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.GOLDEN_CARROT)), new Pair<>(6, 15F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.POTATO)), new Pair<>(1, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.BAKED_POTATO)), new Pair<>(5, 6F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.BEETROOT)), new Pair<>(1, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.DRIED_KELP)), new Pair<>(1, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.BEEF)), new Pair<>(3, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_BEEF)), new Pair<>(8, 13F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.PORKCHOP)), new Pair<>(3, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_PORKCHOP)), new Pair<>(8, 13F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.MUTTON)), new Pair<>(2, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_MUTTON)), new Pair<>(6, 9F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.CHICKEN)), new Pair<>(1, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_CHICKEN)), new Pair<>(6, 7F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.RABBIT)), new Pair<>(3, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_RABBIT)), new Pair<>(5, 6F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COD)), new Pair<>(2, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_COD)), new Pair<>(5, 6F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.SALMON)), new Pair<>(2, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKED_SALMON)), new Pair<>(6, 9F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.TROPICAL_FISH)), new Pair<>(1, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.BREAD)), new Pair<>(5, 6F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.COOKIE)), new Pair<>(2, 1F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.CAKE)), new Pair<>(14, 14F));
        registerFood(new ItemStack(MaterialCompat.safe(XMaterial.PUMPKIN_PIE)), new Pair<>(8, 5F));
    }
        

    public static void registerFood(ItemStack item, Pair<Integer, Float> pair) {
        getFoods().put(item, pair);
    }


	
}

