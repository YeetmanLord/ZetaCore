package com.github.yeetmanlord.zeta_core.api.uitl;

import org.bukkit.entity.Entity;

/**
 * Version independent raytracer. Will raytrace from an entities eye location with its pitch and yaw.
 * Will stop on hitting either an entity or a block. If it hits neither it will return an
 * {@link ResultType#EMPTY} with a null entity and block.
 */
public class EntityRayTrace {

    private Entity entity;

    private double maxDistance;

    public EntityRayTrace(Entity entity, double maxDistance) {
        this.entity = entity;
        this.maxDistance = maxDistance;
    }

    public Result trace() {
        return trace(entity, maxDistance);
    }

    public static void trace(Entity entity, double maxDistance, Result result) {

    }

}
