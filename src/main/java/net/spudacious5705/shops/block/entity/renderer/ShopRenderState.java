package net.spudacious5705.shops.block.entity.renderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import java.util.ArrayList;
import java.util.List;
/** Frame-local commands contain extracted models and transforms, never a live shop entity. */
public final class ShopRenderState extends BlockEntityRenderState {
    @FunctionalInterface public interface Command { void submit(PoseStack pose, SubmitNodeCollector collector); }
    final List<Command> commands = new ArrayList<>();
}
