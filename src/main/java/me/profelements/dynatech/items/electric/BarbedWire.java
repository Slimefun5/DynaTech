package me.profelements.dynatech.items.electric;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import me.profelements.dynatech.items.abstracts.AbstractElectricTicker;
import me.profelements.dynatech.utils.EntityTypeCompat;

public class BarbedWire extends AbstractElectricTicker {

    private static final int DEFAULT_ENTITY_PUSH_FORCE = 10;
    private static final int DEFAULT_ENTITY_DETECT_RANGE = 9;

    private final ItemSetting<Integer> entityPushForce = new ItemSetting<>(this, "entity-push-force", DEFAULT_ENTITY_PUSH_FORCE);
    private final ItemSetting<Integer> entityDetectRange = new ItemSetting<>(this, "entity-detect-range", DEFAULT_ENTITY_DETECT_RANGE);

    @ParametersAreNonnullByDefault
    public BarbedWire(ItemGroup group, SlimefunItemStack item,  RecipeType recipeType, ItemStack[] recipe) {
        super(group, item, recipeType, recipe);
        addItemSetting(entityPushForce, entityDetectRange);
    }

    private List<EntityType> getEntityWhitelist() {
        return EntityTypeCompat.resolve(
            "ARROW", "BLAZE", "CAVE_SPIDER", "CREEPER", "DRAGON_FIREBALL", "DROWNED",
            "ELDER_GUARDIAN", "ENDER_DRAGON", "ENDERMAN", "ENDERMITE", "EVOKER", "FIREBALL",
            "GHAST", "GIANT", "GUARDIAN", "HOGLIN", "HUSK", "ILLUSIONER", "MAGMA_CUBE",
            "PHANTOM", "PIGLIN", "PIGLIN_BRUTE", "PILLAGER", "RAVAGER", "SHULKER",
            "SHULKER_BULLET", "SILVERFISH", "SKELETON", "SLIME", "SMALL_FIREBALL", "SPIDER",
            "STRAY", "VEX", "VINDICATOR", "WITCH", "WITHER", "WITHER_SKELETON", "WITHER_SKULL",
            "ZOGLIN", "ZOMBIE", "ZOMBIFIED_PIGLIN");
    }

    protected void tick(Block block, SlimefunItem item) {
        int entityDetectionRange = entityDetectRange.getValue();
        List<EntityType> entityTypeWhitelist = getEntityWhitelist();
        Location loc = block.getLocation();
    
        for (Entity e : block.getWorld().getNearbyEntities(loc, entityDetectionRange, entityDetectionRange, entityDetectionRange)) {
            if (entityTypeWhitelist.contains(e.getType())) {

                /// Find the Direction Vector. normailze it. multiply by the push force. apply it to the entity.
                Vector launchDirection = loc.subtract(e.getLocation()).toVector().normalize().multiply(entityPushForce.getValue());

                /// If any of the components are not finite just set it to zero.
                if (NumberConversions.isFinite(launchDirection.getX()) && NumberConversions.isFinite(launchDirection.getY())
                    && NumberConversions.isFinite(launchDirection.getZ())) {
                    e.setVelocity(launchDirection);
                } else {
                    e.setVelocity(new Vector(0, 0, 0));
                }
            }
        } 
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }
}

