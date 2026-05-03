package com.unpainperdu.houblonneux.datagen.asset.model.block;

import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

public class BlockModelProvider
{
    private final BlockModelGenerators blockModelsGenerator;
    private final ItemModelGenerators itemModelsGenerator;
    private final ModelProvider provider;

    public BlockModelProvider(BlockModelGenerators blockModelsGenerator, ItemModelGenerators itemModelsGenerator, ModelProvider provider)
    {
        this.blockModelsGenerator = blockModelsGenerator;
        this.itemModelsGenerator = itemModelsGenerator;
        this.provider = provider;
        generateModel();
    }

    public void generateModel()
    {
        createHopBlock();
    }

    public void createHopBlock()
    {
        Block hopBlock = ModBlockRegister.HOP.get();
        MultiVariant seed = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_seed", ModelTemplates.CROP, TextureMapping::crop));
        MultiVariant top_grow = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_top_grow", ModelTemplates.CROP, TextureMapping::crop));
        MultiVariant middle_grow = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_middle_grow", ModelTemplates.CROP, TextureMapping::crop));
        MultiVariant middle_flowered = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_middle_flowered", ModelTemplates.CROP, TextureMapping::crop));

        this.blockModelsGenerator.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(hopBlock)
                                .with(
                                        PropertyDispatch.initial(HopBlock.HOP_BLOCKSTATE)
                                                .select(HopBlockstate.SEED, seed)
                                                .select(HopBlockstate.TOP_GROW, top_grow)
                                                .select(HopBlockstate.MIDDLE_GROW, middle_grow)
                                                .select(HopBlockstate.MIDDLE_FLOWERED, middle_flowered)

                                )
                );

    }
}