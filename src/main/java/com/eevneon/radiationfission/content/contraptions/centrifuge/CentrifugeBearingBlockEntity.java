package com.eevneon.radiationfission.content.contraptions.centrifuge;

import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
// import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CentrifugeBearingBlockEntity extends MechanicalBearingBlockEntity {

    protected ScrollOptionBehaviour<CentrifugeBearingBlockEntity.RotationDirection> movementDirection;

    protected float lastDiffusionModifier;

    protected boolean queuedReassembly;
    protected boolean running;
    public boolean assembleNextTick;

    public CentrifugeBearingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        boolean cancelAssembly = assembleNextTick;
        super.onSpeedChanged(prevSpeed);
        assembleNextTick = cancelAssembly;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide())
            return;
        if (!queuedReassembly)
            return;
        queuedReassembly = false;
        if (!running)
            assembleNextTick = true;
    }

    public void disassembleForMovement() {
        if (!running)
            return;
        disassemble();
        queuedReassembly = true;
    }

    @Override
    protected boolean isWindmill() {
        return false;
    }

    protected float getAngleSpeedDirection() {
        WindmillBearingBlockEntity.RotationDirection rotationDirection = WindmillBearingBlockEntity.RotationDirection.values()[movementDirection.getValue()];
        return (rotationDirection == WindmillBearingBlockEntity.RotationDirection.CLOCKWISE ? 1 : -1);
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putBoolean("QueueAssembly", queuedReassembly);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        if (!wasMoved) {
            queuedReassembly = compound.getBoolean("QueueAssembly");
            super.read(compound, registries, clientPacket);
        }
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        behaviours.remove(movementMode);
        movementDirection = new ScrollOptionBehaviour
                //<>
                (WindmillBearingBlockEntity.RotationDirection.class,
                CreateLang.translateDirect("contraptions.centrifuge.rotation_direction"), this, getMovementModeSlot());
        movementDirection.withCallback($ -> onDirectionChanged());
        behaviours.add(movementDirection);
        // registerAwardables(behaviours, AllAdvancements.WINDMILL, AllAdvancements.WINDMILL_MAXED);
    }

    private void onDirectionChanged() {
        if (!running)
            return;
        if (!level.isClientSide)
            updateGeneratedRotation();
    }

    @Override
    public boolean isWoodenTop() {
        return false;
    }

    public static enum RotationDirection implements INamedIconOptions {

        CLOCKWISE(AllIcons.I_REFRESH), COUNTER_CLOCKWISE(AllIcons.I_ROTATE_CCW),

        ;

        private String translationKey;
        private AllIcons icon;

        RotationDirection(AllIcons icon) {
            this.icon = icon;
            translationKey = "radiationfission.generic." + Lang.asId(name());
        }

        @Override
        public AllIcons getIcon() {
            return null;
        }

        @Override
        public String getTranslationKey() {
            return "";
        }
    }
}

