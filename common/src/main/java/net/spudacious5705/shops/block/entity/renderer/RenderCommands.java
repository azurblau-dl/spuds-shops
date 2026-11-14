package net.spudacious5705.shops.block.entity.renderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
public final class RenderCommands {
    public static void item(BlockEntityRendererProvider.Context context, ItemStack stack, ItemDisplayContext mode, boolean leftHand, PoseStack pose, ShopRenderState target, int light, int overlay) {
        var item = new ItemStackRenderState();
        context.itemModelResolver().updateForTopItem(item, stack.copy(), mode, Minecraft.getInstance().level, null, 0);
        var transform = new Matrix4f(pose.last().pose());
        target.commands.add((base, collector) -> {
            base.pushPose(); base.mulPose(transform);
            item.submit(base, collector, light, overlay, 0);
            base.popPose();
        });
    }
    public static void text(String text, float x, float y, int color, boolean shadow, Matrix4f pose, ShopRenderState target, Font.DisplayMode mode, int background, int light) {
        var transform = new Matrix4f(pose);
        var glyphs = Component.literal(text).getVisualOrderText();
        target.commands.add((base, collector) -> {
            base.pushPose(); base.mulPose(transform);
            collector.submitText(base, x, y, glyphs, shadow, mode, light, color | 0xFF000000, background, 0);
            base.popPose();
        });
    }
    public static void cushion(ModelPart model, Identifier texture, PoseStack pose, ShopRenderState target, int light, int overlay) {
        var transform = new Matrix4f(pose.last().pose());
        target.commands.add((base, collector) -> {
            base.pushPose(); base.mulPose(transform);
            collector.submitModelPart(model, base, RenderTypes.entitySolid(texture), light, overlay, null);
            base.popPose();
        });
    }
    public static boolean isThreeDimensional(ItemStack stack) {
        var item = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(item, stack, ItemDisplayContext.GUI, Minecraft.getInstance().level, null, 0);
        return !item.isEmpty() && item.usesBlockLight();
    }
}
