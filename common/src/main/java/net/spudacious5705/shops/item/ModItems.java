package net.spudacious5705.shops.item;



import net.minecraft.world.item.Item;
import net.spudacious5705.shops.SpudaciousShops;
import net.spudacious5705.shops.item.custom.ContractScroll;

import java.util.function.Supplier;
import net.spudacious5705.shops.util.PendingRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

import static net.spudacious5705.shops.SpudaciousShops.MOD_ID;

public class ModItems {

    public static final PendingRegistry<Item> ITEMS = new PendingRegistry<>(BuiltInRegistries.ITEM, MOD_ID);

    public static final Supplier<Item> STOCK_WARNING = register(() -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, SpudaciousShops.id("stock_warning")))), "stock_warning");

    public static final Supplier<Item> PAYMENT_WARNING = register(() ->  new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, SpudaciousShops.id("payment_warning")))), "payment_warning");

    public static final Supplier<ContractScroll> CONTRACT_SCROLL = register(() ->  new ContractScroll(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, SpudaciousShops.id("contract_scroll")))), "contract_scroll");

    private static <I extends Item> Supplier<I> register(Supplier<I> item, String name) {
        return ITEMS.register(name,(item));
    }
    public static void registerModItems() {
        SpudaciousShops.LOGGER.info("Registering mod items for " + SpudaciousShops.MOD_ID);
        ITEMS.registerAll();
    }
}
