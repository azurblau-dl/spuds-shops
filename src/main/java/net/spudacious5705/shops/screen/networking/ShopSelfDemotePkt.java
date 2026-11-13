package net.spudacious5705.shops.screen.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.spudacious5705.shops.screen.owner_screen.ShopScreenHandlerOwner;
import org.jetbrains.annotations.NotNull;

import static net.spudacious5705.shops.SpudaciousShops.id;

public record ShopSelfDemotePkt() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ShopSelfDemotePkt> TYPE = new CustomPacketPayload.Type<>(id("self_demote"));


    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public static final StreamCodec<ByteBuf, ShopSelfDemotePkt> STREAM_CODEC = new StreamCodec<>() {
            public @NotNull ShopSelfDemotePkt decode(ByteBuf b) {
                return new ShopSelfDemotePkt();
            }

            public void encode(@NotNull ByteBuf b, @NotNull ShopSelfDemotePkt de) {}
        };

    public void handle(net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.Context ctx) {
        ctx.server().execute(() -> {
            ServerPlayer player = ctx.player();
            if(
                    player.containerMenu instanceof ShopScreenHandlerOwner screenHandler
            ) {
                screenHandler.selfDemotePlayer(player);
                player.doCloseContainer();
            }
        });
    }
}
