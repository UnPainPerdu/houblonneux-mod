package com.unpainperdu.houblonneux.datagen.data.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.gen.structure.processor.TurnOnImmediateRollTableDataCompProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class ModProcessorListRegister
{
    public static final ResourceKey<StructureProcessorList> VILLAGE_DISPENSER = register("village_dispenser");

    public static void boostrap(BootstrapContext<StructureProcessorList> context)
    {
        context.register(VILLAGE_DISPENSER, new StructureProcessorList(
                ImmutableList.of(
                        new TurnOnImmediateRollTableDataCompProcessor()
                )));
    }

    protected static ResourceKey<StructureProcessorList> register(String path)
    {
        return ResourceKey.create(Registries.PROCESSOR_LIST, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, path));
    }
}