package net.spudacious5705.shops.lootcondition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import net.spudacious5705.shops.block.entity.AngledShopEntity;
import org.jetbrains.annotations.NotNull;

public record MatchingCushionColourCondition(String expectedColourName) implements LootItemCondition {

    public static final MapCodec<MatchingCushionColourCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codec.STRING.fieldOf("expected_colour").forGetter(MatchingCushionColourCondition::expectedColourName))
                    .apply(instance, MatchingCushionColourCondition::new)
    );

    @Override
    public @NotNull MapCodec<MatchingCushionColourCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockEntity blockEntity = lootContext.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AngledShopEntity shop) {
            return shop.getCushionColour().matchesString(expectedColourName);
        }
        return false;
    }
}
