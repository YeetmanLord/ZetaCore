package com.github.yeetmanlord.reflection_api.entity;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.google.common.collect.ImmutableMap;

public class NMSEntityReflection extends NMSObjectReflection {

    private NMSDataWatcherReflection dataWatcher;

    public NMSEntityReflection(Entity entity) {

        super(entity, "getHandle");
        this.dataWatcher = new NMSDataWatcherReflection(this);

    }

    public NMSEntityReflection(Object nmsEntity) {

        super(nmsEntity);

        if (staticClass.isInstance(nmsEntity)) {
            this.dataWatcher = new NMSDataWatcherReflection(this);
        } else {
            throw (new IllegalArgumentException(nmsEntity.toString() + " is not an instance of net.minecraft.server.Entity"));
        }

    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {

        try {
            Mappings.ENTITY_SET_LOCATION_MAPPING.runMethod(this, x, y, z, yaw, pitch);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setLocation(Location location) {

        try {
            this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Location getLocation() {
        try {
            Entity e = (Entity) invokeMethodForNmsObject("getBukkitEntity");
            return e.getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NMSDataWatcherReflection getDataWatcher() {

        return this.dataWatcher;

    }

    public Object getNmsEntity() {

        return this.nmsObject;

    }

    public int getId() {

        try {
            Entity e = (Entity) invokeMethodForNmsObject("getBukkitEntity");
            return e.getEntityId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public String getName() {

        try {
            Entity e = (Entity) invokeMethodForNmsObject("getBukkitEntity");
            return e.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public String toString() {

        HashMap<String, Object> values = new HashMap<>();
        values.put("type", nmsObject.getClass());
        values.put("entity", this.nmsObject);

        values.put("location", ImmutableMap.of("x", this.getLocation().getX(), "y", this.getLocation().getY(), "z", this.getLocation().getZ()));

        return "EntityReflection" + values;

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.ENTITY_PACKAGE_MAPPING, "Entity");

    public static NMSEntityReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSEntityReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSEntityReflection");

    }

    public NMSAxisAlignedBBReflection getBoundingBox() {

        try {
            return new NMSAxisAlignedBBReflection(Mappings.ENTITY_GET_BOUNDING_BOX_MAPPING.runMethod(this));
        } catch (MappingsException e) {
            e.printStackTrace();
        }

        return null;

    }

}
