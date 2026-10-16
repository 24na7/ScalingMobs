package org.okunev.scalingmobs;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.okunev.scalingmobs.Utils.BossUtils;

import java.util.*;

@Mod.EventBusSubscriber(
        modid = ScalingMobsMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class BossScaler {

    private static final Set<UUID> activeBosses = new HashSet<>();
    private static final Map<UUID, Double> bossMaxScales = new HashMap<>();

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        LivingEntity entity = event.getEntity();

        if (!(entity.level() instanceof ServerLevel)) return;

        if (BossUtils.isBoss(entity)) {
            activeBosses.add(entity.getUUID());
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel world)) return;

        MinecraftServer server = world.getServer();
        if (server == null) return;

        Iterator<UUID> iterator = activeBosses.iterator();

        while (iterator.hasNext()) {
            UUID bossID = iterator.next();
            LivingEntity livingEntity = null;
            for (ServerLevel level : server.getAllLevels()) {
                Entity found = level.getEntity(bossID);
                if (found instanceof LivingEntity le) {
                    livingEntity = le;
                    break;
                }
            }

            if (livingEntity == null) {
                iterator.remove();
                bossMaxScales.remove(bossID);
                continue;
            }

            if (livingEntity.isDeadOrDying()) {
                iterator.remove();
                bossMaxScales.remove(bossID);
                continue;
            }

            int nearbyPlayers = Math.max(
                    1,
                    BossUtils.getNearbyPlayers(livingEntity, 100)
            );

            BossUtils.scaleBoss(
                    livingEntity,
                    nearbyPlayers,
                    bossMaxScales
            );
        }
    }
}