package com.eevneon.radiationfission.content;

import com.eevneon.radiationfission.RadiationFission;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RNFCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RadiationFission.MOD_ID);

    public static final Supplier<CreativeModeTab> RADIATION_N_FISSION = CREATIVE_MODE_TAB.register("radiation_n_fission",
            () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(RNFBlocks.URANIUM_BLOCK.get()))
                .title(Component.translatable("creativetab.radiationfission.radiation_n_fission "))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(RNFItems.URANIUM_INGOT);
                    output.accept(RNFItems.STEEL_INGOT);
                    output.accept(RNFBlocks.POWERED_SPEED_CONTROLLER);
                    output.accept(RNFBlocks.STEEL_CASING);
                    output.accept(RNFBlocks.STEEL_BLOCK);
                    output.accept(RNFBlocks.URANIUM_BLOCK);
                })
                .build());

    // Rewatch Kaupenjoe's creative mode tab tutorial or ask for help elsewhere in the case of a second tab (Extra step needed)

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
