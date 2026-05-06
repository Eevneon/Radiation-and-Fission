package com.eevneon.radiationfission.content;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.content.contraptions.centrifuge.CentrifugeBearingBlockEntity;
import com.simibubi.create.content.contraptions.bearing.BearingRenderer;
import com.simibubi.create.content.contraptions.bearing.BearingVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class RadiationFissionBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = RadiationFission.REGISTRATE;

    public static final BlockEntityEntry<CentrifugeBearingBlockEntity> CENTRIFUGE_BEARING = REGISTRATE
            .blockEntity("centrifuge_bearing", CentrifugeBearingBlockEntity::new)
            .visual(() -> BearingVisual::new)
            .validBlocks(Blocks.CENTRIFUGE_BEARING)
            .renderer(() -> BearingRenderer::new)
            .register();
}
