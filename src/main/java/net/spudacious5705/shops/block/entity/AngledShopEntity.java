package net.spudacious5705.shops.block.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.spudacious5705.shops.block.ModBlockEntities;
import net.spudacious5705.shops.properties.Colour;
import net.spudacious5705.shops.block.resources.CushionTextures;
import org.jetbrains.annotations.NotNull;


public class AngledShopEntity extends AbstractShopEntity{

    /**
     * Do not read from directly in case of null value
     * Use getCushionColour()
     */
    private Colour cushionColour;

    public AngledShopEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ANGLED_SHOP_ENTITY.get(), pos, state, 0.375f);
    }

    private static final String COLOUR_NBT_TAG = "cushion_colour";


    @Override
    protected void loadAdditional(net.minecraft.world.level.storage.ValueInput tag) {
        super.loadAdditional(tag);
        this.cushionColour = Colour.fromId(tag.getIntOr(COLOUR_NBT_TAG, Colour.RED.getId()));
    }

    @Override
    protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput tag) {
        tag.putInt(COLOUR_NBT_TAG, this.getCushionColour().getId());
        super.saveAdditional(tag);
    }

    public Colour getCushionColour() {
        return this.cushionColour == null ? Colour.ORANGE : this.cushionColour;
    }

    public void setCushionColour(@NotNull Colour colour){
        this.cushionColour = colour;
    }

    public Identifier getCushionTextureID() {
        return CushionTextures.TEXTURE_MAP.get(getCushionColour());
    }

}
