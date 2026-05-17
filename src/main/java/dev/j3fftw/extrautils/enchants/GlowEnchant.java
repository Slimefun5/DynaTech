package dev.j3fftw.extrautils.enchants;

import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowEnchant extends Enchantment {

    private final NamespacedKey key;
    private final Set<String> ids = new HashSet<>();

    public GlowEnchant(@Nonnull NamespacedKey key, @Nonnull String[] applicableItems) {
        this.key = key;
        ids.addAll(Arrays.asList(applicableItems));
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Nonnull
    @Override
    public String translationKey() {
        return "enchantment.extra_utils.glow";
    }

    @Nonnull
    @Override
    public String getTranslationKey() {
        return "enchantment.extra_utils.glow";
    }

    @Nonnull
    @Override
    @Deprecated
    public String getName() {
        return "EXTRA_UTILS_GLOW";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Nonnull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    @Deprecated
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@Nonnull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.hasItemMeta()) {
            final ItemMeta itemMeta = item.getItemMeta();
            final Optional<String> id = Slimefun.getItemDataService().getItemData(itemMeta);

            if (id.isPresent()) {
                return ids.contains(id.get());
            }
        }
        return false;
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Nonnull
    @Override
    public io.papermc.paper.registry.set.RegistryKeySet<org.bukkit.inventory.ItemType> getPrimaryItems() {
        return io.papermc.paper.registry.set.RegistrySet.keySet(io.papermc.paper.registry.RegistryKey.ITEM, Collections.emptyList());
    }

    @Nonnull
    @Override
    public io.papermc.paper.registry.set.RegistryKeySet<org.bukkit.inventory.ItemType> getSupportedItems() {
        return io.papermc.paper.registry.set.RegistrySet.keySet(io.papermc.paper.registry.RegistryKey.ITEM, Collections.emptyList());
    }

    @Nonnull
    @Override
    public Component description() {
        return Component.text("Makes item glow");
    }

    @Override
    public int getAnvilCost() {
        return 0;
    }

    @Nonnull
    @Override
    public Component displayName(int level) {
        return Component.text("Glow");
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public int getMinModifiedCost(int level) {
        return 0;
    }

    @Override
    public int getMaxModifiedCost(int level) {
        return 0;
    }

    @Nonnull
    @Override
    public io.papermc.paper.enchantments.EnchantmentRarity getRarity() {
        return io.papermc.paper.enchantments.EnchantmentRarity.COMMON;
    }

    @Override
    public float getDamageIncrease(int level, @Nonnull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public float getDamageIncrease(int level, @Nonnull org.bukkit.entity.EntityType entityType) {
        return 0;
    }

    @Nonnull
    @Override
    public Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Collections.singleton(EquipmentSlotGroup.ANY);
    }

    @Nonnull
    @Override
    public io.papermc.paper.registry.set.RegistryKeySet<Enchantment> getExclusiveWith() {
        return io.papermc.paper.registry.set.RegistrySet.keySet(io.papermc.paper.registry.RegistryKey.ENCHANTMENT, Collections.emptyList());
    }

}
