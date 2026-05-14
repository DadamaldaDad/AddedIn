package net.dadamalda.added_in.data_loading;

import net.dadamalda.added_in.records.EnchantmentVersionData;
import net.dadamalda.added_in.records.ItemVersionData;
import net.dadamalda.added_in.records.PaintingVersionData;
import net.dadamalda.added_in.records.PotionVersionData;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class VersionDataHolder {
    public static Map<ResourceLocation, ItemVersionData> ITEMS = new HashMap<>();
    public static Map<ResourceLocation, PotionVersionData> POTIONS = new HashMap<>();
    public static Map<ResourceLocation, EnchantmentVersionData> ENCHANTMENTS = new HashMap<>();
    public static Map<ResourceLocation, PaintingVersionData> PAINTINGS = new HashMap<>();
}
