package net.dadamalda.added_in.data_loading;

import net.dadamalda.added_in.records.SimpleVersionData;
import net.dadamalda.added_in.records.PotionVersionData;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class VersionDataHolder {
    public static Map<ResourceLocation, SimpleVersionData> ITEMS = new HashMap<>();
    public static Map<ResourceLocation, PotionVersionData> POTIONS = new HashMap<>();
    public static Map<ResourceLocation, SimpleVersionData> ENCHANTMENTS = new HashMap<>();
    public static Map<ResourceLocation, SimpleVersionData> PAINTINGS = new HashMap<>();
    public static Map<ResourceLocation, SimpleVersionData> BLOCKS = new HashMap<>();
    public static Map<ResourceLocation, SimpleVersionData> ENTITIES = new HashMap<>();
}
