package me.profelements.dynatech.fluids;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class FluidTankAdapter {

    private FluidTankAdapter() {
    }

    public static @Nullable FluidStack getFluidFromItemStack(@Nonnull ItemStack itemStack) {
        switch (itemStack.getType()) {
            case WATER_BUCKET:
                return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BUCKET_AMOUNT);
            case MILK_BUCKET:
                return FluidStack.of(FluidStack.MILK_FLUID, FluidStack.BUCKET_AMOUNT);
            case LAVA_BUCKET:
                return FluidStack.of(FluidStack.LAVA_FLUID, FluidStack.BUCKET_AMOUNT);
            case POTION:
                if (itemStack.getItemMeta() instanceof PotionMeta) {
                    PotionMeta pm = (PotionMeta) itemStack.getItemMeta();
                    if (pm.getBasePotionData().getType() == PotionType.WATER) {
                        return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BOTTLE_AMOUNT);
                    } else {
                        return FluidStack.of(FluidStack.POTION_FLUID, FluidStack.BOTTLE_AMOUNT);
                    }
                }
            default:
                return null;
        }
    }

    public static @Nullable FluidStack getFluidStackFromBlock(@Nonnull Block block) {
        Preconditions.checkNotNull(block);
        switch (block.getType()) {
            case CAULDRON:
            case WATER:
            case LAVA:
                return getFluidStackFromLevelled(block);
            default:
                return null;
        }
    }

    public static @Nullable FluidStack getFluidStackFromLevelled(@Nonnull Block block) {
        Preconditions.checkNotNull(block);

        if (!(block.getBlockData() instanceof Levelled) || block.getType() == MaterialCompat.safe(XMaterial.COMPOSTER)) {
            return null;
        }

        Levelled lvl = (Levelled) block.getBlockData();

        // Cauldron levels are 1-3 (0 = a full water/lava source, handled below); higher isn't a usable fluid source.
        if (lvl.getLevel() > 3) {
            return null;
        }

        // This is either MaterialCompat.safe(XMaterial.LAVA), or MaterialCompat.safe(XMaterial.WATER)
        if (lvl.getLevel() == 0) {
            switch (block.getType()) {
                case WATER:
                    return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BUCKET_AMOUNT);
                case LAVA:
                    return FluidStack.of(FluidStack.LAVA_FLUID, FluidStack.BUCKET_AMOUNT);
                default:
                    return null;
            }
        }

        if (block.getType() == MaterialCompat.safe(XMaterial.CAULDRON)) {
            return FluidStack.of(FluidStack.WATER_FLUID, (lvl.getLevel() / lvl.getMaximumLevel()) * 1000);
        }

        return null;
    }
}
