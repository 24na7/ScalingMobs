package org.okunev.scalingmobs.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;

public class ScalingMobsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BOSSES;

    static {
        BOSSES = BUILDER
                .comment("List of mob names that are considered bosses")
                .comment("You can use regular mobs")
                .comment("To add it, enter modid:mobid (for example, twilightforest:naga)")
                .defineList("bosses", List.of(
                        "minecraft:warden",
                        "minecraft:ender_dragon",
                        "minecraft:wither",
                        "minecraft:elder_guardian",
                        "minecells:concierge",
                        "minecells:conjunctivius",
                        "twilightforest:naga",
                        "twilightforest:lich",
                        "twilightforest:alpha_yeti",
                        "twilightforest:ur_ghast",
                        "twilightforest:hydra",
                        "twilightforest:minoshroom",
                        "twilightforest:snow_queen",
                        "alexsmobs:void_worm",
                        "alexscaves:luxtructosaurus",
                        "aquamirae:captain_cornelia",
                        "cataclysm:maledictus",
                        "cataclysm:ignis",
                        "cataclysm:the_leviathan",
                        "cataclysm:netherite_monstrosity",
                        "cataclysm:the_harbinger",
                        "cataclysm:ender_guardian",
                        "cataclysm:ancient_ancient_remnant",
                        "mowziesmobs:ferrous_wroughtnaut",
                        "mowziesmobs:frostmaw",
                        "mowziesmobs:sculptor",
                        "mowziesmobs:umvuthi",
                        "irons_spellbooks:dead_king"

                ), obj -> obj instanceof String);

        CONFIG = BUILDER.build();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
    }

    @OnlyIn(Dist.CLIENT)
    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Settings Scaling Mobs"));

        ConfigCategory category = builder.getOrCreateCategory(Component.literal("Settings bosses"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        category.addEntry(entryBuilder.startStrList(
                        Component.literal("Bosses"),
                        new ArrayList<>(BOSSES.get()))
                .setDefaultValue(List.of(
                        "minecraft:warden",
                        "minecraft:ender_dragon",
                        "minecraft:wither",
                        "minecraft:elder_guardian",
                        "minecells:concierge",
                        "minecells:conjunctivius",
                        "twilightforest:naga",
                        "twilightforest:lich",
                        "twilightforest:alpha_yeti",
                        "twilightforest:ur_ghast",
                        "twilightforest:hydra",
                        "twilightforest:minoshroom",
                        "twilightforest:snow_queen",
                        "alexsmobs:void_worm",
                        "alexscaves:luxtructosaurus",
                        "aquamirae:captain_cornelia",
                        "cataclysm:maledictus",
                        "cataclysm:ignis",
                        "cataclysm:the_leviathan",
                        "cataclysm:netherite_monstrosity",
                        "cataclysm:the_harbinger",
                        "cataclysm:ender_guardian",
                        "cataclysm:ancient_ancient_remnant",
                        "mowziesmobs:ferrous_wroughtnaut",
                        "mowziesmobs:frostmaw",
                        "mowziesmobs:sculptor",
                        "mowziesmobs:umvuthi",
                        "irons_spellbooks:dead_king"
                ))
                .setSaveConsumer(newList -> BOSSES.set(newList))
                .setTooltip(Component.literal("Add mob IDs in the format modid:mobid, for example, twilightforest:naga"))
                .setExpanded(true)
                .build());

        return builder.build();
    }
}