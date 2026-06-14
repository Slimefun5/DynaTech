package me.profelements.dynatech.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.entity.EntityType;

/**
 * Resolves {@link EntityType} constants by name, skipping any that do not exist on
 * the running server. Keeps DynaTech functional on legacy versions (e.g. 1.8) where
 * modern mobs like {@code PIGLIN}, {@code RAVAGER} or {@code PHANTOM} are absent,
 * without throwing a {@code NoSuchFieldError} when the list is built.
 */
public final class EntityTypeCompat {

    private EntityTypeCompat() {
        throw new UnsupportedOperationException("Utility Class");
    }

    @Nonnull
    public static List<EntityType> resolve(@Nonnull String... names) {
        List<EntityType> result = new ArrayList<>();
        for (String name : names) {
            try {
                result.add(EntityType.valueOf(name));
            } catch (IllegalArgumentException e) {
                // Absent on this server version; skip it.
            }
        }
        return result;
    }
}
