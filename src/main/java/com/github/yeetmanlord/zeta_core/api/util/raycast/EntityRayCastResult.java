package com.github.yeetmanlord.zeta_core.api.util.raycast;

import org.bukkit.entity.Entity;

public class EntityRayCastResult extends RayCastResult {

    private Entity entity;

    public EntityRayCastResult(ResultType type, Entity entity) {
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
