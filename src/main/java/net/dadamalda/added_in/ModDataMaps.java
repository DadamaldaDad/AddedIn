package net.dadamalda.added_in;

import net.dadamalda.added_in.datamap.ItemVersionData;
import net.dadamalda.added_in.datamap.PotionVersionData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class ModDataMaps {
    public static final DataMapType<Item, ItemVersionData> ITEM_VERSION_DATA = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(Added_in.MODID, "version_data"),
            Registries.ITEM,
            ItemVersionData.CODEC
    ).synced(
            ItemVersionData.CODEC,
            false
    ).build();

    public static final DataMapType<Potion, PotionVersionData> POTION_VERSION_DATA = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(Added_in.MODID, "version_data"),
            Registries.POTION,
            PotionVersionData.CODEC
    ).synced(
            PotionVersionData.CODEC,
            false
    ).build();
}
