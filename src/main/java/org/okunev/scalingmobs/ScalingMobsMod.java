package org.okunev.scalingmobs;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.okunev.scalingmobs.config.ScalingMobsConfig;
import org.okunev.scalingmobs.config.ScalingMobsMenu;


@Mod(ScalingMobsMod.MODID)
public class ScalingMobsMod {
    public static final String MODID = "scalingmobs";

    public ScalingMobsMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ScalingMobsConfig.register();
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ScalingMobsMenu.registerModMenu(event);
    }
}