package net.spudacious5705.shops.block;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.spudacious5705.shops.SpudaciousShops;
import net.spudacious5705.shops.block.custom.AngledShopBlock;
import net.spudacious5705.shops.block.custom.RugShopBlock;
import net.spudacious5705.shops.block.custom.ShelfShopBlock;
import net.spudacious5705.shops.block.custom.WindowSillShopBlock;
import net.spudacious5705.shops.block.entity.*;

import java.util.function.Supplier;
import net.spudacious5705.shops.util.PendingRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModBlockEntities {

    public static final PendingRegistry<BlockEntityType<?>> BLOCK_ENTITIES =
            new PendingRegistry<>(BuiltInRegistries.BLOCK_ENTITY_TYPE, SpudaciousShops.MOD_ID);


    public static final Supplier<BlockEntityType<AngledShopEntity>> ANGLED_SHOP_ENTITY =
            BLOCK_ENTITIES.register("shop_b_e",
                    () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(AngledShopEntity::new,
                            ModBlocks.ALL_ORIGINAL_SHOPS.stream().map(Supplier::get).toArray(AngledShopBlock[]::new)
                    ).build()
            );

    public static final Supplier<BlockEntityType<WindowSillShopEntity>> WINDOW_SHOP_ENTITY =
            BLOCK_ENTITIES.register("shop_b_e_window_sill",
                    () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(WindowSillShopEntity::new,
                            ModBlocks.ALL_WINDOW_SHOPS.stream().map(Supplier::get).toArray(WindowSillShopBlock[]::new)
                    ).build()
            );


    public static final Supplier<BlockEntityType<HookShopEntity>> HOOK_SHOP_ENTITY =
            BLOCK_ENTITIES.register("shop_b_e_hook",
                    () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(HookShopEntity::new,
                            ModBlocks.SHOP_BLOCK_HOOK.get()
                    ).build()
            );

    public static final Supplier<BlockEntityType<RugShopEntity>> RUG_SHOP_ENTITY =
            BLOCK_ENTITIES.register("shop_b_e_rug",
                    () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(RugShopEntity::new,
                            ModBlocks.ALL_RUG_SHOPS.stream().map(Supplier::get).toArray(RugShopBlock[]::new)
                    ).build()
            );

    public static final Supplier<BlockEntityType<CrateShopEntity>> CRATE_SHOP_ENTITY =
            BLOCK_ENTITIES.register("shop_b_e_crate",
                    () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(CrateShopEntity::new,
                            ModBlocks.SHOP_BLOCK_CRATE.get()
                    ).build()
            );


    public static final Supplier<BlockEntityType<ShelfShopEntity>> SHELF_SHOP_ENTITY =
            BLOCK_ENTITIES.register("shop_b_e_shelf",
                    () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(ShelfShopEntity::new,
                            ModBlocks.ALL_SHELF_SHOPS.stream().map(Supplier::get).toArray(ShelfShopBlock[]::new)
                    ).build()
            );



    public static void registerBlockEntities() {
        SpudaciousShops.LOGGER.info("Registering block entities for" + SpudaciousShops.MOD_ID);
        BLOCK_ENTITIES.registerAll();
    }
}