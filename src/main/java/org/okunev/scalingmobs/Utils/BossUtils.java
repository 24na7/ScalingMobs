package org.okunev.scalingmobs.Utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.okunev.scalingmobs.config.ScalingMobsConfig;

import java.util.*;
import java.util.stream.Collectors;

public class BossUtils {
    private static final Map<UUID, Double> originalHealth = new HashMap<>();
    private static final Map<UUID, Double> originalDamage = new HashMap<>();
    private static final Map<UUID, Double> originalArmor = new HashMap<>();
    private static final Map<UUID, Integer> lastPlayerCount = new HashMap<>();

    public static Set<ResourceLocation> getBosses() {
        return ScalingMobsConfig.BOSSES.get().stream()
                .map(ResourceLocation::new)
                .collect(Collectors.toSet());
    }

    public static boolean isBoss(LivingEntity entity) {
        ResourceLocation entityID = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return getBosses().contains(entityID);
    }

    public static int getNearbyPlayers(LivingEntity boss, double radius) {
        return (int) boss.level().players().stream()
                .filter(player -> player.distanceTo(boss) <= radius)
                .count();
    }

    public static void scaleBoss(
            LivingEntity entity,
            int nearbyPlayers,
            Map<UUID, Double> bossMaxScales
    ) {
        UUID bossID = entity.getUUID();

        if (bossMaxScales.containsKey(bossID)) {
            return;
        }

        nearbyPlayers = Math.max(1, nearbyPlayers);
        double maxScale = Math.sqrt(nearbyPlayers);
        bossMaxScales.put(bossID, maxScale);
        AttributeInstance healthAttr =
                entity.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance damageAttr =
                entity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance armorAttr =
                entity.getAttribute(Attributes.ARMOR);

        if (healthAttr == null) return;
        double newHealth = healthAttr.getBaseValue() * maxScale;
        healthAttr.setBaseValue(newHealth);
        entity.setHealth((float) newHealth);
        if (damageAttr != null) {
            double newDamage = damageAttr.getBaseValue() * maxScale;
            damageAttr.setBaseValue(newDamage);
        }

        if (armorAttr != null) {
            double baseArmor = armorAttr.getBaseValue();

            if (baseArmor > 0) {
                armorAttr.setBaseValue(baseArmor * maxScale);
            } else {
                double newArmor = 5 + Math.sqrt(nearbyPlayers);
                armorAttr.setBaseValue(newArmor);
            }
        }
    }
}