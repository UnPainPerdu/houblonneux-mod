package com.unpainperdu.houblonneux.register.gen.structure;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.gen.structure.processor.TurnOnImmediateRollTableDataCompProcessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructureProcessorRegister
{
    public static final DeferredRegister<MapCodec<? extends StructureProcessor>> STRUCTURE_PROCESSORS = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PROCESSOR, Houblonneux.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends StructureProcessor>, MapCodec<TurnOnImmediateRollTableDataCompProcessor>> TURN_ON_IMMEDIATE_ROLL_TABLE_DATA_COMP_PROCESSOR = STRUCTURE_PROCESSORS.register("turn_on_immediate_roll_table_data_comp_processor", () -> TurnOnImmediateRollTableDataCompProcessor.CODEC);

    public static void register(IEventBus modEventBus)
    {
        STRUCTURE_PROCESSORS.register(modEventBus);
    }
}