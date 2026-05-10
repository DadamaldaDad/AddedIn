package net.dadamalda.added_in.providers;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface VersionProvider {
    VersionResult getVersion(ItemStack itemStack);
}
