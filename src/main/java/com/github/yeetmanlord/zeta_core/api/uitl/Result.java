package com.github.yeetmanlord.zeta_core.api.uitl;

import org.bukkit.block.Block;

public class Result {

    private ResultType type;

    private Object object;

    public Result(ResultType type) {

    }

    public Block getBlock() {
        if (type == ResultType.BLOCK) {
            return (Block) object;
        }
        return null;
    }

    public Entity getEntity() {
        if (type == ResultType.ENTITY) {
            return (Entity) object;
        }
        return null;
    }

    public ResultType getType() {
        return type;
    }

    public boolean isEmpty() {
        return type == ResultType.EMPTY;
    }

}
