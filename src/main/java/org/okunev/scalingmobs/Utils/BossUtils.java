package org.okunev.scalingmobs.Utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.okunev.scalingmobs.config.ScalingMobsConfig;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("removal")
public class BossUtils {
    private static final String ATTRIBUTE_MODIFIER_NAME = "ScalingMobs boss scaling";
    private static final UUID BOSS_HEALTH_ID = UUID.fromString("5c9f1a20-0000-4000-8000-000000000001");
    private static final UUID BOSS_DAMAGE_ID = UUID.fromString("5c9f1a20-0000-4000-8000-000000000002");
    private static final UUID BOSS_ARMOR_ID  = UUID.fromString("5c9f1a20-0000-4000-8000-000000000003");
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
        applyScale(healthAttr, BOSS_HEALTH_ID, maxScale);

        if (damageAttr != null) {
            applyScale(damageAttr, BOSS_DAMAGE_ID, maxScale);
        }

        if (armorAttr != null) {
            if (armorAttr.getValue() > 0) {
                applyScale(armorAttr, BOSS_ARMOR_ID, maxScale);
            } else {
                applyAddition(armorAttr, BOSS_ARMOR_ID, 5 + Math.sqrt(nearbyPlayers));
            }
        }

        entity.setHealth((float) entity.getMaxHealth());
    }

    private static void applyScale(AttributeInstance attr, UUID modifierID, double maxScale) {
        attr.removeModifier(modifierID);
        if (maxScale > 1.0) {
            attr.addPermanentModifier(new AttributeModifier(
                    modifierID,
                    ATTRIBUTE_MODIFIER_NAME,
                    maxScale - 1.0,
                    AttributeModifier.Operation.MULTIPLY_TOTAL
            ));
        }
    }

    private static void applyAddition(AttributeInstance attr, UUID modifierID, double amount) {
        attr.removeModifier(modifierID);
        if (amount > 0.0) {
            attr.addPermanentModifier(new AttributeModifier(
                    modifierID,
                    ATTRIBUTE_MODIFIER_NAME,
                    amount,
                    AttributeModifier.Operation.ADDITION
            ));
        }
    }
}