package com.unpainperdu.houblonneux.level.world.block.block.crop;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.level.world.block.blockstate.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class HopBlock extends VegetationBlock implements BonemealableBlock
{
    public static final MapCodec<HopBlock> CODEC = simpleCodec(HopBlock::new);
    public static final int MIN_HEIGHT = 4;
    public static final int MAX_HEIGHT = 10;
    public static final BooleanProperty CAN_GROW = ModBlockStateProperties.CAN_GROW;
    public static final EnumProperty<HopBlockstate> HOP_BLOCKSTATE = ModBlockStateProperties.HOP;
    private static final VoxelShape SEED = Block.column(16, 16, 0, 2);
    private static final VoxelShape MIDDLE_GROW = Shapes.block();
    private static final VoxelShape TOP_GROW = Block.column(16, 16, 0, 12);

    public HopBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CAN_GROW, true).setValue(HOP_BLOCKSTATE, HopBlockstate.SEED));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(CAN_GROW, HOP_BLOCKSTATE);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(HOP_BLOCKSTATE))
        {
            case SEED -> SEED;
            case TOP_GROW -> TOP_GROW;
            default -> MIDDLE_GROW;
        };
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random)
    {
        boolean isMiddleFlowered = state.getValue(HOP_BLOCKSTATE) == HopBlockstate.MIDDLE_FLOWERED;
        boolean isMiddleGrow = state.getValue(HOP_BLOCKSTATE) == HopBlockstate.MIDDLE_GROW;
        boolean isTopAllowed = level.getBlockState(pos.above()).is(this);
        if (level instanceof ServerLevel serverLevel)
        {
            if (!isTopAllowed && (isMiddleFlowered || isMiddleGrow))
            {
                if (isMiddleFlowered)
                {
                    dropHopLootTable(serverLevel, state, pos);
                    serverLevel.setBlock(pos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.TOP_GROW), 2);
                }
                else
                {
                    serverLevel.setBlock(pos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.TOP_GROW), 2);
                }
            }
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state)
    {
        return !(state.getValue(HOP_BLOCKSTATE) == HopBlockstate.MIDDLE_FLOWERED)
                && !(state.getValue(HOP_BLOCKSTATE) == HopBlockstate.TOP_GROW && !state.getValue(CAN_GROW));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isAreaLoaded(pos, 1)
                && level.getRandom().nextFloat() < 0.23
                && level.getRawBrightness(pos, 0) >= 9
                && (state.getValue(HOP_BLOCKSTATE) != HopBlockstate.MIDDLE_FLOWERED || (state.getValue(HOP_BLOCKSTATE) != HopBlockstate.TOP_GROW && !state.getValue(CAN_GROW)))
        )
        {
            this.grow(level, random, pos, state);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos belowPos = pos.below();
        BlockState belowBlockState = level.getBlockState(belowPos);
        var soilDecision = level.getBlockState(belowPos).canSustainPlant(level, belowPos, net.minecraft.core.Direction.UP, state);
        if (!soilDecision.isDefault()) return soilDecision.isTrue();
        if (belowBlockState.is(this))
        {
            return switch (belowBlockState.getValue(HOP_BLOCKSTATE))
            {
                case SEED, TOP_GROW -> false;
                default -> true;
            };
        }
        return this.mayPlaceOn(state, belowBlockState);
    }

    protected boolean mayPlaceOn(BlockState stateToPlace, BlockState stateBelow)
    {
        return stateToPlace.getValue(HOP_BLOCKSTATE) == HopBlockstate.SEED ? stateBelow.is(BlockTags.SUPPORTS_CROPS) : stateBelow.is(BlockTags.SUPPORTS_CROPS) || stateBelow.is(BlockTags.SUPPORTS_VEGETATION);
    }

    public void grow(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        switch (state.getValue(HOP_BLOCKSTATE))
        {
            case SEED -> level.setBlock(pos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.TOP_GROW), 2);
            case MIDDLE_GROW ->
            {
                if (!state.getValue(CAN_GROW))
                {
                    level.setBlock(pos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.MIDDLE_FLOWERED), 2);
                }
            }
            case TOP_GROW ->
            {
                BlockPos abovePos = pos.above();
                if (level.getBlockState(abovePos).isAir())
                {
                    level.setBlock(pos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.MIDDLE_GROW), 2);
                    level.setBlock(abovePos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.TOP_GROW), 2);
                    if (state.getValue(CAN_GROW))
                    {
                        verifyIfCanStillGrow(level, random, abovePos);
                    }
                }
            }
        }
    }

    private void verifyIfCanStillGrow(Level level, RandomSource random, BlockPos pos)
    {
        int hopHeight = this.hopHeight(level, pos);
        if (
                hopHeight > MAX_HEIGHT - 1
                        || (hopHeight > MIN_HEIGHT && random.nextFloat() < 0.30 + (0.075 * hopHeight))
        )
        {
            changeCanGrowStateAndPropagate(level, pos);
        }
    }

    private void changeCanGrowStateAndPropagate(Level level, BlockPos pos)
    {
        BlockState state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(CAN_GROW, false), 2);
        BlockPos belowPos = pos.below();
        if (level.getBlockState(belowPos).is(this))
        {
            changeCanGrowStateAndPropagate(level, belowPos);
        }
    }

    private int hopHeight(Level level, BlockPos pos)
    {
        return hopHeight(level, pos, 1);
    }

    private int hopHeight(Level level, BlockPos pos, int count)
    {
        BlockPos belowPos = pos.below();
        if (level.getBlockState(belowPos).is(this))
        {
            count++;
            return this.hopHeight(level, belowPos, count);
        }
        return count;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        Optional<ResourceKey<LootTable>> optLootTable = getLootTable();
        if (optLootTable.isPresent() && state.getValue(HOP_BLOCKSTATE) == HopBlockstate.MIDDLE_FLOWERED)
        {
            if (level instanceof ServerLevel serverLevel)
            {
                dropHopLootTable(serverLevel, state, pos);
                level.setBlock(pos, state.setValue(HOP_BLOCKSTATE, HopBlockstate.MIDDLE_GROW), 2);
            }
            return InteractionResult.SUCCESS;
        }
        else
        {
            return InteractionResult.PASS;
        }
    }

    public void dropHopLootTable(ServerLevel level, BlockState state, BlockPos pos)
    {
        Optional<ResourceKey<LootTable>> optLootTable = getLootTable();
        optLootTable.ifPresent(lootTableResourceKey -> dropFromLootTable(
                level,
                lootTableResourceKey,
                params -> params.withParameter(LootContextParams.BLOCK_STATE, state)
                        .create(LootContextParamSets.EMPTY),
                (selfServerLevel, itemStack) -> Block.popResource(selfServerLevel, pos, itemStack)
        ));

    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state)
    {
        HopBlockstate hopBlockstate = state.getValue(HOP_BLOCKSTATE);
        return hopBlockstate == HopBlockstate.SEED || hopBlockstate == HopBlockstate.TOP_GROW;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.grow(level, random, pos, state);
    }
}