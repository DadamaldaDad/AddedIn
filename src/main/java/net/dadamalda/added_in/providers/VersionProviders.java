package net.dadamalda.added_in.providers;

import net.dadamalda.added_in.compat.jade.BlockWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;

import java.util.ArrayList;
import java.util.List;

public class VersionProviders {
    private static final List<VersionProvider<ItemStack>> itemVersionProviders = new ArrayList<>();
    private static final List<VersionProvider<BlockWrapper>> blockVersionProviders = new ArrayList<>();
    private static final List<VersionProvider<Entity>> entityVersionProviders = new ArrayList<>();

    public static void registerItem(VersionProvider<ItemStack> provider) {
        itemVersionProviders.addFirst(provider);
    }

    public static void registerBlock(VersionProvider<BlockWrapper> provider) {
        blockVersionProviders.addFirst(provider);
    }

    public static void registerEntity(VersionProvider<Entity> provider) {
        entityVersionProviders.addFirst(provider);
    }

    public static VersionResult getItemVersion(ItemStack itemStack) {
        for (VersionProvider<ItemStack> provider : itemVersionProviders) {
            VersionResult result = provider.getVersion(itemStack);
            if(result.state() == TriState.FALSE) return VersionResult.none();
            if(result.state() == TriState.TRUE) return result;
        }
        return VersionResult.none();
    }

    public static VersionResult getBlockVersion(BlockWrapper blockWrapper) {
        for (VersionProvider<BlockWrapper> provider : blockVersionProviders) {
            VersionResult result = provider.getVersion(blockWrapper);
            if(result.state() == TriState.FALSE) return VersionResult.none();
            if(result.state() == TriState.TRUE) return result;
        }
        return VersionResult.none();
    }

    public static VersionResult getEntityVersion(Entity entity) {
        for (VersionProvider<Entity> provider : entityVersionProviders) {
            VersionResult result = provider.getVersion(entity);
            if(result.state() == TriState.FALSE) return VersionResult.none();
            if(result.state() == TriState.TRUE) return result;
        }
        return VersionResult.none();
    }
}
