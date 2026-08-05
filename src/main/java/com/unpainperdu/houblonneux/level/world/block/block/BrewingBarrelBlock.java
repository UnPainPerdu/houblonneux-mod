package com.unpainperdu.houblonneux.level.world.block.block;

import com.unpainperdu.houblonneux.level.world.block.blockstate.ModBlockStateProperties;
import com.unpainperdu.houblonneux.util.pos.DirectionalPosGetter;
import com.unpainperdu.houblonneux.util.pos.PosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class BrewingBarrelBlock extends Block
{
    public static final IntegerProperty POSITION = ModBlockStateProperties.BREWING_BARREL_POSITION;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

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
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random)
    {
        //TODO handle water + world destruction
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        //TODO handle player destruction here too
        return super.playerWillDestroy(level, pos, state, player);
    }

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
}