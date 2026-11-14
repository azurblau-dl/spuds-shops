package net.spudacious5705.shops;
import net.fabricmc.api.ModInitializer;
import net.spudacious5705.shops.screen.networking.NetworkHelper;
public final class FabricShops implements ModInitializer {
 public void onInitialize() {
  SpudaciousShops.initialize();
  SpudaciousShops.finishRegistration();
  NetworkHelper.register();
  net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, entity) -> {
   if (entity instanceof net.spudacious5705.shops.block.entity.AbstractShopEntity shop && shop.isUnbreakable(player)) {
    player.sendOverlayMessage(shop.cantBreakMessage()); return false;
   }
   return true;
  });
 }
}
