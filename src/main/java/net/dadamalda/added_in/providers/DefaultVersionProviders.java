package net.dadamalda.added_in.providers;

import net.dadamalda.added_in.data_loading.VersionDataHolder;
import net.dadamalda.added_in.records.EnchantmentVersionData;
import net.dadamalda.added_in.records.ItemVersionData;
import net.dadamalda.added_in.records.PaintingVersionData;
import net.dadamalda.added_in.records.PotionVersionData;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Objects;
import java.util.Optional;

public class DefaultVersionProviders {
    public static void register() {
        VersionProviders.registerItem((itemStack -> {
            ResourceLocation item_id = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            ItemVersionData data = VersionDataHolder.ITEMS.get(item_id);
            if(data == null) return VersionResult.pass();
            return VersionResult.success(data.version());
        }));

        VersionProviders.registerItem(itemStack -> {
            PotionContents potionContents = itemStack.getComponents().get(DataComponents.POTION_CONTENTS);
            if(potionContents == null) return VersionResult.pass();
            Optional<Holder<Potion>> optionalPotionHolder = potionContents.potion();
            if(optionalPotionHolder.isEmpty()) return VersionResult.none();
            ResourceLocation potion_id = BuiltInRegistries.POTION.getKey(optionalPotionHolder.get().value());
            PotionVersionData data = VersionDataHolder.POTIONS.get(potion_id);
            if(data == null) return VersionResult.none();
            if(itemStack.is(Items.POTION) && !Objects.equals(data.version(), "???")) {
                return VersionResult.success(data.version());
            } else if(itemStack.is(Items.SPLASH_POTION) && !Objects.equals(data.splash_version(), "???")) {
                return VersionResult.success(data.splash_version());
            } else if(itemStack.is(Items.LINGERING_POTION) && !Objects.equals(data.lingering_version(), "???")) {
                return VersionResult.success(data.lingering_version());
            } else if(itemStack.is(Items.TIPPED_ARROW) && !Objects.equals(data.arrow_version(), "???")) {
                return VersionResult.success(data.arrow_version());
            } else {
                return VersionResult.none();
            }
        });

        VersionProviders.registerItem(itemStack -> {
            if(!itemStack.is(Items.WHITE_BANNER)) return VersionResult.pass();
            Unit dataComponent = itemStack.getComponents().get(DataComponents.HIDE_ADDITIONAL_TOOLTIP);
            if(dataComponent == null) return VersionResult.pass();
            return VersionResult.success("1.14");
        });

        VersionProviders.registerItem(itemStack -> {
            if(!itemStack.is(Items.ENCHANTED_BOOK)) return VersionResult.pass();
            ItemEnchantments enchantments = itemStack.getComponents().get(DataComponents.STORED_ENCHANTMENTS);
            if(enchantments == null) return VersionResult.none();
            if(enchantments.keySet().size() != 1) return VersionResult.none();
            Optional<Holder<Enchantment>> optionalEnchantmentHolder = enchantments.keySet().stream().findFirst();
            if(optionalEnchantmentHolder.isEmpty()) return VersionResult.none();
            Holder<Enchantment> enchantmentHolder = optionalEnchantmentHolder.get();
            ResourceLocation enchantmentId = enchantmentHolder.unwrapKey()
                    .map(ResourceKey::location)
                    .orElse(ResourceLocation.fromNamespaceAndPath("minecraft", "unknown"));
            EnchantmentVersionData data = VersionDataHolder.ENCHANTMENTS.get(enchantmentId);
            if(data == null) return VersionResult.none();
            return VersionResult.success(data.version());
        });

        VersionProviders.registerItem(itemStack -> {
            if(!itemStack.is(Items.PAINTING)) return VersionResult.pass();
            CustomData entityData = itemStack.getComponents().get(DataComponents.ENTITY_DATA);
            if(entityData == null || !entityData.contains("variant")) return VersionResult.pass();
            Tag variantTag = entityData.copyTag().get("variant");
            if(!(variantTag instanceof StringTag stringVariantTag)) return VersionResult.none();
            ResourceLocation paintingId = ResourceLocation.parse(stringVariantTag.getAsString());
            PaintingVersionData data = VersionDataHolder.PAINTINGS.get(paintingId);
            if(data == null) return VersionResult.none();
            return VersionResult.success(data.version());
        });
    }
}
