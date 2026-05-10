package net.dadamalda.added_in.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ItemVersionData(String version) {
    public static final Codec<ItemVersionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("version").forGetter(ItemVersionData::version)
    ).apply(instance, ItemVersionData::new));
}
