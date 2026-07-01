package me.profelements.dynatech.utils;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;

/**
 * Version-safe access to attribute-backed entity values. The {@code Attribute} API
 * (1.9+) is read reflectively; on older servers (e.g. 1.8) the deprecated
 * {@code LivingEntity#getMaxHealth()} is used instead.
 */
public final class EntityCompat {

    private static final Method GET_ATTRIBUTE = resolveGetAttribute();
    private static final Object MAX_HEALTH_ATTRIBUTE = resolveMaxHealthAttribute();

    private EntityCompat() {
        throw new UnsupportedOperationException("Utility Class");
    }

    @SuppressWarnings("deprecation")
    public static double maxHealth(@Nonnull LivingEntity entity) {
        if (GET_ATTRIBUTE != null && MAX_HEALTH_ATTRIBUTE != null) {
            try {
                Object instance = GET_ATTRIBUTE.invoke(entity, MAX_HEALTH_ATTRIBUTE);
                if (instance != null) {
                    Method getValue = instance.getClass().getMethod("getValue");
                    return (double) getValue.invoke(instance);
                }
            } catch (ReflectiveOperationException e) {
                // Fall through to the legacy accessor.
            }
        }
        return entity.getMaxHealth();
    }

    private static Method resolveGetAttribute() {
        try {
            Class<?> attribute = Class.forName("org.bukkit.attribute.Attribute");
            return LivingEntity.class.getMethod("getAttribute", attribute);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object resolveMaxHealthAttribute() {
        try {
            Class attribute = Class.forName("org.bukkit.attribute.Attribute");
            try {
                return Enum.valueOf(attribute, "GENERIC_MAX_HEALTH");
            } catch (IllegalArgumentException e) {
                return Enum.valueOf(attribute, "MAX_HEALTH");
            }
        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            return null;
        }
    }
}
