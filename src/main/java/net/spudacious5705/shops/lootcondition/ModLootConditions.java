package net.spudacious5705.shops.lootcondition;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;


import static net.spudacious5705.shops.SpudaciousShops.MOD_ID;
import static net.spudacious5705.shops.SpudaciousShops.id;

public class ModLootConditions {
    public static final Identifier RESOURCE_LOCATION = id("matches_colour");

    public static final com.mojang.serialization.MapCodec<MatchingCushionColourCondition> MATCHES_ENUM = MatchingCushionColourCondition.CODEC;
    public static void registerLootConditions() {
        net.minecraft.core.Registry.register(net.minecraft.core.registries.BuiltInRegistries.LOOT_CONDITION_TYPE, RESOURCE_LOCATION, MATCHES_ENUM);
    }
}

