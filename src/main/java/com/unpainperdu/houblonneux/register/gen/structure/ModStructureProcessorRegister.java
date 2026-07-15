package com.unpainperdu.houblonneux.register.gen.structure;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.gen.structure.processor.TurnOnImmediateRollTableDataCompProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructureProcessorRegister
{
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPE = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Houblonneux.MOD_ID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<TurnOnImmediateRollTableDataCompProcessor>> TURN_ON_IMMEDIATE_ROLL_TABLE_DATA_COMP_PROCESSOR = STRUCTURE_PROCESSOR_TYPE.register(
            "turn_on_immediate_roll_table_data_comp_processor",
            () -> () -> TurnOnImmediateRollTableDataCompProcessor.CODEC
    );

    public static void register(IEventBus event)
    {
        STRUCTURE_PROCESSOR_TYPE.register(event);
    }
}