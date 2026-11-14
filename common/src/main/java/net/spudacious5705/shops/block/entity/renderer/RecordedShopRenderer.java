package net.spudacious5705.shops.block.entity.renderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
/** Extracts the original shop layout into the 26.1 render-state pipeline. */
public abstract class RecordedShopRenderer<T extends BlockEntity> implements BlockEntityRenderer<T, ShopRenderState> {
    @Override public ShopRenderState createRenderState() { return new ShopRenderState(); }
    @Override public void extractRenderState(T shop, ShopRenderState state, float delta, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay breaking) {
        BlockEntityRenderer.super.extractRenderState(shop, state, delta, camera, breaking);
        state.commands.clear();
        render(shop, delta, new PoseStack(), state, state.lightCoords, OverlayTexture.NO_OVERLAY);
    }
    protected abstract void render(T shop, float delta, PoseStack pose, ShopRenderState state, int light, int overlay);
    @Override public void submit(ShopRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        for (var command : state.commands) command.submit(pose, collector);
    }
}
