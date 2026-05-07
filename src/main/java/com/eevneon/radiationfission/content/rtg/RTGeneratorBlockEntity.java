package com.eevneon.radiationfission.content.rtg;

import com.eevneon.radiationfission.content.RNFDataComponents;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RTGeneratorBlockEntity extends GeneratingKineticBlockEntity {
    public static final int TICKS_PER_MINUTE = 20 * 60;

    protected ScrollOptionBehaviour<WindmillBearingBlockEntity.RotationDirection> movementDirection;
    protected float lastGeneratedSpeed;

    public ItemStack fuel = ItemStack.EMPTY;

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
        ItemRTGFuelPropertiesData data = this.fuel.getItemHolder().getData(ItemRTGFuelPropertiesData.TYPE);
        return data != null ? data.heat() * 16.0F * this.getAngleSpeedDirection() : 0.0F;
    }

    protected float getAngleSpeedDirection() {
        WindmillBearingBlockEntity.RotationDirection direction =
                WindmillBearingBlockEntity.RotationDirection.values()[this.movementDirection.getValue()];
        return (direction == WindmillBearingBlockEntity.RotationDirection.CLOCKWISE ? 1 : -1);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);

        compound.put("Fuel", this.fuel.saveOptional(registries));
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);

        this.fuel = ItemStack.parseOptional(registries, compound.getCompound("Fuel"));
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

    @Override
    public void tick() {
        super.tick();

        if (this.fuel.isEmpty()) {
            if (this.lastGeneratedSpeed != 0.0F) {
                this.updateGeneratedRotation();
                this.sendData();
            }
            this.lastGeneratedSpeed = 0.0F;
            return;
        }
        ItemRTGFuelPropertiesData data = this.fuel.getItemHolder().getData(ItemRTGFuelPropertiesData.TYPE);
        if (data == null) {
            if (this.lastGeneratedSpeed != 0.0F) {
                this.updateGeneratedRotation();
                this.sendData();
            }
            this.lastGeneratedSpeed = 0.0F;
            return;
        }
        if (this.lastGeneratedSpeed == 0.0F) {
            this.updateGeneratedRotation();
            this.sendData();
        }
        int max = Mth.floor(data.duration() * TICKS_PER_MINUTE);
        Integer depletionNullable = this.fuel.get(RNFDataComponents.DEPLETION_TICKS);
        int depletion = depletionNullable == null ? 0 : depletionNullable;
        depletion++;
        if (depletion >= max) {
            if (data.result() == Items.AIR) {
                this.fuel = ItemStack.EMPTY;
            } else {
                this.fuel = new ItemStack(data.result(), 1);
            }
            this.sendData();
        } else {
            this.fuel.set(RNFDataComponents.DEPLETION_TICKS, depletion);
        }
        this.setChanged();
    }
}
