package com.github.yeetmanlord.zeta_core.api.uitl.raytrace;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.uitl.DistanceUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Version independent raytracer. Will raytrace from an entities eye location with its pitch and yaw.
 * Will stop on hitting either an entity or a block. If it hits neither it will return an
 * {@link ResultType#EMPTY} with a null entity and block.
 */
public class RayTraceUtility {

    public static BlockRayTraceResult rayTraceBlocks(LivingEntity entity, double maxDistance) {

        Location starting = entity.getEyeLocation();
        Vector direction = starting.getDirection();
        Location check = starting.clone();
        Location last = starting.clone();

        double distanceTraveled = 0;
        while (distanceTraveled < maxDistance) {
            last = check.clone();
            check = getRayTraceLocation(check, direction, 0.05D);
            if (check.getBlock().getType() != Material.AIR) {
                break;
            }
            ZetaCore.LOGGER.info("Raytracing at " + check);
            distanceTraveled = DistanceUtils.getDistance(starting, check);
        }

        Block block = check.getBlock();
        BlockFace face = block.getFace(last.getBlock());

        ZetaCore.LOGGER.info(block, last.getBlock());

        if (block.getType() == Material.AIR) {
            return new BlockRayTraceResult(ResultType.EMPTY, block, face);
        }
        return new BlockRayTraceResult(ResultType.BLOCK, block, face);
    }

    public static Location getRayTraceLocation(Location starting, Vector direction, double distance) {
        Location ending = starting.clone().add(direction.clone().multiply(distance));
        Vector perpendicular = direction.clone().crossProduct(new Vector(0, 1, 0)).multiply(2.0);
        Location actualEnding = ending.clone().add(perpendicular);
        return actualEnding;
    }

}
