package net.spudacious5705.shops;

import net.minecraft.resources.Identifier;
import net.spudacious5705.shops.block.*;
import net.spudacious5705.shops.block.resources.VariantResources;
import net.spudacious5705.shops.config.ConfigHandler;
import net.spudacious5705.shops.item.*;
import net.spudacious5705.shops.lootcondition.ModLootConditions;
import net.spudacious5705.shops.properties.ModProperties;
import net.spudacious5705.shops.screen.ModScreenHandlers;
import net.spudacious5705.shops.util.PostRegAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpudaciousShops {
    public static final String MOD_ID = "spudaciousshops";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Identifier id(String path) { return Identifier.fromNamespaceAndPath(MOD_ID, path); }
    public static void initialize() {
        ConfigHandler.initialise();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockTags.initialise();
        ModProperties.registerModProperties();
        ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();
        ModItemGroups.register();
        ModLootConditions.registerLootConditions();
    }
    public static void finishRegistration() {
        ModBlocks.postRegistryTasks.forEach(Runnable::run);
        PostRegAssigner.runAllAssigners();
        VariantResources.register();
        net.spudacious5705.shops.block.resources.CushionResources.initialise();
    }
}
