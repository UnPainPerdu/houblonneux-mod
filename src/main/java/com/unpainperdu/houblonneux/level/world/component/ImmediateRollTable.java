package com.unpainperdu.houblonneux.level.world.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ImmediateRollTable(boolean isImmediatelyRollingTable)
{
    public static final Codec<ImmediateRollTable> CODEC = RecordCodecBuilder.create(
            i -> i.group(
                            Codec.BOOL.fieldOf("is_immediately_rolling_table").forGetter(ImmediateRollTable::isImmediatelyRollingTable)
                    )
                    .apply(i, ImmediateRollTable::new)
    );
}