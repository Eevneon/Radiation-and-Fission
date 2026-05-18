package com.eevneon.radiationfission.content;

import static com.eevneon.radiationfission.content.RNFTags.NameSpace.MOD;
import static com.eevneon.radiationfission.content.RNFTags.NameSpace.COMMON;

import com.eevneon.radiationfission.RadiationFission;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class RNFTags {

    public enum NameSpace {
        MOD(RadiationFission.MOD_ID),
        COMMON(RadiationFission.MOD_ID);

        public final String id;

        NameSpace(String id) {
            this.id = id;
        }

        public ResourceLocation id(String path) {
            return ResourceLocation.fromNamespaceAndPath(this.id, path);
        }

        public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
            return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
        }
    }

    public enum RNFItemTags {
        GOOPANIUM_BUCKETS(COMMON, "buckets/goopanium");

        public final TagKey<Item> tag;

        RNFItemTags() {
            this(MOD);
        }

        RNFItemTags(NameSpace namespace) {
            this(namespace, null);
        }

        RNFItemTags(NameSpace namespace, @Nullable String pathOverride) {
            this.tag = TagKey.create(Registries.ITEM, namespace.id(this, pathOverride));
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }
    }
}
