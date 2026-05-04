package com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record PatchConfiguration(int spread, int minFlowerNumber, int maxFlowerNumber, List<BlockStateProvider> states,
                                 List<TagKey<Block>> groundAllowed) implements FeatureConfiguration
{
    public static final Codec<PatchConfiguration> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                            Codec.INT.fieldOf("spread").forGetter(g -> g.spread),
                            Codec.INT.fieldOf("min_flower_number").forGetter(g -> g.minFlowerNumber),
                            Codec.INT.fieldOf("max_flower_number").forGetter(g -> g.maxFlowerNumber),
                            Codec.list(BlockStateProvider.CODEC).fieldOf("states").forGetter(g -> g.states),
                            Codec.list(TagKey.codec(Registries.BLOCK)).fieldOf("ground_allowed").forGetter(g -> g.groundAllowed)
                    )
                    .apply(builder, PatchConfiguration::new)
    );

    public static class Builder
    {
        private int spread;
        int minFlowerNumber;
        private int maxFlowerNumber;
        private List<BlockStateProvider> states = List.of();
        private List<TagKey<Block>> groundAllowed;

        /**
         * @param spread must be > 0
         */
        public Builder spread(int spread)
        {
            this.spread = spread;
            return this;
        }

        /**
         * @param minFlowerNumber must be > 0
         */
        public Builder minFlowerNumber(int minFlowerNumber)
        {
            this.minFlowerNumber = minFlowerNumber;
            return this;
        }

        /**
         * @param maxFlowerNumber must be > 0 and >= minHopNumber
         */
        public Builder maxFlowerNumber(int maxFlowerNumber)
        {
            this.maxFlowerNumber = maxFlowerNumber;
            return this;
        }

        /**
         * @param states must not be empty, all states will be generated randomly
         */
        public Builder states(List<BlockStateProvider> states)
        {
            this.states = states;
            return this;
        }

        /**
         * @param groundAllowed must not be empty, all tag in will be accepted as ground
         */
        public Builder groundAllowed(List<TagKey<Block>> groundAllowed)
        {
            this.groundAllowed = groundAllowed;
            return this;
        }

        public PatchConfiguration build()
        {
            if (this.spread < 1)
            {
                throw new IllegalArgumentException("spread must be > 0");
            }
            if (this.minFlowerNumber < 1)
            {
                throw new IllegalArgumentException("minHopNumber must be > 0");
            }
            if (this.maxFlowerNumber < 1 || this.maxFlowerNumber < this.minFlowerNumber)
            {
                throw new IllegalArgumentException("maxFlowerNumber must be > 0 and >= minHopNumber");
            }
            if (this.groundAllowed.isEmpty())
            {
                throw new IllegalArgumentException("groundAllowed must not be empty");
            }

            return new PatchConfiguration(this.spread, this.minFlowerNumber, this.maxFlowerNumber, this.states, this.groundAllowed);
        }
    }
}