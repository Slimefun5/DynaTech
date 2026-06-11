package me.profelements.dynatech.registries;

import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;

public final class TypedKey<T> {

    private final NamespacedKey key;

    public TypedKey(@Nonnull NamespacedKey key) {
        this.key = key;
    }

    @Nonnull
    public NamespacedKey key() {
        return key;
    }

    @Nonnull
    public io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey sfKey() {
        return new io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey(key.getNamespace(), key.getKey());
    }

    public static <T> TypedKey<T> create(NamespacedKey key) {
        return new TypedKey<>(key);
    }

    public static <T> TypedKey<T> create(String namespace, String key) {
        return new TypedKey<>(new NamespacedKey(namespace, key));
    }

    // THIS IS TEMPORARY TILL SLIMEFUN MOVES TO NamespacedKey
    public String asSlimefunId() {
        return this.key().toString().replace(':', '_').toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypedKey)) return false;
        TypedKey<?> that = (TypedKey<?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "TypedKey[key=" + key + "]";
    }
}
