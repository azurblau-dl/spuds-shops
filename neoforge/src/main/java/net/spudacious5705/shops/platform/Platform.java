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
 public static final java.util.Map<net.minecraft.resources.ResourceKey<?>, java.util.List<Runnable>> REGISTRATIONS = new java.util.LinkedHashMap<>();
 public static boolean isClient() { return net.neoforged.fml.loading.FMLEnvironment.getDist().isClient(); }
 public static Path configDir() { return net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get(); }
 public static void register(Registry<?> registry, Runnable action) { REGISTRATIONS.computeIfAbsent(registry.key(), key -> new java.util.ArrayList<>()).add(action); }
 public static void send(ServerPlayer player, CustomPacketPayload packet) { net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player, packet); }
 public static CreativeModeTab.Builder creativeTab() { return CreativeModeTab.builder(); }
 public static <T extends BlockEntity> BlockEntityType<T> blockEntityType(BlockFactory<T> factory, Block... blocks) { return new BlockEntityType<>(factory::create, java.util.Set.of(blocks)); }
 public static <T extends AbstractContainerMenu> MenuType<T> menuType(MenuFactory<T> factory) { return net.neoforged.neoforge.common.extensions.IMenuTypeExtension.create((id, inv, buf) -> factory.create(id, inv, ShopOpeningData.STREAM_CODEC.decode(buf))); }

 @FunctionalInterface public interface BlockFactory<T extends BlockEntity> { T create(BlockPos pos, BlockState state); }
 @FunctionalInterface public interface MenuFactory<T extends AbstractContainerMenu> { T create(int id, Inventory inv, ShopOpeningData data); }
}
