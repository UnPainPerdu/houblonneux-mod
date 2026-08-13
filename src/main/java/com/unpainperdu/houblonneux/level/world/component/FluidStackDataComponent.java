package com.unpainperdu.houblonneux.level.world.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.fluids.FluidStack;

public record FluidStackDataComponent(FluidStack fluidStack)
{
    public static final Codec<FluidStackDataComponent> CODEC = RecordCodecBuilder.create(
            i -> i.group(
                            FluidStack.CODEC.fieldOf("fluidstack").forGetter(FluidStackDataComponent::fluidStack)
                    )
                    .apply(i, FluidStackDataComponent::new)
    );
}
