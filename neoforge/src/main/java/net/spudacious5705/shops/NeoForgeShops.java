package net.spudacious5705.shops;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.server.level.ServerPlayer;
import net.spudacious5705.shops.platform.Platform;
import net.spudacious5705.shops.screen.networking.*;
@Mod(SpudaciousShops.MOD_ID)
public final class NeoForgeShops {
 public NeoForgeShops(IEventBus bus) {
  SpudaciousShops.initialize();
  bus.addListener(this::register);
  bus.addListener(this::setup);
  bus.addListener(this::network);
  net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(this::beforeBreak);
 }
 private void register(RegisterEvent event) {
  var actions = Platform.REGISTRATIONS.remove(event.getRegistryKey());
  if (actions != null) actions.forEach(Runnable::run);
 }
 private void setup(FMLCommonSetupEvent event) { event.enqueueWork(SpudaciousShops::finishRegistration); }
 private void beforeBreak(net.neoforged.neoforge.event.level.block.BreakBlockEvent event) {
  if (!(event.getPlayer() instanceof ServerPlayer)) return;
  if (event.getLevel().getBlockEntity(event.getPos()) instanceof net.spudacious5705.shops.block.entity.AbstractShopEntity shop && shop.isUnbreakable(event.getPlayer())) {
   event.setCanceled(true); event.setNotifyClient(true); event.getPlayer().sendOverlayMessage(shop.cantBreakMessage());
  }
 }
 private void network(RegisterPayloadHandlersEvent event) {
  var registrar = event.registrar("1");
  registrar.playBidirectional(ShopTabSyncPkt.TYPE, ShopTabSyncPkt.STREAM_CODEC, (p,c) -> p.handleServerSide((ServerPlayer)c.player()));
  registrar.playBidirectional(ToggleSyncPkt.TYPE, ToggleSyncPkt.STREAM_CODEC, (p,c) -> p.handleServerSide((ServerPlayer)c.player()));
  registrar.playToServer(ShopSelfDemotePkt.TYPE, ShopSelfDemotePkt.STREAM_CODEC, (p,c) -> p.handle((ServerPlayer)c.player()));
 }
}
