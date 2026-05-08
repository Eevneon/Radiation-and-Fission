package com.eevneon.radiationfission.infrastructure.ponder;

import com.eevneon.radiationfission.content.RNFBlocks;
import com.eevneon.radiationfission.infrastructure.ponder.scenes.RNFKineticsScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class RNFPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(RNFBlocks.RT_GENERATOR)
                .addStoryBoard("rt_generator", RNFKineticsScenes::rtg);
    }
}
