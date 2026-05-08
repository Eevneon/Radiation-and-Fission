package com.eevneon.radiationfission.content.rtg;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.content.RNFDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.text.DecimalFormat;
import java.util.List;

// Heat is RPM multiplier, duration is in minutes
public record ItemRTGFuelPropertiesData(float heat, float duration, Item result) {
    public static final Codec<ItemRTGFuelPropertiesData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.FLOAT.fieldOf("heat")
                    .orElse(1.0F)
                    .validate((value) -> {
                        if (value > 0.0F) return DataResult.success(value);
                        else return DataResult.error(() -> "RTG pellet heat multiplier must be positive");
                    })
                    .forGetter(ItemRTGFuelPropertiesData::heat),
            Codec.FLOAT.fieldOf("duration")
                    .validate((value) -> {
                        if (value > 0.0F) return DataResult.success(value);
                        else return DataResult.error(() -> "RTG pellet duration must be positive");
                    })
                    .forGetter(ItemRTGFuelPropertiesData::duration),
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("result")
                    .orElse(Items.AIR).forGetter(ItemRTGFuelPropertiesData::result)
    ).apply(inst, ItemRTGFuelPropertiesData::new));
    public static final DataMapType<Item, ItemRTGFuelPropertiesData> TYPE = DataMapType.builder(
            RadiationFission.asResource("rtg_fuel_properties"),
            Registries.ITEM,
            CODEC
    ).synced(CODEC, true).build();

    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");
    private static final Component TOOLTIP_1 = Component.translatable("tooltip.radiationfission.rtg_fuel_properties.header")
            .withStyle(ChatFormatting.GOLD);
    private static final Component TOOLTIP_2 = Component.translatable("tooltip.radiationfission.rtg_fuel_properties.duration_single")
            .withStyle(ChatFormatting.GRAY);

    public static void handleTooltips(ItemStack stack, Holder<Item> holder, ItemTooltipEvent event) {
        ItemRTGFuelPropertiesData data = holder.getData(TYPE);
        if (data != null) {
            List<Component> tooltip = event.getToolTip();
            tooltip.add(TOOLTIP_1);
            tooltip.add(Component.literal("  ").append(
                    Component.translatable("tooltip.radiationfission.rtg_fuel_properties.heat",
                                    FORMAT.format(data.heat()))
                            .withStyle(ChatFormatting.GRAY)
            ));
            if (data.duration() != 1.0F) {
                tooltip.add(Component.literal("  ").append(
                        Component.translatable("tooltip.radiationfission.rtg_fuel_properties.duration",
                                        FORMAT.format(data.duration()))
                                .withStyle(ChatFormatting.GRAY)
                ));
            } else {
                tooltip.add(Component.literal("  ").append(TOOLTIP_2));
            }
            if (data.result() != Items.AIR) {
                tooltip.add(Component.literal("  ").append(
                        Component.translatable("tooltip.radiationfission.rtg_fuel_properties.result",
                                        data.result().getName(ItemStack.EMPTY).getString())
                                .withStyle(ChatFormatting.GRAY)
                ));
            }
            Integer depletionTicks = stack.get(RNFDataComponents.DEPLETION_TICKS);
            if (depletionTicks != null) {
                float pct = depletionTicks * 100.0F / (data.duration() * 20 * 60);
                tooltip.add(Component.literal("  ").append(
                        Component.translatable("radiationfission.goggles.rt_generator.decay", FORMAT.format(pct))
                                .withStyle(ChatFormatting.GRAY)
                ));
            }
        }
    }
}
