package net.spudacious5705.shops;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.spudacious5705.shops.block.ModBlockEntities;
import net.spudacious5705.shops.block.entity.renderer.*;
import net.spudacious5705.shops.block.resources.*;
import net.spudacious5705.shops.screen.*;
import net.spudacious5705.shops.screen.owner_screen.ShopScreenOwner;
import net.spudacious5705.shops.screen.networking.*;
public class SpudaciousShopsClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        ScreenResources.init();
        CushionTextures.initialiseCushionTextures();
        BlockEntityRendererRegistry.register(ModBlockEntities.ANGLED_SHOP_ENTITY.get(), AngledShopBlockRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.WINDOW_SHOP_ENTITY.get(), WindowSillShopEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.HOOK_SHOP_ENTITY.get(), HookShopEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.RUG_SHOP_ENTITY.get(), RugShopEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.CRATE_SHOP_ENTITY.get(), CrateShopEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SHELF_SHOP_ENTITY.get(), ShelfShopEntityRenderer::new);
        ModelLayerRegistry.registerModelLayer(CushionModel.LAYER_LOCATION, CushionModel::getTexturedModelData);
        MenuScreens.register(ModScreenHandlers.SHOP_SCREEN_HANDLER_OWNER.get(), ShopScreenOwner::new);
        MenuScreens.register(ModScreenHandlers.SHOP_SCREEN_HANDLER_CUSTOMER.get(), ShopScreenCustomer::new);
        ClientPlayNetworking.registerGlobalReceiver(ShopTabSyncPkt.TYPE, (packet, ctx) -> ctx.client().execute(() -> {
            if (ctx.client().screen instanceof ShopScreenOwner screen) screen.getMenu().updateTabSelectionResponse(packet.tab());
        }));
        ClientPlayNetworking.registerGlobalReceiver(ToggleSyncPkt.TYPE, (packet, ctx) -> ctx.client().execute(() -> {
            if (ctx.client().screen instanceof ShopScreenOwner screen) screen.getMenu().updateToggleButtonFromPacket(packet.buttonID(), packet.state());
        }));
    }
}
