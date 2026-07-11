package com.unpainperdu.houblonneux.level.world.block.block;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.level.world.block.ModBlockStateProperties;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

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
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (itemStack.is(ModBlockRegister.COASTER.asItem()) && state.getValue(COASTER_NUMBER) < 4)
        {
            level.setBlockAndUpdate(pos, state.setValue(COASTER_NUMBER, state.getValue(COASTER_NUMBER) + 1));
            if (!player.isCreative())
            {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random)
    {
        return (directionToNeighbour == Direction.DOWN && !state.canSurvive(level, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return null;
    }
}