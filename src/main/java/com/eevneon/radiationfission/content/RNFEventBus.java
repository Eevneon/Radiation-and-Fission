package com.eevneon.radiationfission.content;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.content.rtg.ItemRTGFuelPropertiesData;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = RadiationFission.MOD_ID)
public class RNFEventBus {
    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(ItemRTGFuelPropertiesData.TYPE);
    }

    @SubscribeEvent
    public static void addTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Holder<Item> holder = stack.getItemHolder();

        ItemRTGFuelPropertiesData.handleTooltips(holder, event);
    }
}
