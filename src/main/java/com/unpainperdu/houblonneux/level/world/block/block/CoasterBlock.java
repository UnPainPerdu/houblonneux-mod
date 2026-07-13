package com.unpainperdu.houblonneux.level.world.block.block;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.datagen.data.tag.ModItemTags;
import com.unpainperdu.houblonneux.level.world.block.ModBlockStateProperties;
import com.unpainperdu.houblonneux.level.world.block.entity.CoasterBlockEntity;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CoasterBlock extends BaseEntityBlock
{
    public static final MapCodec<CoasterBlock> CODEC = simpleCodec(CoasterBlock::new);

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty COASTER_NUMBER = ModBlockStateProperties.COASTER_NUMBER;
    private static final VoxelShape COASTER_SHAPE_1 = Block.box(2F, 0F, 2F, 7F, 1F, 7F);
    private static final VoxelShape COASTER_SHAPE_2 = Block.box(9F, 0F, 2F, 14F, 1F, 7F);
    private static final VoxelShape COASTER_SHAPE_3 = Block.box(2F, 0F, 9F, 7F, 1F, 14F);
    private static final VoxelShape COASTER_SHAPE_4 = Block.box(9F, 0F, 9F, 14F, 1F, 14F);

    public CoasterBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(COASTER_NUMBER, 1)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, COASTER_NUMBER);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxY() && level.getBlockState(blockpos.above()).canBeReplaced(context))
        {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        }
        else
        {
            return null;
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        var finalShape = COASTER_SHAPE_1;
        int coasterNumber = state.getValue(COASTER_NUMBER);
        if (coasterNumber > 1)
        {
            finalShape = Shapes.join(finalShape, COASTER_SHAPE_2, BooleanOp.OR);
        }
        if (coasterNumber > 2)
        {
            finalShape = Shapes.join(finalShape, COASTER_SHAPE_3, BooleanOp.OR);
        }
        if (coasterNumber > 3)
        {
            finalShape = Shapes.join(finalShape, COASTER_SHAPE_4, BooleanOp.OR);
        }
        return Shapes.rotateAll(finalShape).get(state.getValue(FACING));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random)
    {
        return (directionToNeighbour == Direction.DOWN && !state.canSurvive(level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (itemStack.is(ModBlockRegister.COASTER.asItem()))
        {
            if (state.getValue(COASTER_NUMBER) < 4)
            {
                placeCoaster(level, pos, state, player, itemStack);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        else if (itemStack.is(ModItemTags.COASTER_POSABLE))
        {
            CoasterBlockEntity coasterBlockEntity = getBlockEntity(pos, level);
            if (coasterBlockEntity != null)
            {
                if (!level.isClientSide())
                {
                    if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_1))
                    {
                        coasterBlockEntity.tryPlaceItem(level, itemStack, 0);
                    }
                    else if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_2))
                    {
                        coasterBlockEntity.tryPlaceItem(level, itemStack, 1);
                    }
                    else if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_3))
                    {
                        coasterBlockEntity.tryPlaceItem(level, itemStack, 2);
                    }
                    else if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_4))
                    {
                        coasterBlockEntity.tryPlaceItem(level, itemStack, 3);
                    }
                }
                return InteractionResult.CONSUME;
            }
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {

        if (player.isShiftKeyDown())
        {
            takeCoaster(state, level, pos, player);
        }
        else
        {
            CoasterBlockEntity coasterBlockEntity = getBlockEntity(pos, level);
            if (coasterBlockEntity != null)
            {
                if (!level.isClientSide())
                {
                    if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_1))
                    {
                        coasterBlockEntity.tryTakeItem(level, player, 0);
                    }
                    else if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_2))
                    {
                        coasterBlockEntity.tryTakeItem(level, player, 1);
                    }
                    else if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_3))
                    {
                        coasterBlockEntity.tryTakeItem(level, player, 2);
                    }
                    else if (isHitInOrOnShape(hitResult, state.getValue(FACING), COASTER_SHAPE_4))
                    {
                        coasterBlockEntity.tryTakeItem(level, player, 3);
                    }
                }
                return InteractionResult.CONSUME;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    public void placeCoaster(Level level, BlockPos pos, BlockState state, Player player, ItemStack itemStack)
    {
        level.setBlockAndUpdate(pos, state.setValue(COASTER_NUMBER, state.getValue(COASTER_NUMBER) + 1));
        SoundType soundType = state.getSoundType(level, pos, player);
        level.playSound(
                player, pos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F
        );
        if (!player.isCreative())
        {
            itemStack.shrink(1);
        }
    }

    public static boolean isHitInOrOnShape(BlockHitResult hitResult, Direction direction, VoxelShape shape)
    {
        BlockPos hitBlockPos = hitResult.getBlockPos();
        Vec3 hitLocation = hitResult.getLocation();
        // only in north
        double localX = hitLocation.x - hitBlockPos.getX();
        double localY = hitLocation.y - hitBlockPos.getY();
        double localZ = hitLocation.z - hitBlockPos.getZ();

        //relative to direction
        double finalLocalX;
        double finalLocalZ;
        switch (direction)
        {
            case SOUTH ->
            {
                finalLocalX = 1 - localX;
                finalLocalZ = 1 - localZ;
            }
            case EAST ->
            {
                finalLocalX = localZ;
                finalLocalZ = 1 - localX;
            }
            case WEST ->
            {
                finalLocalX = 1 - localZ;
                finalLocalZ = localX;
            }
            default ->
            {
                finalLocalX = localX;
                finalLocalZ = localZ;
            }
        }

        Vec3 localHit = new Vec3(finalLocalX, localY, finalLocalZ);

        List<AABB> AABBs = shape.toAabbs();
        return AABBs.stream().anyMatch(aabb -> (localHit.x >= aabb.minX && localHit.x <= aabb.maxX) && (localHit.y >= aabb.minY && localHit.y <= aabb.maxY) && (localHit.z >= aabb.minZ && localHit.z <= aabb.maxZ));
    }

    private void takeCoaster(BlockState state, Level level, BlockPos pos, Player player)
    {
        int coasterNumber = state.getValue(COASTER_NUMBER);
        if (!level.isClientSide())
        {
            CoasterBlockEntity coasterBlockEntity = getBlockEntity(pos, level);
            if (coasterBlockEntity != null)
            {
                addItemStackToPlayer((ServerLevel) level, player, new ItemStack(ModBlockRegister.COASTER));
                if (coasterNumber == 1)
                {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
                else
                {
                    level.setBlockAndUpdate(pos, state.setValue(COASTER_NUMBER, coasterNumber - 1));
                }
                coasterBlockEntity.tryTakeItem(level, player, coasterNumber - 1);
            }
        }
    }

    private void addItemStackToPlayer(ServerLevel level, Player player, ItemStack itemStack)
    {
        if (!player.addItem(itemStack))
        {
            level.addFreshEntity(new ItemEntity(level, player.getOnPos().getX(), player.getOnPos().getY(), player.getOnPos().getZ(), itemStack));
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    public @Nullable CoasterBlockEntity getBlockEntity(BlockPos blockPos, Level level)
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof CoasterBlockEntity cbe)
        {
            return cbe;
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new CoasterBlockEntity(blockPos, blockState);
    }
}