package com.eevneon.radiationfission.content;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.content.contraptions.centrifuge.CentrifugeBearingBlockEntity;
import com.eevneon.radiationfission.content.rtg.RTGeneratorBlockEntity;
import com.eevneon.radiationfission.content.rtg.RTGeneratorRenderer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.bearing.BearingRenderer;
import com.simibubi.create.content.contraptions.bearing.BearingVisual;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class RNFBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = RadiationFission.REGISTRATE;

    public static final BlockEntityEntry<CentrifugeBearingBlockEntity> CENTRIFUGE_BEARING = REGISTRATE
            .blockEntity("centrifuge_bearing", CentrifugeBearingBlockEntity::new)
            .visual(() -> BearingVisual::new)
            .validBlocks(RNFBlocks.CENTRIFUGE_BEARING)
            .renderer(() -> BearingRenderer::new)
            .register();

    public static final BlockEntityEntry<RTGeneratorBlockEntity> RT_GENERATOR = REGISTRATE
            .blockEntity("rt_generator", RTGeneratorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), false)
            .validBlocks(RNFBlocks.RT_GENERATOR)
            .renderer(() -> RTGeneratorRenderer::new)
            .register();

    public static void register() {
    }
}
