package com.eeverest.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CosmeticUpdateData(String cosmetic) {
    public static final Codec<CosmeticUpdateData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("cosmetic").forGetter(CosmeticUpdateData::getCosmetic)).apply(instance, CosmeticUpdateData::new));

    public CosmeticUpdateData(String cosmetic) {
        this.cosmetic = cosmetic;
    }

    public String getCosmetic() {
        return this.cosmetic;
    }
}
