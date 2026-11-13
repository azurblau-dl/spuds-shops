package net.spudacious5705.shops.screen.owner_screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.spudacious5705.shops.screen.ScreenResources;
import net.spudacious5705.shops.screen.ScreenSettingsGroup;
import net.spudacious5705.shops.screen.ToggleButtonID;
import net.spudacious5705.shops.screen.networking.ShopSelfDemotePkt;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static net.spudacious5705.shops.SpudaciousShops.id;
import static net.spudacious5705.shops.screen.ModScreenHandlers.CURRENCY_IMG_MAP;
import static net.spudacious5705.shops.screen.ScreenResources.*;
import static net.spudacious5705.shops.screen.owner_screen.ShopScreenHandlerOwner.*;

public class ShopScreenOwner extends AbstractContainerScreen<ShopScreenHandlerOwner> {

    //region resources
    private static final Identifier RED_BUTTON = id("textures/gui/red_button.png");
    private static final Identifier RED_BUTTON_SELECTED = id("textures/gui/red_button_selected.png");
    private static final Identifier GREEN_BUTTON = id("textures/gui/green_button.png");
    private static final Identifier GREEN_BUTTON_SELECTED = id("textures/gui/green_button_selected.png");
    private static final Identifier COG_ICON = id("textures/gui/settings.png");
    private static final Identifier STORAGE_ICON = id("textures/gui/storage.png");
    private static final Identifier SHOPFRONT_ICON = CURRENCY_IMG_MAP.getOrDefault(
            Component.translatable("gui.spudaciousshops.currency_type").getString().charAt(0),
            id("textures/gui/currency_textures/gbp.png")
    );
    private static final Identifier TAB_SELECTED = id("textures/gui/tab_selected.png");
    private static final Identifier TAB_DESELECTED = id("textures/gui/tab_deselected.png");
    private static final Identifier TAB_HOVER = id("textures/gui/tab_hover.png");
    private static final Identifier CREATIVE_ON = id("textures/gui/creative_on.png");
    private static final Identifier CREATIVE_OFF = id("textures/gui/creative_off.png");
    private static final Identifier EFFECTS_ON = id("textures/gui/effects_on.png");
    private static final Identifier EFFECTS_OFF = id("textures/gui/effects_off.png");
    private static final Identifier NBT_IGNORE = id("textures/gui/nbt_ignore.png");
    private static final Identifier NBT_CHECK = id("textures/gui/nbt_check.png");
    private static final Identifier TRADE_MONO = id("textures/gui/trade_mono.png");
    private static final Identifier TRADE_SELECT = id("textures/gui/trade_select.png");
    //endregion resources

    private final ScreenSettingsGroup SETTINGS;

    TradeItemWidget PaymentItemWidget;
    TradeItemWidget ProductItemWidget;

    NotificationWidget NotifShopStyleSelectOn;
    NotificationWidget NotifNBTMatchOFF;

    TabWidget SellerTabButton;
    TabWidget SettingsTabButton;
    TabWidget ShopFrontTabButton;
    ButtonWidget WarningCancel;
    ButtonWidget WarningProceed;
    ToggleWidget ToggleCreative;
    ToggleWidget ToggleIconsEffects;
    ToggleWidget ToggleShopStyle;
    ToggleWidget ToggleIgnoreNBT;

    public ShopScreenOwner(ShopScreenHandlerOwner menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 228, 256);
        this.SETTINGS = menu.getSettings();
        this.leftPos = (width - imageWidth)/2;
        this.topPos = (height - imageHeight)/2;

