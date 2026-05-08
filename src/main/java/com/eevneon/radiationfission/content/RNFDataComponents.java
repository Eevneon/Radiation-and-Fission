package com.eevneon.radiationfission.content;

import com.eevneon.radiationfission.RadiationFission;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RNFDataComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, RadiationFission.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DEPLETION_TICKS = REGISTER.registerComponentType(
            "depletion_ticks",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
    );

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
