package net.spudacious5705.shops.screen;


import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.spudacious5705.shops.screen.owner_screen.ShopScreenOwner;
import org.jetbrains.annotations.NotNull;

import static net.spudacious5705.shops.screen.ScreenResources.PRODUCT_NBT_UNCHECKED_WARN;


public class ShopScreenCustomer extends AbstractContainerScreen<ShopScreenHandlerCustomer> {


    public ShopScreenCustomer(ShopScreenHandlerCustomer handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title, 228, 256);
    }

    ShopScreenOwner.NotificationWidget NotifNBTMatchOFF;
    @Override
    protected void init() {
        super.init();

        leftPos = (width - imageWidth)/2;
        topPos = (height - imageHeight)/2;

        NotifNBTMatchOFF = new ShopScreenOwner.NotificationWidget(168 + leftPos, 127 + topPos, PRODUCT_NBT_UNCHECKED_WARN,
                menu::showNBToffNotif);
    }

    @Override
    protected void extractLabels(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        // Do nothing — this prevents the title and inventory label from rendering
        //TODO perhaps implement this in fabric
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        net.spudacious5705.shops.screen.GuiTextures.blit(guiGraphics, menu.getBackgroundTexture(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        NotifNBTMatchOFF.extractWidgetRenderState(guiGraphics,mouseX,mouseY,partialTick, font);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }
}
