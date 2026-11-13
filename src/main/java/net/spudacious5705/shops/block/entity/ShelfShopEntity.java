package net.spudacious5705.shops.block.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.spudacious5705.shops.block.ModBlockEntities;
import org.jetbrains.annotations.NotNull;

import static net.spudacious5705.shops.block.entity.ShopInventory.ItemScatterer;

public class ShelfShopEntity extends AbstractShopEntity{

    private final ShopInventory shopInventoryTop;

    @Environment(EnvType.CLIENT)
    protected ShelfRenderData furtherDataTop;
    @Environment(EnvType.CLIENT)
    protected ShelfRenderData furtherDataBottom;

    @Environment(EnvType.CLIENT)
    public ShelfRenderData furtherDataBottom(){return furtherDataBottom;}

    @Environment(EnvType.CLIENT)
    public ShelfRenderData furtherDataTop(){return furtherDataTop;}

    @Environment(EnvType.CLIENT)
    public static class ShelfRenderData {
        public final float itemLrotation = (float) ((Math.random() * 90) + 80f);
        public final float itemRrotation = (float) ((Math.random() * 90) + 80f);
    }

    @Override
    protected @NotNull ShopInventory otherInventory() {
        return shopInventoryTop;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void forceUpdateRenderData() {
        super.forceUpdateRenderData();
        rendererDataTop.update();
    }

    @Override
    public void itemScatter(Level world, BlockPos pos) {
        super.itemScatter(world, pos);
        ItemScatterer(world, pos, shopInventoryTop.prepForItemScatterer());

    }

    @Override
    public void renderTick() {
        this.rendererData.onTick();
        this.rendererDataTop.onTick();
    }

    public ShelfShopEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHELF_SHOP_ENTITY.get(), pos, state, -0.3f);
        this.shopInventoryTop = ShopInventory.create(toggleSettings);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            createRendererDataForShelf();
        }
    }

    @Environment(EnvType.CLIENT)
    protected void createRendererDataForShelf() {
        this.furtherDataTop = new ShelfRenderData();
        this.furtherDataBottom = new ShelfRenderData();
        this.rendererDataTop = new RendererData(shopInventoryTop);
    }

    @Environment(EnvType.CLIENT)
    protected RendererData rendererDataTop;


    @Environment(EnvType.CLIENT)
    public RendererData rendererDataTop(){return  rendererDataTop;}


    @Override
    protected boolean hasTrade() {
        return shopInventory.tradeFunctional()||shopInventoryTop.tradeFunctional();
    }

    @Override
    protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput tag) {
        var items = tag.list("ItemsTwo", net.minecraft.world.ItemStackWithSlot.CODEC);
        for (int slot = 0; slot < shopInventoryTop.size(); slot++) {
            var stack = shopInventoryTop.get(slot);
            if (!stack.isEmpty()) items.add(new net.minecraft.world.ItemStackWithSlot(slot, stack));
        }
        super.saveAdditional(tag);
    }
    @Override
    protected void loadAdditional(net.minecraft.world.level.storage.ValueInput tag) {
        super.loadAdditional(tag);
        shopInventoryTop.clear();
        for (var item : tag.listOrEmpty("ItemsTwo", net.minecraft.world.ItemStackWithSlot.CODEC)) {
            if (item.isValidInContainer(shopInventoryTop.size())) shopInventoryTop.set(item.slot(), item.stack());
        }
    }
}
