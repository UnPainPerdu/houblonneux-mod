package com.unpainperdu.houblonneux.datagen.asset.model;

import com.mojang.math.Quadrant;
import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import com.unpainperdu.houblonneux.level.world.block.block.CoasterBlock;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.registries.DeferredBlock;

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
        createBrewingBarrel(ModBlockRegister.OAK_BREWING_BARREL);
        //beer dispenser
        Block dispenserBlock = ModBlockRegister.BEER_DISPENSER.get();
        MultiVariant beerDispenserLower = plainVariant(this.blockModelsGenerator.createSuffixedVariant(dispenserBlock, "_lower", ModModelTemplate.EMPTY_WITH_PARTICLE, _ -> new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))));
        MultiVariant beerDispenserUpper = plainVariant(this.blockModelsGenerator.createSuffixedVariant(dispenserBlock, "_upper", ModModelTemplate.EMPTY_WITH_PARTICLE, _ -> new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))));
        createDoubleHeightBlockWithFacing(dispenserBlock, beerDispenserLower, beerDispenserUpper);
        itemModelsGenerator.itemModelOutput.accept(
                dispenserBlock.asItem(),
                new CuboidItemModelWrapper.Unbaked(
                        getModelLocationWithCustomPrefix(dispenserBlock, "block/entity/default_"),
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

    public void createBrewingBarrel(DeferredBlock<BrewingBarrelBlock> deferredBlock)
    {
        BrewingBarrelBlock block = deferredBlock.get();
        MultiVariant base = plainVariant(ModModelTemplate.BREWING_BARREL.create(block, ModTextureMapper.getBrewingStationTextureMapping(block), this.blockModelsGenerator.modelOutput));
        MultiPartGenerator modelDef = MultiPartGenerator.multiPart(block)
                .with(condition().term(BlockStateProperties.FACING, Direction.NORTH).term(BrewingBarrelBlock.POSITION, 0), getVariantWithFacingAndPosition(base, Direction.NORTH, 0));
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 0);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 0);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 0);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 1);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 1);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 1);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 1);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 2);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 2);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 2);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 2);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 3);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 3);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 3);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 3);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 4);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 4);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 4);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 4);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 5);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 5);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 5);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 5);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 6);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 6);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 6);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 6);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.NORTH, 7);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.SOUTH, 7);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.EAST, 7);
        modelDef = withForBrewingBarrel(modelDef, base, Direction.WEST, 7);
        this.blockModelsGenerator.blockStateOutput.accept(modelDef);
    }

    private MultiPartGenerator withForBrewingBarrel(MultiPartGenerator modelDef, MultiVariant base, Direction facing, int position)
    {
        return modelDef.with(condition().term(BlockStateProperties.FACING, facing).term(BrewingBarrelBlock.POSITION, position), getVariantWithFacingAndPosition(base, facing, position));
    }

    private MultiVariant getVariantWithFacingAndPosition(MultiVariant model, Direction facing, int position)
    {
        switch (facing)
        {
            case NORTH ->
            {
                return switch (position)
                {
                    case 1 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R90)).with(Y_ROT_180);
                    case 2 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R270)).with(Y_ROT_180);
                    case 3 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R180)).with(Y_ROT_180);
                    case 4 -> model.with(NOP).with(VariantMutator.Z_ROT.withValue(Quadrant.R90));
                    case 5 -> model.with(NOP).with(VariantMutator.Z_ROT.withValue(Quadrant.R180));
                    case 6 -> model.with(NOP);
                    case 7 -> model.with(NOP).with(VariantMutator.Z_ROT.withValue(Quadrant.R270));
                    default -> model.with(Y_ROT_180); //0
                };
            }
            case SOUTH ->
            {
                return switch (position)
                {
                    case 1 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R270));
                    case 2 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R90));
                    case 3 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R180));
                    case 4 -> model.with(NOP).with(VariantMutator.Z_ROT.withValue(Quadrant.R270)).with(Y_ROT_180);
                    case 5 -> model.with(NOP).with(VariantMutator.Z_ROT.withValue(Quadrant.R180)).with(Y_ROT_180);
                    case 6 -> model.with(Y_ROT_180);
                    case 7 -> model.with(NOP).with(VariantMutator.Z_ROT.withValue(Quadrant.R90)).with(Y_ROT_180);
                    default -> model.with(NOP); //0
                };
            }
            case EAST ->
            {
                return switch (position)
                {
                    case 1 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R270)).with(X_ROT_270);
                    case 2 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R90)).with(X_ROT_90);
                    case 3 ->  model.with(VariantMutator.Z_ROT.withValue(Quadrant.R180)).with(Y_ROT_90);
                    case 4 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R90)).with(X_ROT_270);
                    case 5 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R180)).with(Y_ROT_270);
                    case 6 -> model.with(Y_ROT_90);
                    case 7 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R270)).with(X_ROT_90);
                    default -> model.with(Y_ROT_270); //0
                };
            }
            default -> //WEST
            {
                return switch (position)
                {
                    case 1 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R270)).with(X_ROT_90);
                    case 2 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R90)).with(X_ROT_270);
                    case 3 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R180)).with(Y_ROT_270);
                    case 4 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R90)).with(X_ROT_90);
                    case 5 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R180)).with(Y_ROT_90);
                    case 6 -> model.with(Y_ROT_270);
                    case 7 -> model.with(VariantMutator.Z_ROT.withValue(Quadrant.R270)).with(X_ROT_270);
                    default -> model.with(Y_ROT_90); //0
                };
            }
        }
    }

    public static Identifier getModelLocationWithCustomPrefix(Block block, String prefix)
    {
        Identifier key = BuiltInRegistries.BLOCK.getKey(block);
        return key.withPrefix(prefix);
    }
}