package com.eevneon.radiationfission.foundation;

import com.eevneon.radiationfission.RadiationFission;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class RNFData {
    public static final DeferredRegister.DataComponents COMPONENT_REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, RadiationFission.MOD_ID);
    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(RadiationFission.asResource(name));
    }

    public static void register(IEventBus bus) {
        COMPONENT_REGISTRAR.register(bus);
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    }

    public static void gatherRegistrateData(GatherDataEvent event) {
        RadiationFission.REGISTRATE.addDataGenerator(ProviderType.LANG, lang -> {
            String interfacePath = "assets/radiationfission/lang/default/interface.json";
            JsonElement jsonElement = FilesHelper.loadJsonResource(interfacePath);
            if (jsonElement == null) {
                throw new IllegalStateException(String.format("Could not find interface lang file: %s", interfacePath));
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                lang.add(key, value);
            }
        });
    }
}
