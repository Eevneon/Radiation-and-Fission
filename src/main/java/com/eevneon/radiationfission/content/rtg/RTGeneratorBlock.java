package com.eevneon.radiationfission.content.rtg;

import com.eevneon.radiationfission.content.RNFBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class RTGeneratorBlock extends DirectionalKineticBlock implements IBE<RTGeneratorBlockEntity> {
    public RTGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return state.getValue(FACING) == face;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferred = getPreferredFacing(context);
        if (preferred == null || (context.getPlayer() != null && context.getPlayer()
                .isShiftKeyDown())) {
            Direction nearestLookingDirection = context.getNearestLookingDirection();
            return defaultBlockState().setValue(FACING, context.getPlayer() != null && context.getPlayer()
                    .isShiftKeyDown() ? nearestLookingDirection.getOpposite() : nearestLookingDirection);
        }
        return defaultBlockState().setValue(FACING, preferred);
    }

    @Override
    public Class<RTGeneratorBlockEntity> getBlockEntityClass() {
        return RTGeneratorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RTGeneratorBlockEntity> getBlockEntityType() {
        return RNFBlockEntityTypes.RT_GENERATOR.get();
    }

    @Override
    public boolean hideStressImpact() {
        return true;
    }

    @Override
    public boolean showCapacityWithAnnotation() {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
