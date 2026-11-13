package net.spudacious5705.shops.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.spudacious5705.shops.screen.ToggleButtonID;

import java.io.File;
import java.util.EnumMap;

import static net.spudacious5705.shops.SpudaciousShops.MOD_ID;

public class ConfigHandler {

    private static final File CONFIG_PATH = net.fabricmc.loader.api.FabricLoader.getInstance()
            .getConfigDir().resolve(MOD_ID + ".toml").toFile();
    public static final FileConfig config;

    public static final int stackSizeMultiplier;

    private static final EnumMap<ToggleButtonID, Boolean> toggleSettingsStates;


    static {
        try {
            java.nio.file.Files.createDirectories(CONFIG_PATH.toPath().getParent());
        } catch (java.io.IOException e) {
            throw new java.io.UncheckedIOException("Cannot create Spud's Shops configuration directory", e);
        }
        config = FileConfig.of(CONFIG_PATH);
        config.load();

        /*String StackMultiplierID = "TradeStackSizeMultiplier";
        int multiplier = 4;
        if (!config.contains(StackMultiplierID)) {
            config.set(StackMultiplierID, multiplier);
            config.save();
        } else {
            multiplier = config.getOrElse(StackMultiplierID, multiplier);
        }
        stackSizeMultiplier = multiplier;*/
        stackSizeMultiplier = 1;//todo check why these larger stacks cause desync issues.

        toggleSettingsStates = getDefaultToggleSettingStates();

    }

    private static EnumMap<ToggleButtonID, Boolean> getDefaultToggleSettingStates() {
        EnumMap<ToggleButtonID, Boolean> tss = new EnumMap<>(ToggleButtonID.class);

        String TogEffectID = "ToggleEffectsDefault";
        boolean effectsEnabled = true;
        if (!config.contains(TogEffectID)) {
            config.set(TogEffectID, true);
            config.save();
        } else {
            effectsEnabled = config.getOrElse(TogEffectID, true);
        }

        //ugly but ensures all values are covered
        for (ToggleButtonID value : ToggleButtonID.values()) {
            tss.put(value,
                    switch (value){
                        case CreativeToggle -> false;
                        case SelectableTradeToggle -> false;
                        case IgnoreNBTToggle -> false;
                        case EffectsToggle -> effectsEnabled;
                    }
            );
        }
        return tss;
    }
    public static Boolean getDefaultToggleSetting(ToggleButtonID ID) {
        return toggleSettingsStates.getOrDefault(ID,false);
    }

    public static void initialise(){}
}
