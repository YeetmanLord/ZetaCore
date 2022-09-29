package com.github.yeetmanlord.zeta_core.api.uitl.raytrace;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockRayTraceResult extends RayTraceResult {

    private Block block;

    private BlockFace face;

    public BlockRayTraceResult(ResultType type, Block block, BlockFace face) {
        super(type, block);
        this.block = block;
        this.face = face;
    }

    public Block getBlock() {
        return block;
    }

    public BlockFace getFace() {
        return face;
    }

    @Override
    public String toString() {
        return "BlockRayTraceResult{" +
                "block: " + block +
                ", face: " + face +
                '}';
    }
}
