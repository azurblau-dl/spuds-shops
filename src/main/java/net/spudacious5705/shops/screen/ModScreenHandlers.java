package net.spudacious5705.shops.screen;



import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.spudacious5705.shops.SpudaciousShops;
import net.spudacious5705.shops.screen.owner_screen.ShopScreenHandlerOwner;
import net.spudacious5705.shops.screen.owner_screen.ShopScreenOwner;

import java.util.Map;
import java.util.function.Supplier;
import net.spudacious5705.shops.util.PendingRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;

import static net.spudacious5705.shops.SpudaciousShops.MOD_ID;
import static net.spudacious5705.shops.SpudaciousShops.id;

public class ModScreenHandlers {

    public static final PendingRegistry<MenuType<?>> MENUS =
            new PendingRegistry<>(BuiltInRegistries.MENU, SpudaciousShops.MOD_ID);


    public static final Supplier<MenuType<ShopScreenHandlerOwner>> SHOP_SCREEN_HANDLER_OWNER =
            MENUS.register("shop_gui_owner",
                    () -> new ExtendedMenuType<>(ShopScreenHandlerOwner::create, ShopOpeningData.STREAM_CODEC));

    public static final Supplier<MenuType<ShopScreenHandlerCustomer>> SHOP_SCREEN_HANDLER_CUSTOMER =
            MENUS.register("shop_gui_customer",
                    () -> new ExtendedMenuType<>(ShopScreenHandlerCustomer::create, ShopOpeningData.STREAM_CODEC));




    public static final Map<Character, Identifier> CURRENCY_IMG_MAP = Map.of(
            '£', id("textures/gui/currency_textures/gbp.png"),
            '€', id("textures/gui/currency_textures/eur.png"),
            'x', id("textures/gui/contract_slot.png")
    );


    public static void registerScreenHandlers() {//called by modMain

        SpudaciousShops.LOGGER.info("Registering screen handlers for " + SpudaciousShops.MOD_ID);
        MENUS.registerAll();
    }

}
