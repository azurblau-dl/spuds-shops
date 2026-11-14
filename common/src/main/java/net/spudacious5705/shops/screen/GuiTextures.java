package net.spudacious5705.shops.screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
/** Texture coordinates for the mod's existing 256px screens and individual widget textures. */
public final class GuiTextures {
    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int u, int v, int width, int height) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height, 256, 256);
    }
    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height, float u, float v, int sourceWidth, int sourceHeight, int textureWidth, int textureHeight) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height, sourceWidth, sourceHeight, textureWidth, textureHeight);
    }
}
