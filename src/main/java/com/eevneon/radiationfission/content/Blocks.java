package com.eevneon.radiationfission.content;

import com.eevneon.radiationfission.RadiationFission;
import com.eevneon.radiationfission.foundation.AllRadiationFissionSpriteShifts;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.data.*;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

import java.util.function.Supplier;

public class Blocks {

    private static final CreateRegistrate REGISTRATE = RadiationFission.REGISTRATE;

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(RadiationFission.MOD_ID);

    public static final BlockEntry<Block> STEEL_BLOCK = REGISTRATE.block("steel_block", Block::new)
            .initialProperties(() -> net.minecraft.world.level.block.Blocks.IRON_BLOCK)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW)
                    .requiresCorrectToolForDrops())
            .transform(TagGen.pickaxeOnly())
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .transform(TagGen.tagBlockAndItem(CommonMetal.BRASS.storageBlocks))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Steel")
            .register();

    public static final BlockEntry<CasingBlock> STEEL_CASING = REGISTRATE.block("steel_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> AllRadiationFissionSpriteShifts.STEEL_CASING))
            .register();

    public static final BlockEntry<Block> POWERED_SPEED_CONTROLLER = REGISTRATE.block("powered_speed_controller", Block::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .transform(TagGen.axeOrPickaxe())
            .item()
            .transform(customItemModel())
            .register();

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
