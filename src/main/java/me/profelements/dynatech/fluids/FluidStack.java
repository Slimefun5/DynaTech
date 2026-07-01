package me.profelements.dynatech.fluids;

import java.util.Objects;

import javax.annotation.Nullable;

import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.profelements.dynatech.compat.Pdc;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.profelements.dynatech.DynaTech;

public final class FluidStack {

    private final NamespacedKey fluid;
    private final int amount;

    public static int BUCKET_AMOUNT = 1000;
    public static int BOTTLE_AMOUNT = 250;
    public static NamespacedKey LAVA_FLUID = NamespacedKey.minecraft("lava");
    public static NamespacedKey WATER_FLUID = NamespacedKey.minecraft("water");
    public static NamespacedKey MILK_FLUID = NamespacedKey.minecraft("milk");
    public static NamespacedKey POTION_FLUID = NamespacedKey.minecraft("potion");

    public static String FLUID_KEY = "dynatech:fluid";
    public static String FLUID_AMOUNT_KEY = "dynatech:fluid_amount";

    public FluidStack(NamespacedKey fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
    }

    public NamespacedKey fluid() {
        return fluid;
    }

    public int amount() {
        return amount;
    }

    public static FluidStack of(NamespacedKey fluid, int amount) {
        return new FluidStack(fluid, amount);
    }

    public void toBlock(Block block) {
        String fluidType = BlockStorage.getLocationInfo(block.getLocation(), FLUID_KEY.toString());
        String fluidAmount = BlockStorage.getLocationInfo(block.getLocation(), FLUID_AMOUNT_KEY.toString());

        if (fluidType != null && fluidType != this.fluid().toString()) {
            return;
        }

        BlockStorage.addBlockInfo(block, FLUID_KEY.toString(), this.fluid().toString());

        int amt = 0;
        if (fluidAmount != null) {
            amt = Integer.parseInt(fluidAmount);
        }

        BlockStorage.addBlockInfo(block, FLUID_AMOUNT_KEY.toString(), String.valueOf(amt + this.amount()));
    }

    public static @Nullable FluidStack fromBlock(Block block) {
        String fluidType = BlockStorage.getLocationInfo(block.getLocation(), FLUID_KEY.toString());
        String fluidAmount = BlockStorage.getLocationInfo(block.getLocation(), FLUID_AMOUNT_KEY.toString());

        if (fluidType != null && fluidAmount != null) {
            return FluidStack.of(NamespacedKey.fromString(fluidType), Integer.parseInt(fluidAmount));
        } else {
            return null;
        }
    }

    public static @Nullable FluidStack fromItemStack(ItemStack itemStack) {
        String fluidType = Pdc.getString(itemStack.getItemMeta(), FLUID_KEY, "");
        int fluidAmount = Pdc.getInt(itemStack.getItemMeta(), FLUID_AMOUNT_KEY, 0);

        if (!fluidType.equals("") && fluidAmount != 0) {
            return FluidStack.of(NamespacedKey.fromString(fluidType), fluidAmount);
        } else {
            return null;
        }
    }

    public void toItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        String fluidType = Pdc.getString(meta, FLUID_KEY, "");
        int fluidAmount = Pdc.getInt(meta, FLUID_AMOUNT_KEY, 0);

        if (fluidType.equals("") || this.fluid().equals(NamespacedKey.fromString(fluidType))) {
            Pdc.setString(meta, FLUID_KEY, this.fluid().toString());
            Pdc.setInt(meta, FLUID_AMOUNT_KEY, fluidAmount + this.amount());
        }

        itemStack.setItemMeta(meta);
    }

    public ItemMeta apply(ItemMeta meta) {
        String fluidType = Pdc.getString(meta, FLUID_KEY, "");
        int fluidAmount = Pdc.getInt(meta, FLUID_AMOUNT_KEY, 0);

        if (fluidType != null && this.fluid() == NamespacedKey.fromString(fluidType)) {
            Pdc.setString(meta, FLUID_KEY, this.fluid().toString());
            Pdc.setInt(meta, FLUID_AMOUNT_KEY, fluidAmount + this.amount());
        }

        return meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FluidStack)) return false;
        FluidStack that = (FluidStack) o;
        return amount == that.amount && Objects.equals(fluid, that.fluid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fluid, amount);
    }

    @Override
    public String toString() {
        return "FluidStack[fluid=" + fluid + ", amount=" + amount + "]";
    }
}
