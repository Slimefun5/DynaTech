package me.profelements.dynatech.items.misc;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.core.multiblocks.MultiBlockMachine;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import me.profelements.dynatech.utils.MaterialCompat;

public class WitherGolem extends MultiBlockMachine {
  
  public WitherGolem(ItemGroup itemGroup, SlimefunItemStack item) {
    super(itemGroup, item, new ItemStack[] {null, new ItemStack(MaterialCompat.safe(XMaterial.CARVED_PUMPKIN)), null, null, new ItemStack(MaterialCompat.safe(XMaterial.POLISHED_BLACKSTONE)), null, null, new ItemStack(MaterialCompat.safe(XMaterial.POLISHED_BLACKSTONE)), null}, BlockFace.SELF);
  }

  @Override
  public void onInteract(@Nonnull Player p, @Nonnull Block b) {
    Block pumpkinHead = b.getRelative(BlockFace.UP);
    Block bottomBlackstone = b.getRelative(BlockFace.DOWN);
  
    p.getWorld().spawnEntity(b.getLocation().add(0.5, -1, 0.5), EntityType.WITHER_SKELETON);

    pumpkinHead.setType(MaterialCompat.safe(XMaterial.AIR));
    b.setType(MaterialCompat.safe(XMaterial.AIR));
    bottomBlackstone.setType(MaterialCompat.safe(XMaterial.AIR));
  }

}

