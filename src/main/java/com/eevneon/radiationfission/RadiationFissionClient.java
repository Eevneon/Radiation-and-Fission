package com.eevneon.radiationfission;

import com.eevneon.radiationfission.foundation.ponder.RNFPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = RadiationFission.MOD_ID, dist = Dist.CLIENT)
public class RadiationFissionClient {
    public RadiationFissionClient(IEventBus modEventBus) {
        modEventBus.addListener(RadiationFissionClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new RNFPonderPlugin());
    }
}
