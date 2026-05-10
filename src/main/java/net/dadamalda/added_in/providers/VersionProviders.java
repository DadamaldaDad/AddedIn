package net.dadamalda.added_in.providers;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TriState;

import java.util.ArrayList;
import java.util.List;

public class VersionProviders {
    private static List<VersionProvider> providers = new ArrayList<>();

    public static void register(VersionProvider provider) {
        providers.addFirst(provider);
    }

    public static VersionResult getItemVersion(ItemStack itemStack) {
        for (VersionProvider provider : providers) {
            VersionResult result = provider.getVersion(itemStack);
            if(result.state() == TriState.FALSE) return VersionResult.none();
            if(result.state() == TriState.TRUE) return result;
        }
        return VersionResult.none();
    }
}
