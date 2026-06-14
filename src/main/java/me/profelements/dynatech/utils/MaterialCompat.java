package me.profelements.dynatech.utils;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

/**
 * Resolves {@link XMaterial} constants to a {@link Material} that exists on the
 * running server. Keeps DynaTech loadable on legacy versions (e.g. 1.8) where
 * modern constants like {@code *_STAINED_GLASS_PANE} variants or {@code PHANTOM_MEMBRANE}
 * are absent. Falls back to {@link Material#STONE} when a material has no legacy
 * equivalent, so a null is never passed to an item constructor.
 */
public final class MaterialCompat {

    private MaterialCompat() {
        throw new UnsupportedOperationException("Utility Class");
    }

    @Nonnull
    public static Material safe(@Nonnull XMaterial material) {
        Material resolved = material.parseMaterial();
        return resolved != null ? resolved : Material.STONE;
    }

    private static final Method IS_AIR = resolve("isAir");
    private static final Method IS_ITEM = resolve("isItem");

    /**
     * Version-safe {@code Material#isAir()} (added in 1.13). On older servers the
     * known air constants are matched directly.
     */
    public static boolean isAir(@Nonnull Material material) {
        if (IS_AIR != null) {
            try {
                return (boolean) IS_AIR.invoke(material);
            } catch (ReflectiveOperationException e) {
                // Fall through to the legacy check.
            }
        }
        return material == Material.AIR || material.name().endsWith("_AIR");
    }

    /**
     * Version-safe {@code Material#isItem()} (added in 1.13). On older servers every
     * obtainable material is treated as an item.
     */
    public static boolean isItem(@Nonnull Material material) {
        if (IS_ITEM != null) {
            try {
                return (boolean) IS_ITEM.invoke(material);
            } catch (ReflectiveOperationException e) {
                // Fall through to the legacy default.
            }
        }
        return !isAir(material);
    }

    private static Method resolve(@Nonnull String name) {
        try {
            return Material.class.getMethod(name);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
