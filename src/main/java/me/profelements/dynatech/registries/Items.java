package me.profelements.dynatech.registries;

import me.profelements.dynatech.utils.HeadUtils;
import me.profelements.dynatech.utils.ItemWrapper;

import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.utils.LoreBuilder;
import io.github.thebusybiscuit.slimefun5.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun5.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun5.core.attributes.Radioactivity;
import dev.j3fftw.extrautils.utils.LoreBuilderDynamic;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class Items {

    private static final String ID_LORE = "&7ID: <ID>";
    private static final String RIGHT_CLICK_LORE = "&eRight Click &7to open.";
    private static final String THREE_X_PROD_LORE = "&c3x production.";

    public static final ItemWrapper WOOD_MACHINE_CORE = ItemWrapper.create(Keys.WOOD_MACHINE_CORE,
            new SlimefunItemStack(
                    Items.Keys.WOOD_MACHINE_CORE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.OAK_WOOD)));

    public static final ItemWrapper STONE_MACHINE_CORE = ItemWrapper.create(Keys.STONE_MACHINE_CORE,
            new SlimefunItemStack(
                    Items.Keys.STONE_MACHINE_CORE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.SMOOTH_STONE)));

    public static final ItemWrapper IRON_MACHINE_CORE = ItemWrapper.create(Keys.IRON_MACHINE_CORE,
            new SlimefunItemStack(
                    Items.Keys.IRON_MACHINE_CORE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.GRAY_CONCRETE)));

    public static final ItemWrapper DIAMOND_MACHINE_CORE = ItemWrapper.create(Keys.DIAMOND_MACHINE_CORE,
            new SlimefunItemStack(
                    Items.Keys.DIAMOND_MACHINE_CORE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LIGHT_BLUE_CONCRETE)));

    public static final ItemWrapper ENCHANTED_MACHINE_CORE = ItemWrapper.create(Keys.ENCHANTED_MACHINE_CORE,
            new SlimefunItemStack(
                    Items.Keys.ENCHANTED_MACHINE_CORE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.RESPAWN_ANCHOR)));

    public static final ItemWrapper ANCIENT_MACHINE_CORE = ItemWrapper.create(Keys.ANCIENT_MACHINE_CORE,
            new SlimefunItemStack(
                    Keys.ANCIENT_MACHINE_CORE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LAPIS_BLOCK)));

    public static final ItemWrapper ENERGY_STORAGE_COMPONENT = ItemWrapper.create(Keys.ENERGY_STORAGE_COMPONENT,
            new SlimefunItemStack(
                    Items.Keys.ENERGY_STORAGE_COMPONENT.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.REDSTONE_LAMP)));

    public static final ItemWrapper ENERGY_INPUT_COMPONENT = ItemWrapper.create(Keys.ENERGY_INPUT_COMPONENT,
            new SlimefunItemStack(
                    Items.Keys.ENERGY_INPUT_COMPONENT.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.COMPARATOR)));

    public static final ItemWrapper ENERGY_OUTPUT_COMPONENT = ItemWrapper.create(Keys.ENERGY_OUTPUT_COMPONENT,
            new SlimefunItemStack(
                    Items.Keys.ENERGY_OUTPUT_COMPONENT.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.REPEATER)));

    public static final ItemWrapper DEGRADED_WATER_MILL = ItemWrapper.create(Keys.DEGRADED_WATER_MILL,
            new SlimefunItemStack(
                    Items.Keys.DEGRADED_WATER_MILL.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.COBBLESTONE_WALL)));

    public static final ItemWrapper DEGRADED_WATER_MILL_2 = ItemWrapper.create(Keys.DEGRADED_WATER_MILL_2,
            new SlimefunItemStack(
                    Items.Keys.DEGRADED_WATER_MILL_2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.PRISMARINE_WALL)));

    public static final ItemWrapper DEGRADED_WIND_MILL = ItemWrapper.create(Keys.DEGRADED_WIND_MILL,
            new SlimefunItemStack(
                    Items.Keys.DEGRADED_WIND_MILL.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LIGHT_GRAY_GLAZED_TERRACOTTA)));

    public static final ItemWrapper DEGRADED_WIND_MILL_2 = ItemWrapper.create(Keys.DEGRADED_WIND_MILL_2,
            new SlimefunItemStack(
                    Items.Keys.DEGRADED_WIND_MILL_2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.GRAY_GLAZED_TERRACOTTA)));

    public static final ItemWrapper DEGRADED_EGG_MILL = ItemWrapper.create(Keys.DEGRADED_WIND_MILL,
            new SlimefunItemStack(
                    Items.Keys.DEGRADED_EGG_MILL.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.CRYING_OBSIDIAN)));

    public static final ItemWrapper DEGRADED_EGG_MILL_2 = ItemWrapper.create(Keys.DEGRADED_WIND_MILL_2,
            new SlimefunItemStack(
                    Items.Keys.DEGRADED_EGG_MILL_2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.RESPAWN_ANCHOR)));

    public static final ItemWrapper WATER_MILL = ItemWrapper.create(Keys.WATER_MILL,
            new SlimefunItemStack(
                    Items.Keys.WATER_MILL.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.COBBLESTONE_WALL)));

    public static final ItemWrapper WATER_MILL_2 = ItemWrapper.create(Keys.WATER_MILL_2,
            new SlimefunItemStack(
                    Items.Keys.WATER_MILL_2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.PRISMARINE_WALL)));

    public static final ItemWrapper WIND_MILL = ItemWrapper.create(Keys.WIND_MILL,
            new SlimefunItemStack(
                    Items.Keys.WIND_MILL.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LIGHT_GRAY_GLAZED_TERRACOTTA)));

    public static final ItemWrapper WIND_MILL_2 = ItemWrapper.create(Keys.WIND_MILL_2,
            new SlimefunItemStack(
                    Items.Keys.WIND_MILL_2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.GRAY_GLAZED_TERRACOTTA)));

    public static final ItemWrapper EGG_MILL = ItemWrapper.create(Keys.EGG_MILL,
            new SlimefunItemStack(
                    Items.Keys.EGG_MILL.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.CRYING_OBSIDIAN)));

    public static final ItemWrapper EGG_MILL_2 = ItemWrapper.create(Keys.EGG_MILL_2,
            new SlimefunItemStack(
                    Items.Keys.EGG_MILL_2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.RESPAWN_ANCHOR)));

    public static final ItemWrapper DURABILITY_GENERATOR = ItemWrapper.create(Keys.DURABILITY_GENERATOR,
            new SlimefunItemStack(
                    Items.Keys.DURABILITY_GENERATOR.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.SPRUCE_WOOD)));

    public static final ItemWrapper FOOD_GENERATOR = ItemWrapper.create(Keys.FOOD_GENERATOR,
            new SlimefunItemStack(
                    Items.Keys.FOOD_GENERATOR.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BLAST_FURNACE)));

    public static final ItemWrapper STARDUST_GENERATOR = ItemWrapper.create(Keys.STARDUST_GENERATOR,
            new SlimefunItemStack(
                    Items.Keys.STARDUST_GENERATOR.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.IRON_BLOCK)));


    public static final ItemWrapper STAINLESS_STEEL_INGOT = ItemWrapper.create(Keys.STAINLESS_STEEL_INGOT,
            new SlimefunItemStack(
                    Keys.STAINLESS_STEEL_INGOT.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.IRON_INGOT)));

    public static final ItemWrapper STAINLESS_STEEL_ROTOR = ItemWrapper.create(Keys.STAINLESS_STEEL_ROTOR,
            new SlimefunItemStack(
                    Keys.STAINLESS_STEEL_ROTOR.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.IRON_BLOCK)));

    public static final ItemWrapper COAL_COKE = ItemWrapper.create(Keys.COAL_COKE,
            new SlimefunItemStack(
                    Keys.COAL_COKE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.COAL)));

    public static final ItemWrapper BEE = ItemWrapper.create(Keys.BEE, new SlimefunItemStack(
            Keys.BEE.asSlimefunId(),
            HeadUtils.fromHashCode("12724a9a4cdd68ba49415560e5be40b4a1c47cb5be1d66aedb52a30e62ef2d47")));

    public static final ItemWrapper ROBOTIC_BEE = ItemWrapper.create(Keys.ROBOTIC_BEE, new SlimefunItemStack(
            Keys.ROBOTIC_BEE.asSlimefunId(),
            HeadUtils.fromHashCode("16f728c89904b2cb57f853d31d0e2061f52917981fedccb1e949528e08eb4140")));

    public static final ItemWrapper ADVANCED_ROBOTIC_BEE = ItemWrapper.create(Keys.ADVANCED_ROBOTIC_BEE,
            new SlimefunItemStack(
                    Keys.ADVANCED_ROBOTIC_BEE.asSlimefunId(),
                    HeadUtils.fromHashCode("c1c96e8cf83cbade55ffa667197ea6990290e5c7dc679104332caead97eef09")));

    public static final ItemWrapper VEX_GEM = ItemWrapper.create(Keys.VEX_GEM, new SlimefunItemStack(
            Keys.VEX_GEM.asSlimefunId(),
            HeadUtils.fromHashCode("b91aeca7c17e66d867231b36d96e83c1ede75eaf67ccf3a88dca15d4114ae167")));

    public static final ItemWrapper MACHINE_SCRAP = ItemWrapper.create(Keys.MACHINE_SCRAP, new SlimefunItemStack(
            Keys.MACHINE_SCRAP.asSlimefunId(),
            HeadUtils.fromHashCode("13ea401c7e02d13cea1de6835ee9f5c47757d399dae5c2b9c3efde6ae63ea4a2")));

    public static final ItemWrapper ADVANCED_MACHINE_SCRAP = ItemWrapper.create(Keys.ADVANCED_MACHINE_SCRAP,
            new SlimefunItemStack(
                    Keys.ADVANCED_MACHINE_SCRAP.asSlimefunId(),
                    HeadUtils.fromHashCode("4b57a4c68d1d2c5de978ea6de4db91ef387ca6c37966bb8e7c8826f937e6c3")));

    public static final ItemWrapper STAR_DUST = ItemWrapper.create(Keys.STAR_DUST,
            new SlimefunItemStack(
                    Keys.STAR_DUST.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.NETHER_STAR)));

    public static final ItemWrapper GHOSTLY_ESSENCE = ItemWrapper.create(Keys.GHOSTLY_ESSENCE,
            new SlimefunItemStack(
                    Keys.GHOSTLY_ESSENCE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.WHITE_DYE)));

    public static final ItemWrapper TESSERACTING_OBJ = ItemWrapper.create(Keys.TESSERACTING_OBJ,
            new SlimefunItemStack(
                    Keys.TESSERACTING_OBJ.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.MUSHROOM_STEM)));

    public static final ItemWrapper LIVINGROCK = ItemWrapper.create(Keys.LIVINGROCK,
            new SlimefunItemStack(
                    Keys.LIVINGROCK.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.STONE_BRICKS)));

    public static final ItemWrapper LIVINGWOOD = ItemWrapper.create(Keys.LIVINGWOOD,
            new SlimefunItemStack(
                    Keys.LIVINGWOOD.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.OAK_WOOD)));

    public static final ItemWrapper ELECTRICAL_STIMULATOR = ItemWrapper.create(Keys.ELECTRICAL_STIMULATOR,
            new SlimefunItemStack(
                    Keys.ELECTRICAL_STIMULATOR.asSlimefunId(),
                    HeadUtils.fromHashCode("82a319cf66a4de12e3330e8bc4c82c985ccc3cb2230868c336a88fc4a22082a")));

    public static final ItemWrapper INVENTORY_FILTER = ItemWrapper.create(Keys.INVENTORY_FILTER,
            new SlimefunItemStack(
                    Keys.INVENTORY_FILTER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.IRON_BARS)));;

    public static final ItemWrapper ANGEL_GEM = ItemWrapper.create(Keys.ANGEL_GEM,
            new SlimefunItemStack(
                    Keys.ANGEL_GEM.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.NETHERITE_BLOCK)));

    public static final ItemWrapper RECIPE_BOOK = ItemWrapper.create(Keys.RECIPE_BOOK,
            new SlimefunItemStack(
                    Keys.RECIPE_BOOK.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BOOK)));

    public static final ItemWrapper AUTO_INPUT_UPGRADE = ItemWrapper.create(Keys.AUTO_INPUT_UPGRADE,
            new SlimefunItemStack(
                    Keys.AUTO_INPUT_UPGRADE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BLUE_STAINED_GLASS_PANE)));

    public static final ItemWrapper AUTO_OUTPUT_UPGRADE = ItemWrapper.create(Keys.AUTO_OUTPUT_UPGRADE,
            new SlimefunItemStack(
                    Keys.AUTO_OUTPUT_UPGRADE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.RED_STAINED_GLASS_PANE)));

    public static final ItemWrapper FLUID_TANK = ItemWrapper.create(Keys.FLUID_TANK,
            new SlimefunItemStack(
                    Keys.FLUID_TANK.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BUCKET)));

    public static final ItemWrapper LIQUID_TANK = ItemWrapper.create(Keys.LIQUID_TANK,
            new SlimefunItemStack(
                    Keys.LIQUID_TANK.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BUCKET)));

    public static final ItemWrapper PICNIC_BASKET = ItemWrapper.create(Keys.PICNIC_BASKET,
            new SlimefunItemStack(
                    Keys.PICNIC_BASKET.asSlimefunId(),
                    HeadUtils.fromHashCode("7a6bf916e28ccb80b4ebfacf98686ad6af7c4fb257e57a8cb78c71d19dccb2")));

    public static final ItemWrapper SOUL_BOUND_PICNIC_BASKET = ItemWrapper.create(Keys.SOUL_BOUND_PICNIC_BASKET,
            new SlimefunItemStack(
                    Keys.SOUL_BOUND_PICNIC_BASKET.asSlimefunId(),
                    HeadUtils.fromHashCode("7a6bf916e28ccb80b4ebfacf98686ad6af7c4fb257e57a8cb78c71d19dccb2")));

    public static final ItemWrapper SCOOP = ItemWrapper.create(Keys.SCOOP,
            new SlimefunItemStack(
                    Keys.SCOOP.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.IRON_SHOVEL)));

    public static final ItemWrapper DIMENSIONAL_HOME = ItemWrapper.create(Keys.DIMENSIONAL_HOME, new SlimefunItemStack(
            Keys.DIMENSIONAL_HOME.asSlimefunId(),
            HeadUtils.fromHashCode("eb18cf9e1bf7ec57304ae92f2b00d91643cf0b65067dead34fb48baf18e3c385")));

    public static final ItemWrapper ITEM_BAND_HASTE = ItemWrapper.create(Keys.ITEM_BAND_HASTE, new SlimefunItemStack(
            Keys.ITEM_BAND_HASTE.asSlimefunId(),
            HeadUtils.fromHashCode("4f01ec6331a3bc30a8204ec56398d08ca38788556bca9b81d776f6238d567367")));

    public static final ItemWrapper ITEM_BAND_HEALTH = ItemWrapper.create(Keys.ITEM_BAND_HEALTH, new SlimefunItemStack(
            Keys.ITEM_BAND_HEALTH.asSlimefunId(),
            HeadUtils.fromHashCode("f1e2428cb359988f4c4ff0e61de21385c62269de19a69762d773223b75dd1666")));

    public static final ItemWrapper TESSERACT_BINDER = ItemWrapper.create(Keys.TESSERACT_BINDER,
            new SlimefunItemStack(
                    Keys.TESSERACT_BINDER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.NETHERITE_HOE)));

    public static final ItemWrapper WITHER_SKELETON_GOLEM = ItemWrapper.create(Keys.WITHER_SKELETON_GOLEM,
            new SlimefunItemStack(
                    Keys.WITHER_SKELETON_GOLEM.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.WITHER_SKELETON_SKULL)));

    public static final ItemWrapper COAL_COKE_OVEN = ItemWrapper.create(Keys.COAL_COKE_OVEN,
            new SlimefunItemStack(
                    Keys.COAL_COKE_OVEN.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.SMOKER)));

    public static final ItemWrapper AUTO_KITCHEN = ItemWrapper.create(Keys.AUTO_KITCHEN,
            new SlimefunItemStack(
                    Keys.AUTO_KITCHEN.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.SMOKER)));

    public static final ItemWrapper KITCHEN_AUTO_CRAFTER = ItemWrapper.create(Keys.KITCHEN_AUTO_CRAFTER,
            new SlimefunItemStack(
                    Keys.KITCHEN_AUTO_CRAFTER.asSlimefunId(),
                    HeadUtils.fromHashCode("c5c5b24cd5efa07d31beea655d7ff972e6f47cdb898be4404363deeba43ba5d")));

    public static final ItemWrapper ANTIGRAVITY_BUBBLE = ItemWrapper.create(Keys.ANTIGRAVITY_BUBBLE,
            new SlimefunItemStack(
                    Keys.ANTIGRAVITY_BUBBLE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.OBSIDIAN)));

    public static final ItemWrapper WEATHER_CONTROLLER = ItemWrapper.create(Keys.WEATHER_CONTROLLER,
            new SlimefunItemStack(
                    Keys.WEATHER_CONTROLLER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BLUE_STAINED_GLASS)));

    public static final ItemWrapper POTION_SPRINKLER = ItemWrapper.create(Keys.POTION_SPRINKLER, new SlimefunItemStack(
            Keys.POTION_SPRINKLER.asSlimefunId(),
            HeadUtils.fromHashCode("8d302104180cb79d5f4cf423649ddfa8ffb31a1875fa02a983cd248c72dfb0ea")));

    public static final ItemWrapper BARBED_WIRE = ItemWrapper.create(Keys.BARBED_WIRE, new SlimefunItemStack(
            Keys.BARBED_WIRE.asSlimefunId(),
            HeadUtils.fromHashCode("b2ac6c219004d82dfa627ffab664f29c53ecc112d91c9d7a9c915c426832412")));

    public static final ItemWrapper MATERIAL_HIVE = ItemWrapper.create(Keys.MATERIAL_HIVE,
            new SlimefunItemStack(
                    Keys.MATERIAL_HIVE.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BEEHIVE)));

    public static final ItemWrapper WIRELESS_CHARGER = ItemWrapper.create(Keys.WIRELESS_CHARGER,
            new SlimefunItemStack(
                    Keys.WIRELESS_CHARGER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.CLAY)));
    public static final ItemWrapper SEED_PLUCKER = ItemWrapper.create(Keys.SEED_PLUCKER,
            new SlimefunItemStack(
                    Keys.SEED_PLUCKER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.ORANGE_STAINED_GLASS)));

    public static final ItemWrapper BANDAID_MANAGER = ItemWrapper.create(Keys.BANDAID_MANAGER,
            new SlimefunItemStack(
                    Keys.BANDAID_MANAGER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LAPIS_BLOCK)));

    public static final ItemWrapper ORECHID = ItemWrapper.create(Keys.ORECHID,
            new SlimefunItemStack(
                    Keys.ORECHID.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.WITHER_ROSE)));

    public static final ItemWrapper WIRELESS_ENERGY_POINT = ItemWrapper.create(Keys.WIRELESS_ENERGY_POINT,
            new SlimefunItemStack(
                    Keys.WIRELESS_ENERGY_POINT.asSlimefunId(),
                    HeadUtils.fromHashCode("335a21d95e8597759fb259c951ea68e1ad3374ca41e56ef126ffabfe03c1e0")));

    public static final ItemWrapper WIRELESS_ENERGY_BANK = ItemWrapper.create(Keys.WIRELESS_ENERGY_BANK,
            new SlimefunItemStack(
                    Keys.WIRELESS_ENERGY_BANK.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.SNOW_BLOCK)));

    public static final ItemWrapper TESSERACT = ItemWrapper.create(Keys.TESSERACT,
            new SlimefunItemStack(
                    Keys.TESSERACT.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.PURPUR_BLOCK)));

    public static final ItemWrapper WIRELESS_ITEM_INPUT = ItemWrapper.create(Keys.WIRELESS_ITEM_INPUT,
            new SlimefunItemStack(
                    Keys.WIRELESS_ITEM_INPUT.asSlimefunId(),
                    HeadUtils.fromHashCode("abb55560c695d976b346e188d3df2bcd8c5aa32b933141a9715c42f64cb6cee")));

    public static final ItemWrapper WIRELESS_ITEM_OUTPUT = ItemWrapper.create(Keys.WIRELESS_ITEM_OUTPUT,
            new SlimefunItemStack(
                    Keys.WIRELESS_ITEM_OUTPUT.asSlimefunId(),
                    HeadUtils.fromHashCode("c510d9b61ca333d2946c61a26cb17e374d4adb573b46afdebaf89f65ba5d4ae2")));

    public static final ItemWrapper EXTERNAL_HEATER = ItemWrapper.create(Keys.EXTERNAL_HEATER,
            new SlimefunItemStack(
                    Keys.EXTERNAL_HEATER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.BRICKS)));

    public static final ItemWrapper GROWTH_CHAMBER = ItemWrapper.create(Keys.GROWTH_CHAMBER,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.GREEN_STAINED_GLASS)));

    public static final ItemWrapper GROWTH_CHAMBER_OCEAN = ItemWrapper.create(Keys.GROWTH_CHAMBER_OCEAN,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_OCEAN.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.CYAN_STAINED_GLASS)));

    public static final ItemWrapper GROWTH_CHAMBER_NETHER = ItemWrapper.create(Keys.GROWTH_CHAMBER_NETHER,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_NETHER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.RED_STAINED_GLASS)));

    public static final ItemWrapper GROWTH_CHAMBER_END = ItemWrapper.create(Keys.GROWTH_CHAMBER_END,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_END.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.MAGENTA_STAINED_GLASS)));

    public static final ItemWrapper GROWTH_CHAMBER_MK2 = ItemWrapper.create(Keys.GROWTH_CHAMBER_MK2,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_MK2.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LIME_STAINED_GLASS)));

    public static final ItemWrapper GROWTH_CHAMBER_MK2_OCEAN = ItemWrapper.create(Keys.GROWTH_CHAMBER_MK2_OCEAN,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_MK2_OCEAN.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LIGHT_BLUE_STAINED_GLASS)));

    public static final ItemWrapper GROWTH_CHAMBER_MK2_NETHER = ItemWrapper.create(Keys.GROWTH_CHAMBER_MK2_NETHER,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_MK2_NETHER.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.RED_STAINED_GLASS))); // END Machines

    public static final ItemWrapper GROWTH_CHAMBER_MK2_END = ItemWrapper.create(Keys.GROWTH_CHAMBER_MK2_END,
            new SlimefunItemStack(
                    Keys.GROWTH_CHAMBER_MK2_END.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.PURPLE_STAINED_GLASS)));

    public static final ItemWrapper PETAL_APOTHECARY = ItemWrapper.create(Keys.PETAL_APOTHECARY,
            new SlimefunItemStack(
                    Keys.PETAL_APOTHECARY.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.CAULDRON)));

    public static final ItemWrapper LAVA_BOTTLE = ItemWrapper.create(Keys.LAVA_BOTTLE, new SlimefunItemStack(
            Keys.LAVA_BOTTLE.asSlimefunId(),
            Color.ORANGE,
            new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1)));

    public static final ItemWrapper MILK_BOTTLE = ItemWrapper.create(Keys.MILK_BOTTLE, new SlimefunItemStack(
            Keys.MILK_BOTTLE.asSlimefunId(),
            Color.WHITE,
            new PotionEffect(PotionEffectType.SATURATION, 1, 1)));

    public static final ItemWrapper HONEY_BUCKET = ItemWrapper.create(Keys.HONEY_BUCKET,
            new SlimefunItemStack(
                    Keys.HONEY_BUCKET.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.LAVA_BUCKET)));

    public static final ItemWrapper POTION_BUCKET = ItemWrapper.create(Keys.POTION_BUCKET,
            new SlimefunItemStack(
                    Keys.POTION_BUCKET.asSlimefunId(),
                    MaterialCompat.safe(XMaterial.WATER_BUCKET)));

    public static final class Keys {
        public static final TypedKey<ItemWrapper> WOOD_MACHINE_CORE = TypedKey.create("dynatech", "wood_machine_core");

        public static final TypedKey<ItemWrapper> STONE_MACHINE_CORE = TypedKey.create("dynatech",
                "stone_machine_core");

        public static final TypedKey<ItemWrapper> IRON_MACHINE_CORE = TypedKey.create("dynatech", "iron_machine_core");

        public static final TypedKey<ItemWrapper> DIAMOND_MACHINE_CORE = TypedKey.create("dynatech",
                "diamond_machine_core");

        public static final TypedKey<ItemWrapper> ENCHANTED_MACHINE_CORE = TypedKey.create("dynatech",
                "enchanted_machine_core");

        public static final TypedKey<ItemWrapper> ANCIENT_MACHINE_CORE = TypedKey.create("dynatech",
                "ancient_machine_core");

        public static final TypedKey<ItemWrapper> ENERGY_STORAGE_COMPONENT = TypedKey.create("dynatech",
                "energy_storage_component");

        public static final TypedKey<ItemWrapper> ENERGY_INPUT_COMPONENT = TypedKey.create("dynatech",
                "energy_input_component");

        public static final TypedKey<ItemWrapper> ENERGY_OUTPUT_COMPONENT = TypedKey.create("dynatech",
                "energy_output_component");

        public static final TypedKey<ItemWrapper> DEGRADED_WATER_MILL = TypedKey.create("dynatech",
                "degraded_water_mill");

        public static final TypedKey<ItemWrapper> DEGRADED_WATER_MILL_2 = TypedKey.create("dynatech",
                "degraded_water_mill_2");

        public static final TypedKey<ItemWrapper> DEGRADED_WIND_MILL = TypedKey.create("dynatech",
                "degraded_wind_mill");

        public static final TypedKey<ItemWrapper> DEGRADED_WIND_MILL_2 = TypedKey.create("dynatech",
                "degraded_wind_mill_2");

        public static final TypedKey<ItemWrapper> DEGRADED_EGG_MILL = TypedKey.create("dynatech",
                "degraded_egg_mill");

        public static final TypedKey<ItemWrapper> DEGRADED_EGG_MILL_2 = TypedKey.create("dynatech",
                "degraded_egg_mill_2");

        public static final TypedKey<ItemWrapper> WATER_MILL = TypedKey.create("dynatech",
                "water_mill");

        public static final TypedKey<ItemWrapper> WATER_MILL_2 = TypedKey.create("dynatech",
                "water_mill_2");

        public static final TypedKey<ItemWrapper> WIND_MILL = TypedKey.create("dynatech",
                "wind_mill");

        public static final TypedKey<ItemWrapper> WIND_MILL_2 = TypedKey.create("dynatech",
                "wind_mill_2");

        public static final TypedKey<ItemWrapper> EGG_MILL = TypedKey.create("dynatech",
                "egg_mill");

        public static final TypedKey<ItemWrapper> EGG_MILL_2 = TypedKey.create("dynatech",
                "egg_mill_2");

        public static final TypedKey<ItemWrapper> DURABILITY_GENERATOR = TypedKey.create("dynatech",
                "durability_generator");

        public static final TypedKey<ItemWrapper> FOOD_GENERATOR = TypedKey.create("dynatech", "food_generator");

        public static final TypedKey<ItemWrapper> STARDUST_GENERATOR = TypedKey.create("dynatech",
                "stardust_generator");
        public static final TypedKey<ItemWrapper> STAINLESS_STEEL_INGOT = TypedKey
                .create("dynatech", "stainless_steel_ingot");

        public static final TypedKey<ItemWrapper> STAINLESS_STEEL_ROTOR = TypedKey
                .create("dynatech", "stainless_steel_rotor");

        public static final TypedKey<ItemWrapper> COAL_COKE = TypedKey
                .create("dynatech", "coal_coke");

        public static final TypedKey<ItemWrapper> BEE = TypedKey.create("dynatech", "bee");
        public static final TypedKey<ItemWrapper> ROBOTIC_BEE = TypedKey.create("dynatech", "robotic_bee");
        public static final TypedKey<ItemWrapper> ADVANCED_ROBOTIC_BEE = TypedKey.create("dynatech",
                "advanced_robotic_bee");

        public static final TypedKey<ItemWrapper> VEX_GEM = TypedKey.create("dynatech", "vex_gem");

        public static final TypedKey<ItemWrapper> MACHINE_SCRAP = TypedKey.create("dynatech", "machine_scrap");

        public static final TypedKey<ItemWrapper> ADVANCED_MACHINE_SCRAP = TypedKey.create("dynatech",
                "advanced_machine_scrap");

        public static final TypedKey<ItemWrapper> STAR_DUST = TypedKey.create("dynatech", "star_dust");

        public static final TypedKey<ItemWrapper> GHOSTLY_ESSENCE = TypedKey.create("dynatech", "ghostly_essence");

        public static final TypedKey<ItemWrapper> TESSERACTING_OBJ = TypedKey.create("dynatech", "tesseracting_object");

        public static final TypedKey<ItemWrapper> LIVINGROCK = TypedKey.create("dynatech", "livingrock");

        public static final TypedKey<ItemWrapper> LIVINGWOOD = TypedKey.create("dynatech", "livingwood");
        public static final TypedKey<ItemWrapper> ELECTRICAL_STIMULATOR = TypedKey.create("dynatech",
                "electrical_stimulator");

        public static final TypedKey<ItemWrapper> ANGEL_GEM = TypedKey.create("dynatech", "angel_gem");

        public static final TypedKey<ItemWrapper> INVENTORY_FILTER = TypedKey.create("dynatech", "inventory_filter");

        public static final TypedKey<ItemWrapper> RECIPE_BOOK = TypedKey.create("dynatech", "recipe_book");

        public static final TypedKey<ItemWrapper> AUTO_INPUT_UPGRADE = TypedKey.create("dynatech",
                "auto_input_upgrade");

        public static final TypedKey<ItemWrapper> AUTO_OUTPUT_UPGRADE = TypedKey.create("dynatech",
                "auto_output_upgrade");

        public static final TypedKey<ItemWrapper> FLUID_TANK = TypedKey.create("dynatech", "fluid_tank");

        public static final TypedKey<ItemWrapper> PICNIC_BASKET = TypedKey.create("dynatech", "picnic_basket");

        public static final TypedKey<ItemWrapper> SOUL_BOUND_PICNIC_BASKET = TypedKey.create("dynatech",
                "soul_bound_picnic_basket");

        public static final TypedKey<ItemWrapper> SCOOP = TypedKey.create("dynatech", "scoop");

        public static final TypedKey<ItemWrapper> DIMENSIONAL_HOME = TypedKey.create("dynatech", "dimensional_home");

        public static final TypedKey<ItemWrapper> ITEM_BAND_HASTE = TypedKey.create("dynatech", "haste_item_band");

        public static final TypedKey<ItemWrapper> ITEM_BAND_HEALTH = TypedKey.create("dynatech", "health_item_band");

        public static final TypedKey<ItemWrapper> ITEM_BAND_SPEED = TypedKey.create("dynatech", "speed_item_band");

        public static final TypedKey<ItemWrapper> TESSERACT_BINDER = TypedKey.create("dynatech", "tesseract_binder");

        public static final TypedKey<ItemWrapper> LIQUID_TANK = TypedKey.create("dynatech", "liquid_tank");

        public static final TypedKey<ItemWrapper> WITHER_SKELETON_GOLEM = TypedKey.create("dynatech",
                "wither_skeleton_golem");

        public static final TypedKey<ItemWrapper> COAL_COKE_OVEN = TypedKey.create("dynatech", "coal_coke_oven");

        public static final TypedKey<ItemWrapper> AUTO_KITCHEN = TypedKey.create("dynatech", "auto_kitchen");
        public static final TypedKey<ItemWrapper> KITCHEN_AUTO_CRAFTER = TypedKey.create("dynatech",
                "kitchen_auto_crafter");

        public static final TypedKey<ItemWrapper> ANTIGRAVITY_BUBBLE = TypedKey.create("dynatech",
                "antigravity_bubble");

        public static final TypedKey<ItemWrapper> WEATHER_CONTROLLER = TypedKey.create("dynatech",
                "weather_controller");

        public static final TypedKey<ItemWrapper> POTION_SPRINKLER = TypedKey.create("dynatech", "potion_sprinkler");

        public static final TypedKey<ItemWrapper> BARBED_WIRE = TypedKey.create("dynatech", "barbed_wire");

        public static final TypedKey<ItemWrapper> MATERIAL_HIVE = TypedKey.create("dynatech", "material_hive");

        public static final TypedKey<ItemWrapper> WIRELESS_CHARGER = TypedKey.create("dynatech", "wireless_charger");

        public static final TypedKey<ItemWrapper> SEED_PLUCKER = TypedKey.create("dynatech", "seed_plucker");

        public static final TypedKey<ItemWrapper> BANDAID_MANAGER = TypedKey.create("dynatech", "bandaid_manager");

        public static final TypedKey<ItemWrapper> ORECHID = TypedKey.create("dynatech", "orechid");

        public static final TypedKey<ItemWrapper> WIRELESS_ENERGY_POINT = TypedKey.create("dynatech",
                "wireless_energy_point");
        public static final TypedKey<ItemWrapper> WIRELESS_ENERGY_BANK = TypedKey.create("dynatech",
                "wireless_energy_bank");

        public static final TypedKey<ItemWrapper> TESSERACT = TypedKey.create("dynatech", "tesseract");

        public static final TypedKey<ItemWrapper> WIRELESS_ITEM_INPUT = TypedKey.create("dynatech",
                "wireless_item_input");

        public static final TypedKey<ItemWrapper> WIRELESS_ITEM_OUTPUT = TypedKey.create("dynatech",
                "wireless_item_output");

        public static final TypedKey<ItemWrapper> EXTERNAL_HEATER = TypedKey.create("dynatech", "external_heater");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER = TypedKey.create("dynatech", "growth_chamber");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_OCEAN = TypedKey.create("dynatech",
                "ocean_growth_chamber");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_NETHER = TypedKey.create("dynatech",
                "nether_growth_chamber");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_END = TypedKey.create("dynatech",
                "end_growth_chamber");

        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_MK2 = TypedKey.create("dynatech",
                "growth_chamber_mark_2");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_MK2_OCEAN = TypedKey.create("dynatech",
                "ocean_growth_chamber_mark_2");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_MK2_NETHER = TypedKey.create("dynatech",
                "nether_growth_chamber_mark_2");
        public static final TypedKey<ItemWrapper> GROWTH_CHAMBER_MK2_END = TypedKey.create("dynatech",
                "end_growth_chamber_mark_2");

        public static final TypedKey<ItemWrapper> PETAL_APOTHECARY = TypedKey.create("dynatech", "petal_apothecary");

        public static final TypedKey<ItemWrapper> LAVA_BOTTLE = TypedKey.create("dynatech", "lava_bottle");
        public static final TypedKey<ItemWrapper> MILK_BOTTLE = TypedKey.create("dynatech", "milk_bottle");
        public static final TypedKey<ItemWrapper> HONEY_BUCKET = TypedKey.create("dynatech", "honey_bucket");
        public static final TypedKey<ItemWrapper> POTION_BUCKET = TypedKey.create("dynatech", "potion_bucket");
    }
}

