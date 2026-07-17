package me.profelements.dynatech;

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.core.guide.wiki.WikiText;
import io.github.thebusybiscuit.slimefun5.core.guide.wiki.WikiTopic;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.items.backpacks.PicnicBasket;
import me.profelements.dynatech.items.misc.DimensionalHomeDimension;
import me.profelements.dynatech.items.tools.ElectricalStimulator;
import me.profelements.dynatech.listeners.BlockBreakBlockListener;
import me.profelements.dynatech.listeners.CoalCokeListener;
import me.profelements.dynatech.listeners.ElectricalStimulatorListener;
import me.profelements.dynatech.listeners.ExoticGardenIntegrationListener;
import me.profelements.dynatech.listeners.InventoryFilterListener;
import me.profelements.dynatech.listeners.PicnicBasketListener;
import me.profelements.dynatech.listeners.RegistryListeners;
import me.profelements.dynatech.listeners.UpgradesListener;
import me.profelements.dynatech.registries.ItemGroups;
import me.profelements.dynatech.registries.Items;
import me.profelements.dynatech.registries.RecipeTypes;
import me.profelements.dynatech.registries.Recipes;
import me.profelements.dynatech.registries.Registries;
import me.profelements.dynatech.setup.DynaTechItemsSetup;
import me.profelements.dynatech.tasks.ItemBandTask;
import me.profelements.dynatech.utils.Liquid;
import me.profelements.dynatech.utils.LiquidRegistry;
import me.profelements.dynatech.utils.RecipeRegistry;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import dev.walshy.sfmetrics.MetricsModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DynaTech extends JavaPlugin implements SlimefunAddon {

    private static DynaTech instance;
    private static boolean exoticGardenInstalled;
    private static boolean infinityExpansionInstalled;
    private static RecipeRegistry rRegistry;
    private static LiquidRegistry lRegistry;

    private int tickInterval;

    @Override
    public void onEnable() {
        setInstance(this);
        rRegistry = RecipeRegistry.init();
        lRegistry = LiquidRegistry.init();
        setExoticGardenInstalled(Bukkit.getPluginManager().isPluginEnabled("ExoticGarden"));
        setInfinityExpansionInstalled(Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion"));

        final int TICK_TIME = Slimefun.getTickerTask().getTickRate();

        saveDefaultConfig();

        
        if (!getConfig().getBoolean("options.disable-dimensionalhome-world")) {
            WorldCreator worldCreator = new WorldCreator("dimensionalhome");
            worldCreator.generator(new DimensionalHomeDimension());
            worldCreator.createWorld();
        }
        DynaTechLiquids.registerLiquids(DynaTech.getLiquidRegistry());

        DynaTechItemsSetup.setup(this);
        new PicnicBasketListener(this, (PicnicBasket) Items.PICNIC_BASKET.stack().getItem());
        new ElectricalStimulatorListener(this, (ElectricalStimulator) Items.ELECTRICAL_STIMULATOR.stack().getItem());
        // InventoryFilterListener's only handler takes EntityPickupItemEvent (MC 1.12+). Guard its
        // construction so the class - and its unloadable event reference - is never touched on 1.8-1.11.
        try {
            Class.forName("org.bukkit.event.entity.EntityPickupItemEvent");
            new InventoryFilterListener(this);
        } catch (ClassNotFoundException ignored) {
            // 1.8-1.11: no EntityPickupItemEvent, inventory-filter pickup blocking is unavailable.
        }
        new UpgradesListener(this);
        new CoalCokeListener(this);
        new BlockBreakBlockListener(this);
        new RegistryListeners(this);
        try {
            Class.forName("io.github.schntgaispock.gastronomicon.api.items.FoodItemStack");
            // new GastronomiconIntegrationListener(this);
        } catch (ClassNotFoundException ex) {

        }

        try {
            Class.forName("io.github.thebusybiscuit.exoticgarden.items.CustomFood");
            new ExoticGardenIntegrationListener(this);
        } catch (ClassNotFoundException ex) {
        }

        // Tasks
        getServer().getScheduler().runTaskTimerAsynchronously(DynaTech.getInstance(), new ItemBandTask(), 0L, 5 * 20L);
        getServer().getScheduler().runTaskTimer(DynaTech.getInstance(), () -> this.tickInterval++, 0, TICK_TIME);

        setupRegistries();

        MetricsModule.setup(this, 31440);

        // Contribute this addon's per-language item translations (languages/<lang>/items.yml).
        Slimefun.getItemTranslationService().registerTranslations(this);

        // Register this addon's own in-game wiki page (core does not auto-generate addon wikis).
        registerWiki();
    }

    private void registerWiki() {
        WikiText wiki = Slimefun.getWikiText();

        // Bucket this addon's items by their ItemGroup dynamically - never hardcode item lists.
        Map<ItemGroup, List<String>> byGroup = new LinkedHashMap<>();

        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            try {
                if (item.getAddon() != this) {
                    continue;
                }
                ItemGroup group = item.getItemGroup();
                if (group == null) {
                    continue;
                }
                byGroup.computeIfAbsent(group, g -> new ArrayList<>()).add(item.getId());

                // Author this item's own wiki page if we have something to say about it.
                List<String> text = itemText(item.getId());
                if (text != null) {
                    wiki.set(item.getId(), text);
                }
            } catch (Exception | LinkageError ignored) {
                // A broken item should not break wiki registration.
            }
        }

        for (Map.Entry<ItemGroup, List<String>> entry : byGroup.entrySet()) {
            try {
                String groupKey = entry.getKey().getKey().getKey();
                String topicId = "addon_dynatech_" + groupKey;

                wiki.registerTopic(new WikiTopic(topicId,
                        categoryTitle(groupKey), categoryIcon(groupKey), categoryTagline(groupKey)));
                wiki.setMechanic(topicId, categoryBlurb(groupKey));
                wiki.setTopicItems(topicId, entry.getValue());
            } catch (Exception | LinkageError ignored) {
                // A broken group should not break wiki registration.
            }
        }
    }

    private static String categoryTitle(String groupKey) {
        switch (groupKey) {
            case "resources": return "DynaTech: Resources";
            case "tools": return "DynaTech: Tools";
            case "machines": return "DynaTech: Machines";
            case "generators": return "DynaTech: Generators";
            case "experimental": return "DynaTech: Experimental";
            case "apiaries": return "DynaTech: Mineralized Apiaries";
            default: return "DynaTech";
        }
    }

    private static XMaterial categoryIcon(String groupKey) {
        switch (groupKey) {
            case "resources": return XMaterial.PUFFERFISH;
            case "tools": return XMaterial.DIAMOND_AXE;
            case "machines": return XMaterial.SEA_LANTERN;
            case "generators": return XMaterial.PRISMARINE_BRICKS;
            case "experimental": return XMaterial.REDSTONE_LAMP;
            case "apiaries": return XMaterial.BEEHIVE;
            default: return XMaterial.CONDUIT;
        }
    }

    private static String categoryTagline(String groupKey) {
        switch (groupKey) {
            case "resources": return "&7Alloys, scrap, gems & living bees";
            case "tools": return "&7Gadgets, gems & portable helpers";
            case "machines": return "&7Automation, growth & wireless tech";
            case "generators": return "&7Trade durability, food & stardust for power";
            case "experimental": return "&7Mills, components & work-in-progress kit";
            case "apiaries": return "&7Bees that breed metal from materials";
            default: return "&7Machines driven by motion";
        }
    }

    private static List<String> categoryBlurb(String groupKey) {
        switch (groupKey) {
            case "resources": return Arrays.asList(
                    "&7The crafting backbone of DynaTech.", "",
                    "&7Smelt &fStainless Steel&7, salvage &fMachine Scrap",
                    "&7from broken tech, and condense it into the",
                    "&fAncient Machine Core &7that every advanced",
                    "&7machine is built around.", "",
                    "&7Capture &6Bees &7with a Scoop and upgrade them",
                    "&7into &6Robotic Bees &7for the apiaries, harvest",
                    "&6Vex Gems &7and &fGhostly Essence &7from mobs, and",
                    "&7geomine &6Stardust &7to fuel the Stardust Reactor.", "",
                    "&7Click an item below for its recipe.");
            case "tools": return Arrays.asList(
                    "&7Handheld gadgets and quality-of-life items.", "",
                    "&7Carry food in a &6Picnic Basket &7that feeds you",
                    "&7automatically, soar with the &6Flight Gem&7, and",
                    "&7teleport to a private &6Dimensional Home&7.", "",
                    "&7Slap an &6Item Band &7onto your gear for permanent",
                    "&7Haste or Health, scoop up bees with the &6Scoop&7,",
                    "&7and link Tesseracts together with the binder.", "",
                    "&7Most powered tools charge in any Slimefun charger.");
            case "machines": return Arrays.asList(
                    "&7The heart of DynaTech automation - all hungry",
                    "&7for energy, so build out your generators first.", "",
                    "&7Mass-grow crops in the four biome &aGrowth",
                    "&7Chambers &7(and their 3x &lMK2 &7tiers), pull seeds",
                    "&7with the &6Seed Plucker&7, and breed materials in",
                    "&7the &6Material Hive&7.", "",
                    "&7Move energy and items across the map without",
                    "&7cables using the &6Tesseract&7, &6Wireless Energy",
                    "&7and &6Wireless Item &7nodes, sprinkle potions, push",
                    "&7mobs with &6Barbed Wire&7, and bend the &6Weather&7.");
            case "generators": return Arrays.asList(
                    "&7Unconventional power sources.", "",
                    "&7Instead of burning fuel, these convert other",
                    "&7resources straight into energy.", "",
                    "&7The &bChipping Generator &7eats tool durability,",
                    "&7the &bCulinary Generator &7burns your hunger, and",
                    "&7the end-game &bStardust Reactor &7devours &6Star",
                    "&7Dust &7for a huge burst of power.", "",
                    "&7Pair them with the motion mills under the",
                    "&7Experimental tab for a full grid.");
            case "experimental": return Arrays.asList(
                    "&7Work-in-progress kit and crafting components.", "",
                    "&7Home of the motion generators that give DynaTech",
                    "&7its name: &bWater Mills&7, &bWind Mills &7and &bDragon",
                    "&7Egg Mills&7, each in a basic and turbine tier that",
                    "&7slowly &cdegrades &7with use.", "",
                    "&7Also holds the &fMachine Cores&7, &fEnergy",
                    "&7Components &7and &fStainless Steel &7parts that feed",
                    "&7crafting, plus utility items like the &6Recipe",
                    "&7Book&7, &6Inventory Filter &7and fluid bottles.", "",
                    "&cExpect rough edges - these are still in testing.");
            case "apiaries": return Arrays.asList(
                    "&7Bees that breed metal instead of honey.", "",
                    "&7Each &6Mineralized Apiary &7is generated for a",
                    "&7specific ingot - both Slimefun and vanilla metals",
                    "&7are supported - and slowly produces that material",
                    "&7using power and bees.", "",
                    "&7Build them around the &6Material Hive &7and feed",
                    "&7them &6Robotic Bees&7. The set of available apiaries",
                    "&7depends on which metals are installed, so this",
                    "&7list is generated fresh on every server.", "",
                    "&cHigh radioactivity - handle with protection.");
            default: return Arrays.asList(
                    "&7Machines driven by motion.", "",
                    "&7Water mills, wind mills and momentum-based",
                    "&7generators, plus advanced processing machines.", "",
                    "&7Click an item below for its recipe.");
        }
    }

    private static List<String> itemText(String id) {
        switch (id) {
            // --- Resources ---
            case "DYNATECH_ANCIENT_MACHINE_CORE": return Arrays.asList(
                    "&7The heart of DynaTech's advanced machinery.",
                    "&7Condensed from Machine Scrap and used in almost",
                    "&7every high-tier machine and wireless node.");
            case "DYNATECH_MACHINE_SCRAP": return Arrays.asList(
                    "&7Salvaged remnants of broken technology.",
                    "&7A base material refined into the Ancient",
                    "&7Machine Core and other components.");
            case "DYNATECH_ADVANCED_MACHINE_SCRAP": return Arrays.asList(
                    "&7A refined grade of Machine Scrap.",
                    "&7Used in the Stardust Reactor, wireless nodes",
                    "&7and other end-game builds.");
            case "DYNATECH_VEX_GEM": return Arrays.asList(
                    "&6A gem dropped by Vexes.",
                    "&7An ingredient in the Mineralized Apiaries and,",
                    "&7with InfinityExpansion, the Vex mob data card.");
            case "STARDUST_METEOR": return Arrays.asList(
                    "&6A GEO resource mined from Mountain and",
                    "&7Badlands biomes with a GEO Miner.",
                    "&7Processed into Star Dust for the Stardust Reactor.");
            case "DYNATECH_STAR_DUST": return Arrays.asList(
                    "&6Refined from Stardust Meteors.",
                    "&7The fuel burned by the &bStardust Reactor &7for a",
                    "&7massive amount of energy.");
            case "DYNATECH_GHOSTLY_ESSENCE": return Arrays.asList(
                    "&fA faint essence harvested from mobs.",
                    "&7A core ingredient in the wireless transfer",
                    "&7nodes and energy network blocks.");
            case "DYNATECH_TESSERACTING_OBJECT": return Arrays.asList(
                    "&6A strange object that shimmers and shifts.",
                    "&7Used in crafting the &6Tesseract &7for wireless",
                    "&7item and energy transfer.");
            case "DYNATECH_BEE": return Arrays.asList(
                    "&6A bee captured with the Scoop.",
                    "&7The starting point of the apiary line - upgrade",
                    "&7it into a Robotic Bee.");
            case "DYNATECH_ROBOTIC_BEE": return Arrays.asList(
                    "&6A mechanically enhanced bee.",
                    "&7Powers the Material Hive and Mineralized",
                    "&7Apiaries. Upgrades further into an Advanced tier.");
            case "DYNATECH_ADVANCED_ROBOTIC_BEE": return Arrays.asList(
                    "&6The top tier of robotic bee.",
                    "&7The most efficient worker for material",
                    "&7production in the hives and apiaries.");

            // --- Tools ---
            case "DYNATECH_PICNIC_BASKET": return Arrays.asList(
                    "&6Stores up to 27 stacks of food.",
                    "&7While in your inventory it automatically feeds",
                    "&7you when you get hungry. Right-click to open.");
            case "DYNATECH_SOUL_BOUND_PICNIC_BASKET": return Arrays.asList(
                    "&6A Picnic Basket that stays with you on death.",
                    "&7Auto-feeds from its 27 stored slots and is",
                    "&dSoulbound&7, so you never drop it.");
            case "DYNATECH_ELECTRICAL_STIMULATOR": return Arrays.asList(
                    "&6Automatically feeds you using stored energy.",
                    "&7Keep it charged and it tops up your hunger so",
                    "&7you never have to stop to eat.");
            case "DYNATECH_ANGEL_GEM": return Arrays.asList(
                    "&6Grants permanent creative-style flight.",
                    "&7Includes adjustable flight-speed settings.",
                    "&7Keep it in your inventory to stay airborne.");
            case "DYNATECH_SCOOP": return Arrays.asList(
                    "&6Used to capture bees into items.",
                    "&7Right-click a bee to scoop it up - the start",
                    "&7of the robotic bee and apiary line.",
                    "&7Charges like any powered tool.");
            case "DYNATECH_DIMENSIONAL_HOME": return Arrays.asList(
                    "&6Teleports you to a private dimension and back.",
                    "&7Each holder gets their own pocket home chunk.",
                    "&7Right-click to travel home.");
            case "DYNATECH_HASTE_ITEM_BAND": return Arrays.asList(
                    "&6Apply to a tool or armor piece in the",
                    "&7Item Band Manager to grant permanent Haste II",
                    "&7while it is worn or held.");
            case "DYNATECH_HEALTH_ITEM_BAND": return Arrays.asList(
                    "&6Apply to a tool or armor piece in the",
                    "&7Item Band Manager to grant permanent Health",
                    "&7Boost II while it is worn or held.");
            case "DYNATECH_TESSERACT_BINDER": return Arrays.asList(
                    "&6Links two Tesseracts together.",
                    "&7Right-click a Tesseract to copy its location,",
                    "&7then crouch + right-click another to bind them.");
            case "DYNATECH_WITHER_SKELETON_GOLEM": return Arrays.asList(
                    "&6A multiblock that spawns a Wither Skeleton.",
                    "&7Assemble the structure to summon the mob.");
            case "DYNATECH_LIQUID_TANK": return Arrays.asList(
                    "&6A portable tank that holds up to 16,000mb.",
                    "&7Right-click to grab a fluid, crouch-click to",
                    "&7place it back down.");

            // --- Machines ---
            case "DYNATECH_KITCHEN_AUTO_CRAFTER": case "DYNATECH_AUTO_KITCHEN": return Arrays.asList(
                    "&6Automatically crafts Kitchen recipes.",
                    "&7Supply the ingredients and power; it cooks",
                    "&7food items continuously. (Requires ExoticGarden.)");
            case "DYNATECH_GROWTH_CHAMBER": case "DYNATECH_OCEAN_GROWTH_CHAMBER":
            case "DYNATECH_NETHER_GROWTH_CHAMBER": case "DYNATECH_END_GROWTH_CHAMBER": return Arrays.asList(
                    "&aAutomatically grows plants from its biome type.",
                    "&7Place seeds or saplings inside and supply power;",
                    "&7it grows, harvests and replants for you.",
                    "&7The MK2 version runs at triple speed.");
            case "DYNATECH_GROWTH_CHAMBER_MARK_2": case "DYNATECH_OCEAN_GROWTH_CHAMBER_MARK_2":
            case "DYNATECH_NETHER_GROWTH_CHAMBER_MARK_2": case "DYNATECH_END_GROWTH_CHAMBER_MARK_2": return Arrays.asList(
                    "&6The upgraded Growth Chamber.",
                    "&73x faster production for a higher energy cost.",
                    "&7Grows the same plants as its base tier.");
            case "DYNATECH_ANTIGRAVITY_BUBBLE": return Arrays.asList(
                    "&6Grants creative flight within a 45-block radius.",
                    "&7Place it, supply power, and everyone inside the",
                    "&7bubble can fly freely.");
            case "DYNATECH_WEATHER_CONTROLLER": return Arrays.asList(
                    "&6Sets the weather when given a key item.",
                    "&7Insert the right item and power it to clear",
                    "&7skies or summon a storm on demand.");
            case "DYNATECH_POTION_SPRINKLER": return Arrays.asList(
                    "&6Applies potion effects to nearby players.",
                    "&7A ranged, multi-target effect dispenser - great",
                    "&7for buffing a whole base at once.");
            case "DYNATECH_BARBED_WIRE": return Arrays.asList(
                    "&6Pushes mobs away within a radius.",
                    "&7A powered defensive block that keeps hostiles",
                    "&7off your base perimeter.");
            case "DYNATECH_MATERIAL_HIVE": return Arrays.asList(
                    "&6Slowly generates materials using power and bees.",
                    "&7The centerpiece of the apiary system - feed it",
                    "&7Robotic Bees to produce resources.",
                    "&4Highly radioactive; handle with protection.");
            case "DYNATECH_WIRELESS_CHARGER": return Arrays.asList(
                    "&6Charges energy items in your inventory.",
                    "&7Stand near it while powered and your gear tops",
                    "&7up without being placed inside.");
            case "DYNATECH_SEED_PLUCKER": return Arrays.asList(
                    "&6Extracts seeds from plant-based items.",
                    "&7Feed it crops and it returns their seeds.");
            case "DYNATECH_BANDAID_MANAGER": return Arrays.asList(
                    "&6Applies Item Bands to your tools and armor.",
                    "&7Insert a band and the target item to fuse the",
                    "&7band's effect onto it.");
            case "DYNATECH_ORECHID": return Arrays.asList(
                    "&6Converts Stone or Netherrack into ores.",
                    "&7Supply power and the base block; it transmutes",
                    "&7them into their respective ores.");
            case "DYNATECH_WIRELESS_ENERGY_BANK": return Arrays.asList(
                    "&6Stores power for a Wireless Energy Point to use.",
                    "&7Charge it from your grid, then draw on it",
                    "&7wirelessly elsewhere.");
            case "DYNATECH_WIRELESS_ENERGY_POINT": return Arrays.asList(
                    "&6Pulls energy wirelessly from a Wireless Energy",
                    "&7Bank. Right-click the bank to link them, then",
                    "&7run cables from the point.");
            case "DYNATECH_WIRELESS_ITEM_INPUT": return Arrays.asList(
                    "&6Sends items wirelessly to a Wireless Item Output.",
                    "&7Costs a little energy per stack moved - no cargo",
                    "&7nodes or pipes between the two points needed.");
            case "DYNATECH_WIRELESS_ITEM_OUTPUT": return Arrays.asList(
                    "&6Receives items from a Wireless Item Input.",
                    "&7Right-click the input to pair them and items",
                    "&7flow across the map.");
            case "DYNATECH_TESSERACT": return Arrays.asList(
                    "&6Transfers items and energy wirelessly - both ways.",
                    "&7Right-click another Tesseract (or use the Binder)",
                    "&7to link a pair into a 2-way portal for goods.");
            case "DYNATECH_EXTERNAL_HEATER": return Arrays.asList(
                    "&6Heats vanilla Furnaces, Blast Furnaces and",
                    "&7Smokers using energy instead of fuel.",
                    "&7Powers nearby cookers for free smelting.");

            // --- Generators ---
            case "DYNATECH_DURABILITY_GENERATOR": return Arrays.asList(
                    "&bExchanges tool durability for power.",
                    "&7Insert damaged tools and it drains their",
                    "&7remaining durability into energy.");
            case "DYNATECH_FOOD_GENERATOR": return Arrays.asList(
                    "&bExchanges hunger for power.",
                    "&7Feed it food items and it burns them into a",
                    "&7steady trickle of energy.");
            case "DYNATECH_STARDUST_GENERATOR": return Arrays.asList(
                    "&bThe end-game DynaTech power source.",
                    "&7Burns &6Star Dust &7for a massive 1024 J/s output.",
                    "&7Built around a Nuclear Reactor and machine scrap.");

            // --- Experimental: motion mills ---
            case "DYNATECH_WATER_MILL": case "DYNATECH_WATER_MILL_2": return Arrays.asList(
                    "&bGenerates energy from flowing water around it.",
                    "&7Surround it with water for output. The Hydro",
                    "&7Turbine tier produces far more.",
                    "&cDegrades over time and must be rebuilt.");
            case "DYNATECH_WIND_MILL": case "DYNATECH_WIND_MILL_2": return Arrays.asList(
                    "&bGenerates energy from wind at altitude.",
                    "&7Output scales with height; the Wind Turbine",
                    "&7tier reaches much higher rates.",
                    "&cDegrades over time and must be rebuilt.");
            case "DYNATECH_EGG_MILL": case "DYNATECH_EGG_MILL_2": return Arrays.asList(
                    "&bGenerates energy from a Dragon Egg's motion.",
                    "&7The Dragon Egg Turbine tier produces more.",
                    "&cDegrades over time and must be rebuilt.");

            // --- Experimental: components & materials ---
            case "DYNATECH_STAINLESS_STEEL_INGOT": return Arrays.asList(
                    "&fA corrosion-resistant alloy.",
                    "&7A staple crafting material for DynaTech",
                    "&7machines, tanks and rotors.");
            case "DYNATECH_STAINLESS_STEEL_ROTOR": return Arrays.asList(
                    "&fA spinning component made of Stainless Steel.",
                    "&7Used in motion-based generators.");
            case "DYNATECH_COAL_COKE": return Arrays.asList(
                    "&fA refined fuel baked in the Coke Oven.",
                    "&7Burns hotter and longer than raw coal.");
            case "DYNATECH_LIVINGROCK": return Arrays.asList(
                    "&6Stone infused with natural life.",
                    "&7Used in the Petal Apothecary and nature recipes.");
            case "DYNATECH_LIVINGWOOD": return Arrays.asList(
                    "&6Wood infused with natural life.",
                    "&7Used in the Petal Apothecary and nature recipes.");
            case "DYNATECH_RECIPE_BOOK": return Arrays.asList(
                    "&6Look up the recipe of any item you hold.",
                    "&7A handy reference tool for DynaTech machines.");
            case "DYNATECH_INVENTORY_FILTER": return Arrays.asList(
                    "&6Filters dropped items off the ground.",
                    "&7Items matching its inventory are blocked from",
                    "&7being picked up. Right-click to configure.");
            case "DYNATECH_PETAL_APOTHECARY": return Arrays.asList(
                    "&dAdd sparks of nature to brew powerful flowers.",
                    "&7Drop reagents into the water-filled apothecary",
                    "&7to craft nature-themed items.");

            default: return null;
        }
    }

    private static void setupRegistries() {
        ItemGroups.init(Registries.ITEM_GROUPS);
        RecipeTypes.init(Registries.RECIPE_TYPES);
        Recipes.init(Registries.RECIPES);
        Registries.ITEMS.freeze();
        Registries.ITEM_GROUPS.freeze();
        Registries.RECIPE_TYPES.freeze();
        Registries.RECIPES.freeze();

    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);

        setInstance(null);
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Slimefun5/DynaTech/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nonnull
    public static DynaTech getInstance() {
        return instance;
    }

    @Nonnull
    public static RecipeRegistry getRecipeRegistry() {
        return RecipeRegistry.getInstance();
    }

    @Nonnull
    public static LiquidRegistry getLiquidRegistry() {
        return LiquidRegistry.getInstance();
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public static boolean isExoticGardenInstalled() {
        return exoticGardenInstalled;
    }

    public static boolean isInfinityExpansionInstalled() {
        return infinityExpansionInstalled;
    }

    public static void setInstance(DynaTech inst) {
        instance = inst;
    }

    public static void setExoticGardenInstalled(boolean isExoticGardenInstalled) {
        exoticGardenInstalled = isExoticGardenInstalled;
    }

    public static void setInfinityExpansionInstalled(boolean isInfinityExpansionInstalled) {
        infinityExpansionInstalled = isInfinityExpansionInstalled;
    }

    @Nullable
    public static BukkitTask runSync(@Nonnull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "Cannot run null");

        if (instance == null || !instance.isEnabled()) {
            return null;
        }

        return instance.getServer().getScheduler().runTask(getInstance(), runnable);
    }

}

