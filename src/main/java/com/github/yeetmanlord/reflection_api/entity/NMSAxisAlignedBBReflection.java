package com.github.yeetmanlord.reflection_api.entity;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import org.bukkit.Location;

import javax.annotation.Nullable;

public class NMSAxisAlignedBBReflection extends NMSObjectReflection {

    private double x1;
    private double y1;
    private double z1;

    private double x2;
    private double y2;
    private double z2;


    public NMSAxisAlignedBBReflection(double x1, double y1, double z1, double x2, double y2, double z2) {
        super("AxisAlignedBB", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class}, new Object[]{x1, y1, z1, x2, y2, z2});
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
        this.z2 = Math.max(z1, z2);
    }

    public NMSAxisAlignedBBReflection(Location loc1, Location loc2) {
        this(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
    }

    public NMSAxisAlignedBBReflection(Object nmsObject) {
        super(nmsObject);
        try {
            this.x1 = (double) this.getFieldFromNmsObject("a");
            this.y1 = (double) this.getFieldFromNmsObject("b");
            this.z1 = (double) this.getFieldFromNmsObject("c");

            this.x2 = (double) this.getFieldFromNmsObject("d");
            this.y2 = (double) this.getFieldFromNmsObject("e");
            this.z2 = (double) this.getFieldFromNmsObject("f");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "NMSAxisAlignedBBReflection{" +
                "x1: " + x1 +
                ", y1: " + y1 +
                ", z1: " + z1 +
                ", x2: " + x2 +
                ", y2: " + y2 +
                ", z2: " + z2 +
                '}';
    }

    public boolean isWithinBoundingBox(double x, double y, double z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    public boolean doesCollide(NMSAxisAlignedBBReflection boundingBox) {
        return this.isWithinBoundingBox(boundingBox.x1, boundingBox.y1, boundingBox.z1) || this.isWithinBoundingBox(boundingBox.x2, boundingBox.y2, boundingBox.z2);
    }

    public boolean isWithinBoundingBox(Location location) {
        return this.isWithinBoundingBox(location.getX(), location.getY(), location.getZ());
    }

    public Location getStarting() {
        return new Location(null, this.x1, this.y1, this.z1);
    }

    public Location getEnding() {
        return new Location(null, this.x2, this.y2, this.z2);
    }

}
