package net.spudacious5705.shops.platform;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.spudacious5705.shops.screen.ShopOpeningData;
public abstract class ShopMenuProvider implements MenuProvider {
 public abstract ShopOpeningData getScreenOpeningData(ServerPlayer player);
 @Override public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
  ShopOpeningData.STREAM_CODEC.encode(buffer, getScreenOpeningData(null));
 }
}
