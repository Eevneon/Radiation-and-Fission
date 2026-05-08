package com.eevneon.radiationfission.foundation.ponder;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.infrastructure.ponder.RNFPonderScenes;
import com.eevneon.radiationfission.infrastructure.ponder.RNFPonderTags;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class RNFPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return RadiationFission.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        RNFPonderScenes.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        RNFPonderTags.register(helper);
    }
}
