package me.profelements.dynatech.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Version-safe sound playback. Modern {@link Sound} enum names (1.9+) like
 * {@code BLOCK_ANVIL_FALL} do not exist on 1.8, where the enum uses names such as
 * {@code ANVIL_LAND}. This resolves the modern name, falling back to a known legacy
 * name, and silently no-ops if neither exists so a tick/event never crashes.
 */
public final class SoundCompat {

    // Modern (1.9+) name -> legacy (1.8) name fallback.
    private static final Map<String, String> LEGACY_NAMES = new HashMap<>();

    static {
        LEGACY_NAMES.put("BLOCK_ANVIL_FALL", "ANVIL_LAND");
        LEGACY_NAMES.put("ENTITY_PLAYER_BURP", "BURP");
    }

    private SoundCompat() {
        throw new UnsupportedOperationException("Utility Class");
    }

    public static void play(@Nonnull Player player, @Nonnull Location location,
                            @Nonnull String soundName, float volume, float pitch) {
        Sound sound = resolve(soundName);
        if (sound != null) {
            player.playSound(location, sound, volume, pitch);
        }
    }

    private static Sound resolve(@Nonnull String name) {
        Sound sound = byName(name);
        if (sound == null) {
            String legacy = LEGACY_NAMES.get(name);
            if (legacy != null) {
                sound = byName(legacy);
            }
        }
        return sound;
    }

    private static Sound byName(@Nonnull String name) {
        try {
            return Sound.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
