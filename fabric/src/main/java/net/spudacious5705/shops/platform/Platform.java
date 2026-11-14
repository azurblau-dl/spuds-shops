package net.spudacious5705.shops.platform;
import java.nio.file.Path;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.spudacious5705.shops.screen.ShopOpeningData;
public final class Platform {
 public static boolean isClient() { return net.fabricmc.loader.api.FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT; }
 public static Path configDir() { return net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir(); }
 public static void register(Registry<?> registry, Runnable action) { action.run(); }
 public static void send(ServerPlayer player, CustomPacketPayload packet) { net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, packet); }
 public static CreativeModeTab.Builder creativeTab() { return net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab.builder(); }
 public static <T extends BlockEntity> BlockEntityType<T> blockEntityType(BlockFactory<T> factory, Block... blocks) { return net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(factory::create, blocks).build(); }
 public static <T extends AbstractContainerMenu> MenuType<T> menuType(MenuFactory<T> factory) { return new net.fabricmc.fabric.api.menu.v1.ExtendedMenuType<>(factory::create, ShopOpeningData.STREAM_CODEC); }

 @FunctionalInterface public interface BlockFactory<T extends BlockEntity> { T create(BlockPos pos, BlockState state); }
 @FunctionalInterface public interface MenuFactory<T extends AbstractContainerMenu> { T create(int id, Inventory inv, ShopOpeningData data); }
}
