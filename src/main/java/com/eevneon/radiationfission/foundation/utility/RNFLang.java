package com.eevneon.radiationfission.foundation.utility;

import com.eevneon.radiationfission.RadiationFission;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class RNFLang extends Lang {
    public static LangBuilder builder() {
        return new LangBuilder(RadiationFission.MOD_ID);
    }

    public static LangBuilder blockName(BlockState state) {
        return builder().add(state.getBlock().getName());
    }
    public static LangBuilder itemName(ItemStack stack) {
        return builder().add(stack.getHoverName().copy());
    }
    public static LangBuilder fluidName(FluidStack stack) {
        return builder().add(stack.getHoverName().copy());
    }

    public static LangBuilder number(double value) {
        return builder().text(LangNumberFormat.format(value));
    }
    public static LangBuilder translate(String key, Object... args) {
        return builder().translate(key, args);
    }
    public static LangBuilder text(String text) {
        return builder().text(text);
    }
}
