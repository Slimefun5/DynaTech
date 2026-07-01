package me.profelements.dynatech.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.slimefun5.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.exoticgarden.ExoticGardenRecipeTypes;
import io.github.thebusybiscuit.exoticgarden.items.CustomFood;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.events.SlimefunItemRegistryFinalizedEvent;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.profelements.dynatech.items.backpacks.PicnicBasket;
import me.profelements.dynatech.items.electric.SeedPlucker;
import me.profelements.dynatech.items.electric.generators.CulinaryGenerator;
import me.profelements.dynatech.items.electric.growthchambers.GrowthChamber;
import me.profelements.dynatech.items.electric.growthchambers.GrowthChamberMK2;
import me.profelements.dynatech.registries.Items;

public class ExoticGardenIntegrationListener implements Listener {

    public ExoticGardenIntegrationListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSlimefunRegistyFinalized(SlimefunItemRegistryFinalizedEvent e) {
        boolean exoticGardenInstalled = Bukkit.getServer().getPluginManager().isPluginEnabled("ExoticGarden");
        SlimefunItem item1 = SlimefunItem.getByItem(Items.FOOD_GENERATOR.stack().item());
        SlimefunItem item2 = SlimefunItem.getByItem(Items.SEED_PLUCKER.stack().item());
        SlimefunItem item3 = SlimefunItem.getByItem(Items.GROWTH_CHAMBER.stack().item());
        SlimefunItem item4 = SlimefunItem.getByItem(Items.GROWTH_CHAMBER_MK2.stack().item());
        /*
         * if (item1 instanceof CulinaryGenerator cg && item2 instanceof SeedPlucker sp
         * && gastronomiconInstalled) {
         * for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
         * if (item.getItem() instanceof FoodItemStack food &&
         * !food.getTexture().equals(HeadTextures.NONE) &&
         * !item.getId().contains("GN_PERFECT")) {
         * cg.registerFuel(food, food.getHunger() * 4);
         * PicnicBasket.registerFood(food, new Pair<>(food.getHunger(), (float)
         * food.getSaturation()));
         * }
         * 
         * if (item.getRecipeType() == GastroRecipeType.HARVEST) {
         * sp.registerRecipe(new MachineRecipe(10, new ItemStack[] {
         * item.getRecipeOutput() }, new ItemStack[] { item.getRecipe()[4] }));
         * }
         * }
         * }
         */
        if (item1 instanceof CulinaryGenerator && item3 instanceof GrowthChamber
                && item4 instanceof GrowthChamberMK2 && item2 instanceof SeedPlucker && exoticGardenInstalled) {
            CulinaryGenerator cg1 = (CulinaryGenerator) item1;
            GrowthChamber gc = (GrowthChamber) item3;
            GrowthChamberMK2 gc2 = (GrowthChamberMK2) item4;
            SeedPlucker sp1 = (SeedPlucker) item2;
            for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
                if (item instanceof CustomFood) {
                    CustomFood cfItem = (CustomFood) item;
                    cg1.registerFuel(cfItem.getItem(), cfItem.getFoodValue() * 4);
                    PicnicBasket.registerFood(cfItem.getItem(), new Pair<>(cfItem.getFoodValue(), 10F));
                }

                if (item.getRecipeType() == ExoticGardenRecipeTypes.HARVEST_BUSH
                        || item.getRecipeType() == ExoticGardenRecipeTypes.HARVEST_TREE) {
                    gc.registerRecipe(new MachineRecipe(30, new ItemStack[] { item.getRecipe()[4] },
                            new ItemStack[] { item.getRecipe()[4], item.getRecipeOutput() }));
                    gc2.registerRecipe(new MachineRecipe(30, new ItemStack[] { item.getRecipe()[4] },
                            new ItemStack[] { item.getRecipe()[4], item.getRecipeOutput() }));
                    sp1.registerRecipe(new MachineRecipe(10, new ItemStack[] { item.getRecipeOutput() },
                            new ItemStack[] { item.getRecipe()[4] }));
                }

                if (item.getId().contains("_ESSENCE")) {
                    SlimefunItem orePlant = SlimefunItem.getById(item.getId().replace("_ESSENCE", "_PLANT"));
                    if (orePlant != null) {
                        gc.registerRecipe(new MachineRecipe(30, new ItemStack[] { orePlant.getItem() },
                                new ItemStack[] { orePlant.getItem(), item.getItem() }));
                        gc2.registerRecipe(new MachineRecipe(30, new ItemStack[] { orePlant.getItem() },
                                new ItemStack[] { orePlant.getItem(), item.getItem(), }));
                    }
                }
            }
        }
    }
}

