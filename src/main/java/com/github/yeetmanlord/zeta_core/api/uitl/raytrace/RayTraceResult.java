package com.github.yeetmanlord.zeta_core.api.uitl.raytrace;

import org.bukkit.block.Block;

public class RayTraceResult {

    private ResultType type;

    private Object object;

    public RayTraceResult(ResultType type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Object get() {
        return object;
    }
    public ResultType getType() {
        return type;
    }

    public boolean isEmpty() {
        return type == ResultType.EMPTY;
    }

    @Override
    public String toString() {
        return "RayTraceResult{" +
                "type: " + type +
                ", object: " + object +
                '}';
    }
}
