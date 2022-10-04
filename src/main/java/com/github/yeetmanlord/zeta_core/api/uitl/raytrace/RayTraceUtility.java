package com.github.yeetmanlord.zeta_core.api.uitl.raytrace;

import com.github.yeetmanlord.reflection_api.entity.NMSEntityReflection;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.uitl.DistanceUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            check = getRayTraceLocation(check, direction, 0.25D);
            if (check.getBlock().getType() != Material.AIR) {
                break;
            }
            distanceTraveled = DistanceUtils.getDistance(starting, check);
        }

        Block block = check.getBlock();
        BlockFace face = block.getFace(last.getBlock());


        if (block.getType() == Material.AIR) {
            return new BlockRayTraceResult(ResultType.EMPTY, block, face);
        }
        return new BlockRayTraceResult(ResultType.BLOCK, block, face);
    }

    public static EntityRayTraceResult rayTraceEntities(LivingEntity entity, double maxDistance) {
        Location starting = entity.getEyeLocation();
        Vector direction = starting.getDirection();
        Location check = starting.clone();
        List<Entity> entityList = new ArrayList<>(entity.getNearbyEntities(maxDistance + 0.5, maxDistance + 0.5, maxDistance + 0.5)).stream().filter(e -> e != entity).collect(Collectors.toList());
        Entity hitResult = null;
        double distanceTraveled = 0;
        while (distanceTraveled < maxDistance) {
            check = getRayTraceLocation(check, direction, 0.01D);
            if (check.getBlock().getType() != Material.AIR) {
                break;
            }
            List<Entity> results = new ArrayList<>();
            for (Entity e : entityList) {
                if (e.equals(entity)) {
                    continue;
                }
                if (new NMSEntityReflection(e).getBoundingBox().isWithinBoundingBox(check)) {
                    results.add(e);
                }
            }
            if (results.size() > 0) {
                Entity closest = results.get(0);
                for (int i = 1; i < results.size(); i++) {
                    if (results.get(i).getLocation().distance(check) < closest.getLocation().distance(check)) {
                        closest = results.get(i);
                    }
                }
                hitResult = closest;
                break;
            }
            distanceTraveled = DistanceUtils.getDistance(starting, check);
        }

        if (hitResult == null) {
            return new EntityRayTraceResult(ResultType.EMPTY, null);
        }
        return new EntityRayTraceResult(ResultType.ENTITY, hitResult);

    }

    public static RayTraceResult raytrace(LivingEntity entity, double maxDistance) {
        EntityRayTraceResult entityResult = rayTraceEntities(entity, maxDistance);
        BlockRayTraceResult blockResult = rayTraceBlocks(entity, maxDistance);
        if (entityResult.getType() != ResultType.EMPTY) {
            return entityResult;
        }
        if (blockResult.getType() != ResultType.EMPTY) {
            return blockResult;
        }
        return new RayTraceResult(ResultType.EMPTY, null);
    }

    public static Location getRayTraceLocation(Location starting, Vector direction, double distance) {
        Location ending = starting.clone().add(direction.clone().multiply(distance));
        return ending;
    }

}
