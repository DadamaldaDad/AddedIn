package net.dadamalda.added_in.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PotionVersionData(String version, String splash_version, String lingering_version, String arrow_version) {
    public static final Codec<PotionVersionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("version").forGetter(PotionVersionData::version),
            Codec.STRING.fieldOf("splash_version").forGetter(PotionVersionData::splash_version),
            Codec.STRING.fieldOf("lingering_version").forGetter(PotionVersionData::lingering_version),
            Codec.STRING.fieldOf("arrow_version").forGetter(PotionVersionData::arrow_version)
    ).apply(instance, PotionVersionData::new));
}
