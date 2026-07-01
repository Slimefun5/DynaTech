package me.profelements.dynatech.registries;


import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class ItemGroups {

    public static final void init(Registry<ItemGroup> registry) {
        registry.register(Keys.GENERAL, GENERAL);
        registry.register(Keys.RESOURCES, RESOURCES);
        registry.register(Keys.TOOLS, TOOLS);
        registry.register(Keys.MACHINES, MACHINES);
        registry.register(Keys.GENERATORS, GENERATORS);
        registry.register(Keys.EXPERIMENTAL, EXPERIMENTAL);
        registry.register(Keys.APIARIES, HIVES);
    }

    public static final NestedItemGroup GENERAL = (NestedItemGroup) new NestedItemGroup(
            Keys.GENERAL.sfKey(),
            CustomItemStack.create(MaterialCompat.safe(XMaterial.CONDUIT), "&bDynaTech")).setTheme("machines");

    public static final SubItemGroup RESOURCES = (SubItemGroup) new SubItemGroup(
            Keys.RESOURCES.sfKey(), GENERAL,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.PUFFERFISH), "&bDynaTech Resources")).setTheme("resources");

    public static final SubItemGroup TOOLS = (SubItemGroup) new SubItemGroup(Keys.TOOLS.sfKey(),
            GENERAL, CustomItemStack.create(MaterialCompat.safe(XMaterial.DIAMOND_AXE), "&bDynaTech Tools")).setTheme("tools");

    public static final SubItemGroup MACHINES = (SubItemGroup) new SubItemGroup(Keys.MACHINES.sfKey(), GENERAL,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.SEA_LANTERN), "&bDynaTech Machines")).setTheme("machines");

    public static final SubItemGroup GENERATORS = (SubItemGroup) new SubItemGroup(Keys.GENERATORS.sfKey(), GENERAL,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.PRISMARINE_BRICKS), "&bDynaTech Generators")).setTheme("energy_tech");

    public static final SubItemGroup EXPERIMENTAL = (SubItemGroup) new SubItemGroup(Keys.EXPERIMENTAL.sfKey(), GENERAL,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.REDSTONE_LAMP), "&fDynaTech Experimental")).setTheme("machines");

    public static final SubItemGroup HIVES = (SubItemGroup) new SubItemGroup(Keys.APIARIES.sfKey(),
            GENERAL, CustomItemStack.create(MaterialCompat.safe(XMaterial.BEEHIVE), "&bDynaTech Apiaries")).setTheme("resources");

    public static final class Keys {
        public static final TypedKey<ItemGroup> GENERAL = TypedKey.create("dynatech", "general");
        public static final TypedKey<ItemGroup> RESOURCES = TypedKey.create("dynatech", "resources");
        public static final TypedKey<ItemGroup> TOOLS = TypedKey.create("dynatech", "tools");
        public static final TypedKey<ItemGroup> MACHINES = TypedKey.create("dynatech", "machines");
        public static final TypedKey<ItemGroup> GENERATORS = TypedKey.create("dynatech", "generators");
        public static final TypedKey<ItemGroup> EXPERIMENTAL = TypedKey.create("dynatech", "experimental");
        public static final TypedKey<ItemGroup> APIARIES = TypedKey.create("dynatech", "apiaries");
    }
}

