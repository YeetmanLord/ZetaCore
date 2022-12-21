package com.github.yeetmanlord.reflection_api.world;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.block.NMSBlockPositionReflection;
import com.github.yeetmanlord.reflection_api.exceptions.FieldReflectionExcpetion;
import org.bukkit.Location;

import java.lang.reflect.Constructor;

public class NMSVec3DReflection extends NMSObjectReflection {

    public NMSVec3DReflection(double x, double y, double z) {

        super(init(x, y, z));

    }

    public NMSVec3DReflection(Location loc) {

    	this(loc.getX(), loc.getY(), loc.getZ());

    }

    public NMSVec3DReflection(NMSBlockPositionReflection blockPos) throws FieldReflectionExcpetion {

    	this(blockPos.x.get(), blockPos.y.get(), blockPos.z.get());

    }

    private static Object init(double x, double y, double z) {

        try {
            Constructor<?> constr = ReflectionApi.getNMSClass("Vec3D").getConstructor(double.class, double.class, double.class);
            return constr.newInstance(x, y, z);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass("Vec3D");

    public static final NMSVec3DReflection ZERO = new NMSVec3DReflection(0, 0, 0);
}
