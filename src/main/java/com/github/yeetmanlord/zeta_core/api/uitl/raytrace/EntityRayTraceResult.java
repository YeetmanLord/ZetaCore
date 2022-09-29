package com.github.yeetmanlord.zeta_core.api.uitl.raytrace;

import org.bukkit.entity.Entity;

public class EntityRayTraceResult extends RayTraceResult {

    private Entity entity;

    public EntityRayTraceResult(ResultType type, Entity entity) {
        super(type, entity);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "EntityRayTraceResult{" +
                "entity: " + entity +
                '}';
    }
}
