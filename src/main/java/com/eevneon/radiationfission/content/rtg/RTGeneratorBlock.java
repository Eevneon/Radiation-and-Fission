package com.eevneon.radiationfission.content.rtg;

import com.eevneon.radiationfission.content.RNFBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(FACING) == hitResult.getDirection()) return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        Holder<Item> holder = stack.getItemHolder();
        if (holder.getData(ItemRTGFuelPropertiesData.TYPE) != null) {
            return this.onBlockEntityUseItemOn(level, pos, te -> {
                if (!te.fuel.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                if (!level.isClientSide) {
                    te.fuel = stack.split(1);
                    te.updateGeneratedRotation();
                    te.sendData();
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            });
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return this.onBlockEntityUse(level, pos, te -> {
            if (te.fuel.isEmpty()) return InteractionResult.PASS;
            if (!level.isClientSide) {
                player.addItem(te.fuel.copy());
                te.fuel = ItemStack.EMPTY;
                te.updateGeneratedRotation();
                te.sendData();
            }
            return InteractionResult.SUCCESS;
        });
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
