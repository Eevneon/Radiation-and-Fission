package com.eevneon.radiationfission.foundation;

import com.eevneon.radiationfission.RadiationFission;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RNFBuilderTransformers {
    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> bearing(String prefix,
                                                                                        String backTexture) {
        ResourceLocation baseBlockModelLocation = Create.asResource("block/bearing/block");
        ResourceLocation baseItemModelLocation = Create.asResource("block/bearing/item");
        ResourceLocation topTextureLocation = Create.asResource("block/bearing_top");
        ResourceLocation sideTextureLocation = RadiationFission.asResource("block/" + prefix + "_bearing_side");
        ResourceLocation backTextureLocation = Create.asResource("block/" + backTexture);
        return b -> b.initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .blockstate((c, p) -> p.directionalBlock(c.get(), p.models()
                        .withExistingParent(c.getName(), baseBlockModelLocation)
                        .texture("side", sideTextureLocation)
                        .texture("back", backTextureLocation)))
                .item()
                .model((c, p) -> p.withExistingParent(c.getName(), baseItemModelLocation)
                        .texture("top", topTextureLocation)
                        .texture("side", sideTextureLocation)
                        .texture("back", backTextureLocation))
                .build();
    }
}
