package com.eevneon.radiationfission.item;

import com.eevneon.radiationfission.RadiationFission;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RadiationFission.MOD_ID);

    public static final Supplier<CreativeModeTab> RADIATION_N_FISSION = CREATIVE_MODE_TAB.register("radiation_n_fission",
            () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(Items.URANIUM_INGOT.get()))
                .title(Component.translatable("creativetab.radiationfission.uranium_ingot"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(Items.URANIUM_INGOT);
                    output.accept(Items.STEEL_INGOT);
                })
                .build());

    // Rewatch Kaupenjoe's creative mode tab tutorial or ask for help elsewhere in the case of a second tab (Extra step needed)

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
