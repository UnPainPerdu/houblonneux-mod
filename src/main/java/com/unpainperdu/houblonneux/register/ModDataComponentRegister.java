package com.unpainperdu.houblonneux.register;

import com.mojang.serialization.Codec;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.component.ImmediateRollTable;
import com.unpainperdu.houblonneux.level.world.component.WrappedDispenserTradeTableKey;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponentRegister
{
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WrappedDispenserTradeTableKey>> WRAPPED_DISPENSER_TRADE_TABLE_KEY = register(
            "wrapped_dispenser_trade_table_key",
            b -> b.persistent(WrappedDispenserTradeTableKey.CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ImmediateRollTable>> IMMEDIATE_ROLL_TABLE = register(
            "immediate_roll_table",
            b -> b.persistent(ImmediateRollTable.CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUIDSTACK = register(
            "fluidstack",
            b -> b.persistent(SimpleFluidContent.CODEC)
                    .networkSynchronized(SimpleFluidContent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BREWING_TIME = register(
            "brewing_time",
            b -> b.persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
    );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String id, UnaryOperator<DataComponentType.Builder<T>> builder)
    {
        return DATA_COMPONENT_TYPE.register(id, () -> builder.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus event)
    {
        DATA_COMPONENT_TYPE.register(event);
    }
}