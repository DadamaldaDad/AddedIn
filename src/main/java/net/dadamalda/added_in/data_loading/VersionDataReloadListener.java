package net.dadamalda.added_in.data_loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dadamalda.added_in.Added_in;
import net.dadamalda.added_in.records.SimpleVersionData;
import net.dadamalda.added_in.records.PotionVersionData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.ModList;

import java.util.Map;

public class VersionDataReloadListener extends SimpleJsonResourceReloadListener {

    public VersionDataReloadListener() {
        super(new Gson(), "version_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        boolean jadeLoaded = ModList.get().isLoaded("jade");
        VersionDataHolder.ITEMS.clear();
        VersionDataHolder.POTIONS.clear();
        for(Map.Entry<ResourceLocation, JsonElement> entry : resourceLocationJsonElementMap.entrySet()) {
            if(!entry.getKey().getNamespace().equals(Added_in.MODID)) continue;
            if(!entry.getValue().isJsonObject()) continue;
            JsonObject root = entry.getValue().getAsJsonObject();
            if(root.get("items") != null && root.get("items").isJsonObject()) {
                JsonObject items = root.get("items").getAsJsonObject();
                for(Map.Entry<String, JsonElement> item : items.asMap().entrySet()) {
                    if(!item.getValue().isJsonPrimitive()) continue;
                    ResourceLocation item_id = ResourceLocation.parse(item.getKey());
                    SimpleVersionData data = new SimpleVersionData(item.getValue().getAsString());
                    VersionDataHolder.ITEMS.put(item_id, data);
                }
            }
            if(root.get("potions") != null && root.get("potions").isJsonObject()) {
                JsonObject potions = root.get("potions").getAsJsonObject();
                for(Map.Entry<String, JsonElement> potion : potions.asMap().entrySet()) {
                    if(!potion.getValue().isJsonObject()) continue;
                    ResourceLocation potion_id = ResourceLocation.parse(potion.getKey());
                    JsonObject potion_object = potion.getValue().getAsJsonObject();
                    String version = "???";
                    if(potion_object.has("potion") && potion_object.get("potion").isJsonPrimitive()) {
                        version = potion_object.get("potion").getAsString();
                    }
                    String splash_version = "???";
                    if(potion_object.has("splash_potion") && potion_object.get("splash_potion").isJsonPrimitive()) {
                        splash_version = potion_object.get("splash_potion").getAsString();
                    }
                    String lingering_version = "???";
                    if(potion_object.has("lingering_potion") && potion_object.get("lingering_potion").isJsonPrimitive()) {
                        lingering_version = potion_object.get("lingering_potion").getAsString();
                    }
                    String arrow_version = "???";
                    if(potion_object.has("tipped_arrow") && potion_object.get("tipped_arrow").isJsonPrimitive()) {
                        arrow_version = potion_object.get("tipped_arrow").getAsString();
                    }
                    PotionVersionData data = new PotionVersionData(version, splash_version, lingering_version, arrow_version);
                    VersionDataHolder.POTIONS.put(potion_id, data);
                }
            }
            if(root.get("enchantments") != null && root.get("enchantments").isJsonObject()) {
                JsonObject enchantments = root.get("enchantments").getAsJsonObject();
                for(Map.Entry<String, JsonElement> enchantment : enchantments.asMap().entrySet()) {
                    if(!enchantment.getValue().isJsonPrimitive()) continue;
                    ResourceLocation enchantmen_id = ResourceLocation.parse(enchantment.getKey());
                    SimpleVersionData data = new SimpleVersionData(enchantment.getValue().getAsString());
                    VersionDataHolder.ENCHANTMENTS.put(enchantmen_id, data);
                }
            }
            if(root.get("paintings") != null && root.get("paintings").isJsonObject()) {
                JsonObject paintings = root.get("paintings").getAsJsonObject();
                for(Map.Entry<String, JsonElement> painting : paintings.asMap().entrySet()) {
                    if(!painting.getValue().isJsonPrimitive()) continue;
                    ResourceLocation painting_id = ResourceLocation.parse(painting.getKey());
                    SimpleVersionData data = new SimpleVersionData(painting.getValue().getAsString());
                    VersionDataHolder.PAINTINGS.put(painting_id, data);
                }
            }



            if(jadeLoaded && root.get("blocks") != null && root.get("blocks").isJsonObject()) {
                JsonObject blocks = root.get("blocks").getAsJsonObject();
                for(Map.Entry<String, JsonElement> block : blocks.asMap().entrySet()) {
                    if(!block.getValue().isJsonPrimitive()) continue;
                    ResourceLocation block_id = ResourceLocation.parse(block.getKey());
                    SimpleVersionData data = new SimpleVersionData(block.getValue().getAsString());
                    VersionDataHolder.BLOCKS.put(block_id, data);
                }
            }
            if(jadeLoaded && root.get("entities") != null && root.get("entities").isJsonObject()) {
                JsonObject entities = root.get("entities").getAsJsonObject();
                for(Map.Entry<String, JsonElement> entity : entities.asMap().entrySet()) {
                    if(!entity.getValue().isJsonPrimitive()) continue;
                    ResourceLocation entity_id = ResourceLocation.parse(entity.getKey());
                    SimpleVersionData data = new SimpleVersionData(entity.getValue().getAsString());
                    VersionDataHolder.ENTITIES.put(entity_id, data);
                }
            }
        }
    }
}
