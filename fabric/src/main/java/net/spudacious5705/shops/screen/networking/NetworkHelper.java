package net.spudacious5705.shops.screen.networking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
public final class NetworkHelper {
    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(ShopTabSyncPkt.TYPE, ShopTabSyncPkt.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ShopTabSyncPkt.TYPE, ShopTabSyncPkt.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ToggleSyncPkt.TYPE, ToggleSyncPkt.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ToggleSyncPkt.TYPE, ToggleSyncPkt.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ShopSelfDemotePkt.TYPE, ShopSelfDemotePkt.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ShopTabSyncPkt.TYPE, (packet, ctx) -> ctx.server().execute(() -> packet.handleServerSide(ctx.player())));
        ServerPlayNetworking.registerGlobalReceiver(ToggleSyncPkt.TYPE, (packet, ctx) -> ctx.server().execute(() -> packet.handleServerSide(ctx.player())));
        ServerPlayNetworking.registerGlobalReceiver(ShopSelfDemotePkt.TYPE, (packet, ctx) -> ctx.server().execute(() -> packet.handle(ctx.player())));
    }
}
