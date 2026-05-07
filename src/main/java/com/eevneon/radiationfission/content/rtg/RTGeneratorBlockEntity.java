package com.eevneon.radiationfission.content.rtg;

import com.simibubi.create.content.contraptions.DirectionalExtenderScrollOptionSlot;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RTGeneratorBlockEntity extends GeneratingKineticBlockEntity {
    protected ScrollOptionBehaviour<WindmillBearingBlockEntity.RotationDirection> movementDirection;
    protected float lastGeneratedSpeed;

    public RTGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void updateGeneratedRotation() {
        super.updateGeneratedRotation();
        this.lastGeneratedSpeed = this.getGeneratedSpeed();
    }

    @Override
    public float getGeneratedSpeed() {
        return 16.0F * this.getAngleSpeedDirection();
    }

    protected float getAngleSpeedDirection() {
        WindmillBearingBlockEntity.RotationDirection direction =
                WindmillBearingBlockEntity.RotationDirection.values()[this.movementDirection.getValue()];
        return (direction == WindmillBearingBlockEntity.RotationDirection.CLOCKWISE ? 1 : -1);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        this.movementDirection = new ScrollOptionBehaviour<>(WindmillBearingBlockEntity.RotationDirection.class,
                CreateLang.translateDirect("contraptions.windmill.rotation_direction"), this, this.getMovementModeSlot());
        this.movementDirection.withCallback($ -> this.onDirectionChanged());
        behaviours.add(this.movementDirection);
    }

    private ValueBoxTransform getMovementModeSlot() {
        return new CenteredSideValueBoxTransform(
                (state, side) ->
                        side == state.getValue(RTGeneratorBlock.FACING).getOpposite()
        );
    }

    private void onDirectionChanged() {
        if (!this.level.isClientSide) this.updateGeneratedRotation();
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!this.hasSource() || this.getGeneratedSpeed() > this.getTheoreticalSpeed())
            this.updateGeneratedRotation();
    }
}
