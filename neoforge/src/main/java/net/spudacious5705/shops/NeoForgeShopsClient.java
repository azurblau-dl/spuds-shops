package net.spudacious5705.shops;
import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.minecraft.client.Minecraft;
import net.spudacious5705.shops.block.ModBlockEntities;
import net.spudacious5705.shops.block.entity.renderer.*;
import net.spudacious5705.shops.block.resources.*;
import net.spudacious5705.shops.screen.*;
import net.spudacious5705.shops.screen.owner_screen.ShopScreenOwner;
import net.spudacious5705.shops.screen.networking.*;
@Mod(value = SpudaciousShops.MOD_ID, dist = Dist.CLIENT)
public final class NeoForgeShopsClient {
 public NeoForgeShopsClient(IEventBus bus) {
  bus.addListener(this::renderers); bus.addListener(this::layers); bus.addListener(this::screens); bus.addListener(this::network);
  bus.addListener((net.neoforged.fml.event.lifecycle.FMLClientSetupEvent event) -> event.enqueueWork(() -> { ScreenResources.init(); CushionTextures.initialiseCushionTextures(); }));
 }
 private void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ANGLED_SHOP_ENTITY.get(), AngledShopBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WINDOW_SHOP_ENTITY.get(), WindowSillShopEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.HOOK_SHOP_ENTITY.get(), HookShopEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.RUG_SHOP_ENTITY.get(), RugShopEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CRATE_SHOP_ENTITY.get(), CrateShopEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHELF_SHOP_ENTITY.get(), ShelfShopEntityRenderer::new);
 }
 private void layers(EntityRenderersEvent.RegisterLayerDefinitions event) { event.registerLayerDefinition(CushionModel.LAYER_LOCATION, CushionModel::getTexturedModelData); }
 private void screens(RegisterMenuScreensEvent event) {
        event.register(ModScreenHandlers.SHOP_SCREEN_HANDLER_OWNER.get(), ShopScreenOwner::new);
        event.register(ModScreenHandlers.SHOP_SCREEN_HANDLER_CUSTOMER.get(), ShopScreenCustomer::new);
 }
 private void network(RegisterClientPayloadHandlersEvent event) {
  event.register(ShopTabSyncPkt.TYPE, (p,c) -> { if (Minecraft.getInstance().screen instanceof ShopScreenOwner screen) screen.getMenu().updateTabSelectionResponse(p.tab()); });
  event.register(ToggleSyncPkt.TYPE, (p,c) -> { if (Minecraft.getInstance().screen instanceof ShopScreenOwner screen) screen.getMenu().updateToggleButtonFromPacket(p.buttonID(), p.state()); });
 }
}
