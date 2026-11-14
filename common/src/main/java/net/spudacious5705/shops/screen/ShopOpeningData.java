package net.spudacious5705.shops.screen;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
public record ShopOpeningData(BlockPos pos, boolean openTop) {
    public static final StreamCodec<ByteBuf, ShopOpeningData> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, ShopOpeningData::pos,
        ByteBufCodecs.BOOL, ShopOpeningData::openTop, ShopOpeningData::new);
}
