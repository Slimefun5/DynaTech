package me.profelements.dynatech.utils;


import me.profelements.dynatech.utils.HeadUtils;
import io.github.thebusybiscuit.slimefun5.utils.compatibility.VersionedPlayerHead;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class HeadUtils {

    private HeadUtils() {}

    public static ItemStack fromHashCode(String hash) {
        String url = "https://textures.minecraft.net/texture/" + hash;
        String json = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
        String base64 = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        return VersionedPlayerHead.getItemStack(base64);
    }
}

