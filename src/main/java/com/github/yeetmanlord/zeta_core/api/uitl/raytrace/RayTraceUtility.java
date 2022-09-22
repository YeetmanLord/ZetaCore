package com.github.yeetmanlord.zeta_core.api.uitl.raytrace;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

/**
 * Version independent raytracer. Will raytrace from an entities eye location with its pitch and yaw.
 * Will stop on hitting either an entity or a block. If it hits neither it will return an
 * {@link ResultType#EMPTY} with a null entity and block.
 */
public class RayTraceUtility {

    public static BlockRayTraceResult rayTraceBlocks(LivingEntity entity, double maxDistance) {

        Location starting = entity.getEyeLocation();
        Location current = starting.clone();
        float pitch = starting.getPitch();
        float yaw = starting.getYaw();

        double distanceTraveled = 0;
        while (distanceTraveled < maxDistance) {
            current = current.multiply(1.01D);

        }


    }

}
