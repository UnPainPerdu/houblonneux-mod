package com.unpainperdu.houblonneux.datagen.data.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class ModStructureTemplatePoolRegister
{
    public static final ResourceKey<StructureTemplatePool> VILLAGE_DISPENSER = register("village_dispenser");

    public static void boostrap(BootstrapContext<StructureTemplatePool> context)
    {
        HolderGetter<StructureProcessorList> processorListHolderGetter = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> emptyStructureTemplatePool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);

        context.register(VILLAGE_DISPENSER,
                new StructureTemplatePool(
                        emptyStructureTemplatePool,
                        ImmutableList.of(
                                Pair.of(StructurePoolElement.single("houblonneux:village/common/dispenser_1", processorListHolderGetter.getOrThrow(ModProcessorListRegister.VILLAGE_DISPENSER)), 1)
                        ),
                        StructureTemplatePool.Projection.TERRAIN_MATCHING
                )
        );
    }

    protected static ResourceKey<StructureTemplatePool> register(String path)
    {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, path));
    }
}