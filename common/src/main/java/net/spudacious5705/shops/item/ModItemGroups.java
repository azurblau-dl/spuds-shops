package net.spudacious5705.shops.item;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.spudacious5705.shops.SpudaciousShops;
import net.spudacious5705.shops.block.ModBlocks;

import java.util.function.Supplier;
import net.spudacious5705.shops.util.PendingRegistry;
import net.minecraft.core.registries.BuiltInRegistries;


public final class ModItemGroups {

    public static final PendingRegistry<CreativeModeTab> CREATIVE_MODE_TABS =
            new PendingRegistry<>(BuiltInRegistries.CREATIVE_MODE_TAB, SpudaciousShops.MOD_ID);

    public static final Supplier<CreativeModeTab> SHOPS_TAB = CREATIVE_MODE_TABS.register("shops_tab",
            () -> net.spudacious5705.shops.platform.Platform.creativeTab().icon(() -> new ItemStack(ModBlocks.SHOP_BLOCK_ANGLED_OAK.get().getDefaultColouredShopItem()))
                    .title(Component.translatable("itemGroup.spudaciousshops.shop_item_group"))
                    .displayItems(
                            (params, entries) -> {
                                entries.accept(ModItems.CONTRACT_SCROLL.get());
                                ModBlocks.ALL_SHOPS.forEach(shop -> entries.accept(shop.get()));
                    }
                    ).build());
    

    public static void register() {
        CREATIVE_MODE_TABS.registerAll();
    }
}

