package com.unpainperdu.houblonneux.level.world.block.block;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.level.world.block.ModBlockStateProperties;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.item.LockerItem;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class BeerDispenserBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.INVISIBLE;
    }

    public static final MapCodec<BeerDispenserBlock> CODEC = simpleCodec(BeerDispenserBlock::new);

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LOCKED = ModBlockStateProperties.LOCKED;

    private static final VoxelShape SHAPE = Block.box(1F, 0F, 1F, 15F, 16F, 15F);

    public BeerDispenserBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(HALF, DoubleBlockHalf.LOWER)
                        .setValue(WATERLOGGED, false)
                        .setValue(LOCKED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(HALF, FACING, WATERLOGGED, LOCKED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        FluidState fluidStateDown = level.getFluidState(blockpos);
        boolean flag = fluidStateDown.getType() == Fluids.WATER;
        if (blockpos.getY() < level.getMaxY() && level.getBlockState(blockpos.above()).canBeReplaced(context))
        {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, flag).setValue(FACING, context.getHorizontalDirection());
        }
        else
        {
            return null;
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        FluidState fluidStateUp = level.getFluidState(pos.above());
        boolean flag = fluidStateUp.getType() == Fluids.WATER;
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, flag), 3);
    }

    @Override
    protected BlockState updateShape(BlockState selfState, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos selfPos, Direction direction, BlockPos neighbourPos, BlockState neighbourState, RandomSource rand)
    {
        DoubleBlockHalf doubleblockhalf = selfState.getValue(HALF);
        if (selfState.getValue(WATERLOGGED))
        {
            scheduledTickAccess.createTick(selfPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (direction.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (direction == Direction.UP))
        {
            return neighbourState.is(this) && neighbourState.getValue(HALF) != doubleblockhalf
                    ? selfState.setValue(FACING, neighbourState.getValue(FACING))
                    : Blocks.AIR.defaultBlockState();
        }
        else
        {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !selfState.canSurvive(level, selfPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(selfState, level, scheduledTickAccess, selfPos, direction, neighbourPos, neighbourState, rand);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (!level.isClientSide() && (player.isCreative() || !player.hasCorrectToolForDrops(state, level, pos)))
        {
            preventDropFromBottomPart(level, pos, state, player);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER)
        {
            return new BeerDispenserBlockEntity(blockPos, blockState);
        }
        return null;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
    {
        return (MenuProvider) this.getBlockEntity(level, pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        if (!level.isClientSide() && player instanceof ServerPlayer)
        {
            if (player.isShiftKeyDown())
            {
                BlockEntity blockEntity = this.getBlockEntity(level, pos, state);
                if (blockEntity instanceof BeerDispenserBlockEntity)
                {
                    player.openMenu((MenuProvider) blockEntity);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer)
        {
            BlockEntity blockEntity = this.getBlockEntity(level, pos, state);
            if (blockEntity instanceof BeerDispenserBlockEntity beerDispenserBE)
            {
                boolean isLocked = state.getValue(LOCKED);
                if (itemStack.getItem() instanceof LockerItem)
                {
                    lockBLock(state, level, pos, !isLocked);
                    return InteractionResult.SUCCESS;
                }
                else
                {
                    if (!isLocked)
                    {
                        DispenserTrade dispenserTrade = DispenserTrade.getTradeFromCost(level, itemStack);
                        if (dispenserTrade != null)
                        {
                            beerDispenserBE.setTrade(dispenserTrade);
                            lockBLock(state, level, pos, true);
                            level.sendBlockUpdated(pos, state, state, 3);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    DispenserTrade dispenserTrade = beerDispenserBE.getTrade();
                    if (dispenserTrade != null)
                    {
                        if (dispenserTrade.buy(itemStack, !player.isCreative()))
                        {
                            beerDispenserBE.trade(serverPlayer, level);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                }
            }
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    public BlockEntity getBlockEntity(Level level, BlockPos pos, BlockState state)
    {
        if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER)
        {
            return level.getBlockEntity(pos);
        }
        else
        {
            return level.getBlockEntity(pos.below());
        }
    }

    public static void preventDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player)
    {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER)
        {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER)
            {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockpos, blockstate1, 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public void lockBLock(BlockState state, Level level, BlockPos pos, boolean isLocked)
    {
        level.setBlockAndUpdate(pos, state.setValue(LOCKED, isLocked));
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            BlockState belowState = level.getBlockState(pos.below());
            level.setBlockAndUpdate(pos.below(), belowState.setValue(LOCKED, isLocked));
        }
        else
        {
            BlockState upperState = level.getBlockState(pos.above());
            level.setBlockAndUpdate(pos.above(), upperState.setValue(LOCKED, isLocked));
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        if (!level.isClientSide())
        {
            return createTickerHelper(type, ModBlockEntityRegister.BEER_DISPENSER.get(), (serverLevel, pos, state, blockEntity) -> BeerDispenserBlockEntity.tick((ServerLevel) serverLevel, pos, state, blockEntity));
        }
        return super.getTicker(level, blockState, type);
    }


}