        menu.updateTabSelection();
    }

    @Override
    protected void init() {
        super.init();

        int posX = SETTINGS.tab1ButtonX() + leftPos;
        int posY = SETTINGS.tab1ButtonY() + topPos;
        SellerTabButton = new TabWidget(posX, posY, Component.literal(""), STORAGE_ICON, SELLER_TAB);


        posX = SETTINGS.tab2ButtonX() + leftPos;
        posY = SETTINGS.tab2ButtonY() + topPos;
        SettingsTabButton = new TabWidget(posX, posY, Component.literal(""), COG_ICON, SETTINGS_TAB);


        posX = SETTINGS.tab3ButtonX() + leftPos;
        posY = SETTINGS.tab3ButtonY() + topPos;
        ShopFrontTabButton = new TabWidget(posX, posY, Component.literal(""), SHOPFRONT_ICON, CUSTOMER_TAB);


        posX = 22 + leftPos;
        posY = 128 + topPos;
        WarningCancel = new ButtonWidget(posX, posY, Component.literal("CANCEL"), () -> this.menu.updateTabSelectionClientside(SETTINGS_TAB), GREEN_BUTTON, GREEN_BUTTON_SELECTED, CANCEL, 3840);

        posX += 113;
        WarningProceed = new ButtonWidget(posX, posY, Component.literal("CONTINUE"), this::WarnPopupContinue, RED_BUTTON, RED_BUTTON_SELECTED, DELETE, 984329);

        posX = SETTINGS.creativeButtonX() + leftPos;
        posY = SETTINGS.creativeButtonY() + topPos;
        ToggleCreative = new ToggleWidget(posX, posY, ToggleButtonID.CreativeToggle, CREATIVE_ON, CREATIVE_OFF, CREATIVE_TOGGLE_TOOLTIP);
        posX = SETTINGS.toggleEffectsButtonX() + leftPos;
        posY = SETTINGS.toggleEffectsButtonY() + topPos;
        ToggleIconsEffects = new ToggleWidget(posX, posY, ToggleButtonID.EffectsToggle, EFFECTS_ON, EFFECTS_OFF, EFFECTS_TOGGLE_TOOLTIP);

        PaymentItemWidget = new TradeItemWidget(menu.PaymentSlot, PAYMENT, 0, PAYMENT_EMPTY_TOOLTIP);
        ProductItemWidget = new TradeItemWidget(menu.VendingSlot, PRODUCT, 1, PRODUCT_EMPTY_TOOLTIP);


        posX = SETTINGS.shopStyleButtonX() + leftPos;
        posY = SETTINGS.shopStyleButtonY() + topPos;
        ToggleShopStyle = new ToggleWidget(posX, posY, ToggleButtonID.SelectableTradeToggle, TRADE_SELECT, TRADE_MONO, SHOP_STYLE_TOGGLE_TOOLTIP);

        posX = SETTINGS.ignoreNBTButtonX() + leftPos;
        posY = SETTINGS.ignoreNBTButtonY() + topPos;
        ToggleIgnoreNBT = new ToggleWidget(posX, posY, ToggleButtonID.IgnoreNBTToggle, NBT_IGNORE, NBT_CHECK, IGNORE_NBT_TOGGLE_TOOLTIP);

        posX = 2 + leftPos;
        posY = 78 + topPos;
        NotifShopStyleSelectOn = new NotificationWidget(posX, posY, SELECT_STYLE_INFO,
                () -> menu.getStateOfSetting(ToggleButtonID.SelectableTradeToggle));

        posX = 168 + leftPos;
        posY = 127 + topPos;
        NotifNBTMatchOFF = new NotificationWidget(posX, posY, PRODUCT_NBT_UNCHECKED_WARN,
                () -> menu.getStateOfSetting(ToggleButtonID.IgnoreNBTToggle) && !menu.getStateOfSetting(ToggleButtonID.SelectableTradeToggle));

    }

    //region rendering
    @Override
    protected void extractLabels(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        // Do nothing — this prevents the title and inventory label from rendering
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        net.spudacious5705.shops.screen.GuiTextures.blit(guiGraphics, menu.getBackgroundTexture(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(context, mouseX, mouseY, partialTick);
        Font font = Minecraft.getInstance().font;


        switch (menu.getActiveTab()){
            case SELLER_TAB -> {
                renderStorageHeaders(context,font,leftPos,topPos);

                renderScreenGenerics(context,mouseX,mouseY,partialTick);

                PaymentItemWidget.extractWidgetRenderState(context,mouseX,mouseY,partialTick,font);
                ProductItemWidget.extractWidgetRenderState(context,mouseX,mouseY,partialTick,font);

                NotifShopStyleSelectOn.extractWidgetRenderState(context,mouseX,mouseY,partialTick, font);
            }
            case SETTINGS_TAB -> {

                //toggleButtons.forEach();

                if(menu.isPlayerCreative()) {
                    ToggleCreative.extractWidgetRenderState(context, mouseX, mouseY, partialTick);
                }

                ToggleIconsEffects.extractWidgetRenderState(context,mouseX,mouseY,partialTick);

                ToggleIgnoreNBT.extractWidgetRenderState(context,mouseX,mouseY,partialTick);

                ToggleShopStyle.extractWidgetRenderState(context,mouseX,mouseY,partialTick);




                for(ScreenResources.ToolTipText ttt : SETTINGS_HOVER_INFO_TEXTS){
                    ttt.render(context,font,mouseX,mouseY,leftPos,topPos);
                }

                renderScreenGenerics(context,mouseX,mouseY,partialTick);
            }
            case CUSTOMER_TAB -> {

                renderScreenGenerics(context,mouseX,mouseY,partialTick);

                NotifNBTMatchOFF.extractWidgetRenderState(context,mouseX,mouseY,partialTick, font);
            }
            case WARNING_TAB -> {
                WarningCancel.extractWidgetRenderState(context,mouseX,mouseY,partialTick);
                WarningProceed.extractWidgetRenderState(context,mouseX,mouseY,partialTick);

                renderWarnPopupTextBody(context,font,leftPos,topPos);

            }
            default -> throw new IllegalStateException("Unexpected value: " + menu.getActiveTab());
        }

        this.extractTooltip(context, mouseX, mouseY);
    }

    private void renderScreenGenerics(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY, float partialTick) {
        SellerTabButton.extractWidgetRenderState(context,mouseX,mouseY,partialTick);
        SettingsTabButton.extractWidgetRenderState(context,mouseX,mouseY,partialTick);
        ShopFrontTabButton.extractWidgetRenderState(context,mouseX,mouseY,partialTick);
    }
    //endregion rendering

    //region interaction
    private void WarnPopupContinue(){
        ClientPlayNetworking.send(new ShopSelfDemotePkt());
        menu.close();
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x(), mouseY = event.y();
        int button = event.button();


            if(switch (menu.getActiveTab()) {
                case SETTINGS_TAB -> tryClickWidgets(mouseX,mouseY,button,
                        ToggleCreative, ToggleIconsEffects, ToggleIgnoreNBT, ToggleShopStyle,
                        SettingsTabButton, SellerTabButton, ShopFrontTabButton
                        );

                case WARNING_TAB -> tryClickWidgets(mouseX,mouseY,button,
                        WarningCancel,
                        WarningProceed
                );

                case SELLER_TAB -> tryClickWidgets(mouseX,mouseY,button,
                        SettingsTabButton, SellerTabButton, ShopFrontTabButton,
                        ProductItemWidget, PaymentItemWidget
                );

                default -> tryClickWidgets(mouseX,mouseY,button,
                        SettingsTabButton, SellerTabButton, ShopFrontTabButton
                );
            }){
                //click success
                var player = Minecraft.getInstance().player;
                if(player != null){
                    player.playSound(
                            SoundEvents.UI_BUTTON_CLICK.value()
                    );
                }
                return true;
            }



        return super.mouseClicked(event, doubleClick);
    }

    private boolean tryClickWidgets(double mouseX, double mouseY, int button, CustomClickableWidget... widgets) {
        for(CustomClickableWidget widget : widgets){
            if(widget.attemptClick(mouseX,mouseY,button))return true;
        }
        return false;
    }
    //endregion interaction


    private abstract static class CustomClickableWidget extends AbstractWidget {

        public CustomClickableWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
            super(pX, pY, pWidth, pHeight, pMessage);
        }

        public boolean attemptClick(double X, double Y, int button){
            if( isHovered(X,Y) ){
                onClick(X,Y, button);
                return true;
            }
            return false;
        }

        public void onClick(double X, double Y) {}

        public void onClick(double X, double Y, int button){
            if(button==0) {
                onClick(X, Y);
            }
        }

        public boolean isHovered(double X, double Y){
            X -= this.getX();
            Y -= this.getY();
            return X >= 0 && X < this.width
                    &&
                    Y >= 0 && Y < this.height;
        }

        @Override
        protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor pGuiGraphicsExtractor, int pMouseX, int pMouseY, float pPartialTick) {}

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}
    }

    private class TabWidget extends CustomClickableWidget {

        private final Identifier ICON_TEXTURE;

        private final int relatedState;

        public TabWidget(int x, int y, Component message, Identifier texture, int relatedState) {
            super(x, y, 22, 22, message);
            this.relatedState = relatedState;
            this.ICON_TEXTURE = texture;
        }

        @Override
        protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor context, int pMouseX, int pMouseY, float pPartialTick) {
            int x = this.getX()-3;
            int y = this.getY()-6;
            if(menu.getActiveTab() == relatedState){
                net.spudacious5705.shops.screen.GuiTextures.blit(context, TAB_SELECTED,x,y,32,32,0f,0f,32,32,32,32);
            }else if(isHovered(pMouseX,pMouseY)){
                net.spudacious5705.shops.screen.GuiTextures.blit(context, TAB_HOVER,x,y,32,32,0f,0f,32,32,32,32);
            }else{
                net.spudacious5705.shops.screen.GuiTextures.blit(context, TAB_DESELECTED,x,y,32,32,0f,0f,32,32,32,32);
            }
            net.spudacious5705.shops.screen.GuiTextures.blit(context, ICON_TEXTURE,x+6,y+9,16,16,0f,0f,16,16,16,16);
        }

        public void onClick(double mouseX, double mouseY) {
            menu.updateTabSelectionClientside(relatedState);
        }

    }

    private class ToggleWidget extends CustomClickableWidget{

        private final Identifier TEXTURE_ON;
        private final Identifier TEXTURE_OFF;

        private final ToggleButtonID BUTTON_ID;
        private final MutableComponent tooltip;


        public ToggleWidget(int x, int y, ToggleButtonID buttonID, Identifier textureON, Identifier textureOFF, MutableComponent tooltipText) {
            super(x, y, 32, 16, Component.literal(""));
            this.BUTTON_ID = buttonID;
            this.visible = false;
            this.TEXTURE_ON = textureON;
            this.TEXTURE_OFF = textureOFF;
            this.tooltip = tooltipText;
        }

        @Override
        protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY, float pPartialTick) {
            int x = this.getX();
            int y = this.getY();

            boolean toggle = menu.getStateOfSetting(BUTTON_ID);

            net.spudacious5705.shops.screen.GuiTextures.blit(context, SETTINGS.BUTTON_BACKGROUND(),x-3,y-3,64,64,0f,0f,64,64,64,64);
            net.spudacious5705.shops.screen.GuiTextures.blit(context, toggle ? TEXTURE_ON : TEXTURE_OFF ,x,y,32,32,0f,0f,32,32,32,32);

            if(isHovered(mouseX,mouseY)){
                context.setTooltipForNextFrame(Minecraft.getInstance().font, tooltip,mouseX,mouseY);
            }
        }

        @Override
        public void onClick(double pMouseX, double pMouseY) {
            menu.handleToggleButtonInput(BUTTON_ID);
        }
    }

    private class ButtonWidget extends CustomClickableWidget{

        private final Runnable FUNCTION;
        private final Identifier TEXTURE;
        private final Identifier TEXTURE_HOVERED;
        private final MutableComponent TEXT;
        private final int textX;
        private final int textY;
        private final int textColour;

        public ButtonWidget(int x, int y, Component message, Runnable function, Identifier texture, Identifier textureHovered, MutableComponent text, int colour) {
            super(x, y, 64, 28, message);
            this.FUNCTION = function;
            this.TEXTURE = texture;
            this.TEXTURE_HOVERED = textureHovered;
            this.textX = 32+x;
            this.textY = 13+y;
            this.TEXT = text;
            this.textColour = colour;
        }

        @Override
        protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor context, int pMouseX, int pMouseY, float pPartialTick) {
            int x = this.getX();
            int y = this.getY()-16;
            int tx = textX;

            if(isHovered(pMouseX,pMouseY)){

                net.spudacious5705.shops.screen.GuiTextures.blit(context, TEXTURE_HOVERED,x,y,64,64,0f,0f,64,64,64,64);

                tx++;

            }else{

                net.spudacious5705.shops.screen.GuiTextures.blit(context, TEXTURE,x,y,64,64,0f,0f,64,64,64,64);

            }

            renderCentredText(context, font, TEXT, tx,textY,textColour, false);
        }


        @Override
        public void onClick(double mouseX, double mouseY) {
            FUNCTION.run();
        }

    }

    private class TradeItemWidget extends CustomClickableWidget{

        private final ShopScreenHandlerOwner.shop_trade_slot slot;
        private final MutableComponent emptyStackTooltip;

        private TradeItemWidget(ShopScreenHandlerOwner.shop_trade_slot slot, Component message, int slotId, MutableComponent emptyStackTooltip) {
            super(slot.x, slot.y, 16, 16, message);
            this.slot = slot;
            this.emptyStackTooltip = emptyStackTooltip;
        }


        protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor context, int pMouseX, int pMouseY, float pPartialTick, Font font) {

            int x = this.getX()+leftPos;
            int y = this.getY()+topPos;

            ItemStack stack = slot.getItem();



            context.pose().pushMatrix();
            context.nextStratum();
            if (isHovered(pMouseX,pMouseY)) {//hovered?
                context.fill(x, y, x + 16, y + 16, -2130706433);
                if(stack.isEmpty()){
                    context.setTooltipForNextFrame(font, emptyStackTooltip, pMouseX, pMouseY);
                } else {
                    context.setTooltipForNextFrame(font, stack, pMouseX, pMouseY);
                }
            }

            context.item(stack, x, y);
            context.itemDecorations(font, stack, x, y, null);


            context.pose().popMatrix();
        }

        @Override
        public boolean isHovered(double X, double Y) {
            return super.isHovered(X-leftPos, Y-topPos);
        }

        @Override
        public void onClick(double mouseX, double mouseY, int button) {


            if(!menu.perms.canEditTrades()){
                playWarnSound(menu.playerInventory.player);
                return;
            }

            assert minecraft != null;
            assert minecraft.gameMode != null;
            assert minecraft.player != null;
            minecraft.gameMode.handleContainerInput(menu.containerId, slot.index, button, ContainerInput.PICKUP, minecraft.player);
        }

    }

    public static class NotificationWidget extends CustomClickableWidget {
        private final List<Component> tooltip;

        private final Supplier<Boolean> renderToggle;

        public NotificationWidget(int pX, int pY, List<Component> tooltip, Supplier<Boolean> renderToggle) {
            super(pX, pY, 16, 16, Component.literal(""));
            this.tooltip = tooltip;
            this.renderToggle = renderToggle;
        }
        public void extractWidgetRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float d, Font font) {
            if(!renderToggle.get())return;
            int x = this.getX();
            int y = this.getY();

            net.spudacious5705.shops.screen.GuiTextures.blit(graphics, NOTIFICATION_ICON,x,y,32,32,0f,0f,32,32,32,32);

            if(isHovered(mouseX,mouseY)){
                graphics.setComponentTooltipForNextFrame(font, tooltip, mouseX, mouseY);
            }
        }
    }
}
