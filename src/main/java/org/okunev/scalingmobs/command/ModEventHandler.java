package org.okunev.scalingmobs.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.okunev.scalingmobs.ScalingMobsMod;

@Mod.EventBusSubscriber(modid = ScalingMobsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ScalingMobsCommand.register(event.getDispatcher());
    }
}