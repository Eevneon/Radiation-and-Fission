package com.eevneon.radiationfission.infrastructure.ponder;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.content.RNFBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class RNFPonderTags {

    private static ResourceLocation loc(String id) {
        return RadiationFission.asResource(id);
    }

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(RegisteredObjectsHelper::getKeyOrThrow);

        HELPER.addToTag(AllCreatePonderTags.KINETIC_SOURCES)
                .add(RNFBlocks.RT_GENERATOR);
    }
}
