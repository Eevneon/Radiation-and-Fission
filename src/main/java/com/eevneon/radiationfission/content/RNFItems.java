package com.eevneon.radiationfission.content;

import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import com.eevneon.radiationfission.RadiationFission;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RNFItems {
    private static final CreateRegistrate REGISTRATE = RadiationFission.REGISTRATE;

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RadiationFission.MOD_ID);

    public static final ItemEntry<Item> URANIUM_INGOT = taggedIngredient("uranium_ingot", CREATE_INGOTS.tag),
            STEEL_INGOT = taggedIngredient("steel_ingot", CREATE_INGOTS.tag);

    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
