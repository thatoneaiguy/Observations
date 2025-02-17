package com.eeverest.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TraitsUpdateData(String trait, String trait2, String trait3) {
    public static final Codec<TraitsUpdateData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("trait").forGetter(TraitsUpdateData::getTrait),
                    Codec.STRING.fieldOf("trait2").forGetter(TraitsUpdateData::getTrait2),
                    Codec.STRING.fieldOf("trait3").forGetter(TraitsUpdateData::getTrait3)
            ).apply(instance, TraitsUpdateData::new)
    );

    public TraitsUpdateData(String trait, String trait2, String trait3) {
        this.trait = trait;
        this.trait2 = trait2;
        this.trait3 = trait3;
    }

    public String getTrait() {
        return this.trait;
    }

    public String getTrait2() {
        return this.trait2;
    }

    public String getTrait3() {
        return this.trait3;
    }
}
