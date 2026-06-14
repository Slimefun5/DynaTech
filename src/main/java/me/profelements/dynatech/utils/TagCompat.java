package me.profelements.dynatech.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

/**
 * Version-safe access to {@code org.bukkit.Tag} groups (added in 1.13).
 *
 * On 1.13+ the live {@code Tag.LOGS} values are read reflectively. On older
 * servers (e.g. 1.8) where {@code Tag} is absent, a hard-coded set of the log
 * materials that exist on the running version is returned, so recipe
 * registration still works without referencing the missing class.
 */
public final class TagCompat {

    private static final XMaterial[] LOG_MATERIALS = {
        XMaterial.OAK_LOG, XMaterial.SPRUCE_LOG, XMaterial.BIRCH_LOG,
        XMaterial.JUNGLE_LOG, XMaterial.ACACIA_LOG, XMaterial.DARK_OAK_LOG
    };

    private TagCompat() {
        throw new UnsupportedOperationException("Utility Class");
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public static Set<Material> logs() {
        Set<Material> result = new LinkedHashSet<>();
        try {
            Class<?> tagClass = Class.forName("org.bukkit.Tag");
            Object logsTag = tagClass.getField("LOGS").get(null);
            Collection<Material> values =
                (Collection<Material>) logsTag.getClass().getMethod("getValues").invoke(logsTag);
            result.addAll(values);
            return result;
        } catch (ReflectiveOperationException | LinkageError e) {
            for (XMaterial material : LOG_MATERIALS) {
                Material resolved = material.parseMaterial();
                if (resolved != null) {
                    result.add(resolved);
                }
            }
            return result;
        }
    }
}
