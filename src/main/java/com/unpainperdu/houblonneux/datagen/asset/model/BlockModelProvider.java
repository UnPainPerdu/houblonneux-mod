package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.level.world.block.block.CoasterBlock;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.Collections;
import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

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
        //beer dispenser
        Block dispenserBlock = ModBlockRegister.BEER_DISPENSER.get();
        MultiVariant beerDispenserLower = plainVariant(this.blockModelsGenerator.createSuffixedVariant(dispenserBlock, "_lower", ModModelTemplate.EMPTY_WITH_PARTICLE, _ -> new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))));
        MultiVariant beerDispenserUpper = plainVariant(this.blockModelsGenerator.createSuffixedVariant(dispenserBlock, "_upper", ModModelTemplate.EMPTY_WITH_PARTICLE, _ -> new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))));
        createDoubleHeightBlockWithFacing(dispenserBlock, beerDispenserLower, beerDispenserUpper);
        itemModelsGenerator.itemModelOutput.accept(
                dispenserBlock.asItem(),
                new CuboidItemModelWrapper.Unbaked(
                        getModelLocationWithCustomPrefix(dispenserBlock, "block_entity/default_"),
                        Optional.empty(),
                        Collections.emptyList()
                )
        );
        //coaster
        CoasterBlock coaster = ModBlockRegister.COASTER.get();
        MultiVariant coaster1 = plainVariant(this.blockModelsGenerator.createSuffixedVariant(coaster, "_01", ModModelTemplate.COASTER_1, _ -> new TextureMapping()));
        MultiVariant coaster2 = plainVariant(this.blockModelsGenerator.createSuffixedVariant(coaster, "_02", ModModelTemplate.COASTER_2, _ -> new TextureMapping()));
        MultiVariant coaster3 = plainVariant(this.blockModelsGenerator.createSuffixedVariant(coaster, "_03", ModModelTemplate.COASTER_3, _ -> new TextureMapping()));
        MultiVariant coaster4 = plainVariant(this.blockModelsGenerator.createSuffixedVariant(coaster, "_04", ModModelTemplate.COASTER_4, _ -> new TextureMapping()));
        this.blockModelsGenerator.blockStateOutput
                .accept(MultiVariantGenerator.dispatch(coaster)
                        .with(
                                PropertyDispatch.initial(CoasterBlock.FACING, CoasterBlock.COASTER_NUMBER)
                                        .select(Direction.NORTH, 1, coaster1)
                                        .select(Direction.EAST, 1, coaster1.with(Y_ROT_90))
                                        .select(Direction.SOUTH, 1, coaster1.with(Y_ROT_180))
                                        .select(Direction.WEST, 1, coaster1.with(Y_ROT_270))
                                        .select(Direction.NORTH, 2, coaster2)
                                        .select(Direction.EAST, 2, coaster2.with(Y_ROT_90))
                                        .select(Direction.SOUTH, 2, coaster2.with(Y_ROT_180))
                                        .select(Direction.WEST, 2, coaster2.with(Y_ROT_270))
                                        .select(Direction.NORTH, 3, coaster3)
                                        .select(Direction.EAST, 3, coaster3.with(Y_ROT_90))
                                        .select(Direction.SOUTH, 3, coaster3.with(Y_ROT_180))
                                        .select(Direction.WEST, 3, coaster3.with(Y_ROT_270))
                                        .select(Direction.NORTH, 4, coaster4)
                                        .select(Direction.EAST, 4, coaster4.with(Y_ROT_90))
                                        .select(Direction.SOUTH, 4, coaster4.with(Y_ROT_180))
                                        .select(Direction.WEST, 4, coaster4.with(Y_ROT_270))
                        ));
        itemModelsGenerator.itemModelOutput.accept(
                coaster.asItem(),
                new CuboidItemModelWrapper.Unbaked(
                        getModelLocationWithCustomPrefix(coaster, "block/").withSuffix("_1"),
                        Optional.empty(),
                        Collections.emptyList()
                )
        );
    }

    public void createHopBlock()
    {
        Block hopBlock = ModBlockRegister.HOP.get();
        MultiVariant seed = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_seed", ModelTemplates.CROSS, TextureMapping::cross));
        MultiVariant top_grow = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_top_grow", ModelTemplates.CROSS, TextureMapping::cross));
        MultiVariant middle_grow = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_middle_grow", ModelTemplates.CROSS, TextureMapping::cross));
        MultiVariant middle_flowered = plainVariant(this.blockModelsGenerator.createSuffixedVariant(hopBlock, "_middle_flowered", ModelTemplates.CROSS, TextureMapping::cross));

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

    public void createDoubleHeightBlockWithFacing(Block block, MultiVariant lower, MultiVariant upper)
    {
        this.blockModelsGenerator.blockStateOutput
                .accept(MultiVariantGenerator.dispatch(block)
                        .with(
                                PropertyDispatch.initial(HorizontalDirectionalBlock.FACING, BlockStateProperties.DOUBLE_BLOCK_HALF)
                                        .select(Direction.NORTH, DoubleBlockHalf.LOWER, lower)
                                        .select(Direction.EAST, DoubleBlockHalf.LOWER, lower.with(Y_ROT_90))
                                        .select(Direction.SOUTH, DoubleBlockHalf.LOWER, lower.with(Y_ROT_180))
                                        .select(Direction.WEST, DoubleBlockHalf.LOWER, lower.with(Y_ROT_270))
                                        .select(Direction.NORTH, DoubleBlockHalf.UPPER, upper)
                                        .select(Direction.EAST, DoubleBlockHalf.UPPER, upper.with(Y_ROT_90))
                                        .select(Direction.SOUTH, DoubleBlockHalf.UPPER, upper.with(Y_ROT_180))
                                        .select(Direction.WEST, DoubleBlockHalf.UPPER, upper.with(Y_ROT_270))
                        ));
    }

    public static Identifier getModelLocationWithCustomPrefix(Block block, String prefix)
    {
        Identifier key = BuiltInRegistries.BLOCK.getKey(block);
        return key.withPrefix(prefix);
    }
}