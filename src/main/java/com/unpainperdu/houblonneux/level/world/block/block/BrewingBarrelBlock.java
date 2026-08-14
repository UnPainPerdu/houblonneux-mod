package com.unpainperdu.houblonneux.level.world.block.block;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.level.world.block.blockstate.ModBlockStateProperties;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import com.unpainperdu.houblonneux.util.pos.DirectionalPosGetter;
import com.unpainperdu.houblonneux.util.pos.PosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class BrewingBarrelBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, WorldlyContainerHolder
{
    public static final IntegerProperty POSITION = ModBlockStateProperties.BREWING_BARREL_POSITION;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final MapCodec<BrewingBarrelBlock> CODEC = simpleCodec(BrewingBarrelBlock::new);

    public BrewingBarrelBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POSITION, 0)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(POSITION, FACING, WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        FluidState fluidStateDown = level.getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();
        boolean flag = fluidStateDown.getType() == Fluids.WATER;
        if (blockpos.getY() < level.getMaxY() && getAllMultiBlockPosFromMasterPos(blockpos, direction).stream().allMatch(pos -> level.getBlockState(pos).canBeReplaced(context)))
        {
            return this.defaultBlockState().setValue(POSITION, 0).setValue(WATERLOGGED, flag).setValue(FACING, direction);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity by, ItemStack itemStack)
    {
        Direction direction = state.getValue(FACING);
        List<BlockPos> poss = getAllMultiBlockPosFromMasterPos(pos, direction);
        for (int i = 1; i < poss.size(); i++)
        {
            BlockPos currentPos = poss.get(i);
            FluidState fluidStateUp = level.getFluidState(currentPos);
            boolean flag = fluidStateUp.getType() == Fluids.WATER;
            level.setBlock(currentPos, state.setValue(POSITION, i).setValue(WATERLOGGED, flag), 3);
        }
    }

    @Override
    protected BlockState updateShape(BlockState selfState, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos selfPos, Direction direction, BlockPos neighbourPos, BlockState neighbourState, RandomSource rand)
    {
        if (selfState.getValue(WATERLOGGED))
        {
            scheduledTickAccess.createTick(selfPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        BlockPos masterPos = getMasterPos(selfState, selfPos);
        Direction selfDirection = selfState.getValue(FACING);
        List<BlockPos> selfPoss = getAllMultiBlockPosFromMasterPos(masterPos, selfDirection);
        int position = selfPoss.indexOf(neighbourPos);
        if (position != -1) //neighbor is in multiblock poss
        {
            if (!neighbourState.is(this))
            {
                return Blocks.AIR.defaultBlockState();
            }
            else if (position != neighbourState.getValue(POSITION))
            {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(selfState, level, scheduledTickAccess, selfPos, direction, neighbourPos, neighbourState, rand);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (state.getValue(POSITION) == 0)
        {
            if (!level.isClientSide() && player.preventsBlockDrops())
            {
                BlockEntity blockEntity = this.getBlockEntity(level, pos, state);
                if (blockEntity instanceof BrewingBarrelBlockEntity)
                {
                    ItemStack itemStack = new ItemStack(state.getBlock());
                    itemStack.applyComponents(blockEntity.collectComponents());
                    ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
                    entity.setDefaultPickUpDelay();
                    level.addFreshEntity(entity);
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * @return master pos, the one with blockstate POSITION = 0
     */
    private BlockPos getMasterPos(BlockState state, BlockPos pos)
    {
        int currentPosition = state.getValue(POSITION);
        DirectionalPosGetter dpg = PosHelper.getDirectionalPosGetter(state.getValue(FACING), pos);
        switch (currentPosition)
        {
            case 1 ->
            {
                return dpg.getBelow().getBlockPos();
            }
            case 2 ->
            {
                return dpg.getLeft().getBlockPos();
            }
            case 3 ->
            {
                return dpg.getLeft().getBelow().getBlockPos();
            }
            case 4 ->
            {
                return dpg.getFront().getBlockPos();
            }
            case 5 ->
            {
                return dpg.getFront().getBelow().getBlockPos();
            }
            case 6 ->
            {
                return dpg.getFront().getLeft().getBlockPos();
            }
            case 7 ->
            {
                return dpg.getFront().getBelow().getLeft().getBlockPos();
            }
            default ->
            {
                return dpg.getBlockPos();
            }
        }
    }

    /**
     * @param masterPos pos on front left bottom of 2x2x2 (0,0,0) with POSITION = 0
     */
    private List<BlockPos> getAllMultiBlockPosFromMasterPos(BlockPos masterPos, Direction direction)
    {
        DirectionalPosGetter dpg = PosHelper.getDirectionalPosGetter(direction, masterPos);
        return List.of(
                dpg.getBlockPos(), // 0 (IntegerProperty POSITION)
                dpg.getAbove().getBlockPos(), // 1
                dpg.getRight().getBlockPos(), // 2
                dpg.getRight().getAbove().getBlockPos(), // 3
                dpg.getBehind().getBlockPos(), // 4
                dpg.getBehind().getAbove().getBlockPos(), // 5
                dpg.getBehind().getRight().getBlockPos(), // 6
                dpg.getBehind().getRight().getAbove().getBlockPos() // 7
        );
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState)
    {
        if (blockState.getValue(POSITION) == 0)
        {
            return new BrewingBarrelBlockEntity(worldPosition, blockState);
        }
        return null;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
    {
        BlockEntity blockEntity = this.getBlockEntity(level, pos, state);
        return blockEntity instanceof MenuProvider ? (MenuProvider) blockEntity : null;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer)
        {
            BlockEntity blockEntity = this.getBlockEntity(level, pos, state);
            if (blockEntity instanceof BrewingBarrelBlockEntity brewingBarrelBlockEntity && brewingBarrelBlockEntity.handlePumping(itemStack, serverPlayer))
            {
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        if (!level.isClientSide() && player instanceof ServerPlayer)
        {
            BlockEntity blockEntity = this.getBlockEntity(level, pos, state);
            if (blockEntity instanceof BrewingBarrelBlockEntity)
            {
                player.openMenu((MenuProvider) blockEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public @Nullable BlockEntity getBlockEntity(LevelAccessor level, BlockPos pos, BlockState state)
    {
        return level.getBlockEntity(getMasterPos(state, pos));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        if (!level.isClientSide())
        {
            return createTickerHelper(type, ModBlockEntityRegister.BREWING_BARREL.get(), (serverLevel, pos, state, blockEntity) -> BrewingBarrelBlockEntity.tick((ServerLevel) serverLevel, pos, state, blockEntity));
        }
        return super.getTicker(level, blockState, type);
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos)
    {
        return (WorldlyContainer) this.getBlockEntity(level, pos, state);
    }
}