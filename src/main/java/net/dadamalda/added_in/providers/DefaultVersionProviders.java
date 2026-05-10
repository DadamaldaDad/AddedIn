package net.dadamalda.added_in.providers;

import net.dadamalda.added_in.ModDataMaps;
import net.dadamalda.added_in.datamap.ItemVersionData;
import net.dadamalda.added_in.datamap.PotionVersionData;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.Optional;

public class DefaultVersionProviders {
    public static void register() {
        VersionProviders.register((itemStack -> {
            Holder<Item> holder = itemStack.getItemHolder();
            ItemVersionData data = holder.getData(ModDataMaps.ITEM_VERSION_DATA);
            if(data == null) return VersionResult.pass();
            return VersionResult.success(data.version());
        }));

        VersionProviders.register(itemStack -> {
            PotionContents potionContents = itemStack.getComponents().get(DataComponents.POTION_CONTENTS);
            if(potionContents == null) return VersionResult.pass();
            Optional<Holder<Potion>> optionalPotionHolder = potionContents.potion();
            if(optionalPotionHolder.isEmpty()) return VersionResult.none();
            Holder<Potion> potionHolder = optionalPotionHolder.get();
            PotionVersionData data = potionHolder.getData(ModDataMaps.POTION_VERSION_DATA);
            if(data == null) return VersionResult.none();
            if(itemStack.is(Items.POTION)) {
                return VersionResult.success(data.version());
            } else if(itemStack.is(Items.SPLASH_POTION)) {
                return VersionResult.success(data.splash_version());
            } else if(itemStack.is(Items.LINGERING_POTION)) {
                return VersionResult.success(data.lingering_version());
            } else if(itemStack.is(Items.TIPPED_ARROW)) {
                return VersionResult.success(data.arrow_version());
            } else {
                return VersionResult.pass();
            }
        });
    }
}
