package me.profelements.dynatech.utils;

import java.util.Objects;

import com.google.common.base.Preconditions;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import me.profelements.dynatech.registries.Registries;
import me.profelements.dynatech.registries.TypedKey;

public final class ItemWrapper {

    private final TypedKey<ItemWrapper> key;
    private final SlimefunItemStack stack;

    public ItemWrapper(TypedKey<ItemWrapper> key, SlimefunItemStack stack) {
        this.key = key;
        this.stack = stack;
    }

    public TypedKey<ItemWrapper> key() {
        return key;
    }

    public SlimefunItemStack stack() {
        return stack;
    }

    public static ItemWrapper create(TypedKey<ItemWrapper> key, SlimefunItemStack stack) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(stack);

        ItemWrapper item = new ItemWrapper(key, stack);
        Registries.ITEMS.register(key, item);

        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemWrapper)) return false;
        ItemWrapper that = (ItemWrapper) o;
        return Objects.equals(key, that.key) && Objects.equals(stack, that.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, stack);
    }

    @Override
    public String toString() {
        return "ItemWrapper[key=" + key + ", stack=" + stack + "]";
    }
}
