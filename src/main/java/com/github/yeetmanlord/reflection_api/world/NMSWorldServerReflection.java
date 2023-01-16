package com.github.yeetmanlord.reflection_api.world;

import java.lang.reflect.Method;
import java.util.*;

import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import org.bukkit.World;
import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.NMSEntityReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.server.NMSServerReflection;

public class NMSWorldServerReflection extends NMSObjectReflection {

    private NMSServerReflection server;

    public NMSWorldServerReflection(World bukkitWorld) {

        super(bukkitWorld, "getHandle");
        this.server = new NMSServerReflection(this);

    }

    public NMSWorldServerReflection(Object nmsObject) {

        super(nmsObject);

    }

    public Object getNmsWorldServer() {

        return nmsObject;

    }

    public Object getNMSServer() {

        try {
            return Mappings.WORLD_SERVER_GET_SERVER_MAPPING.runMethod(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public NMSServerReflection getServer() {

        return server;

    }

    /**
     * Gets the list of entities for a server world
     *
     * @return The entity list of a server but as reflected entities
     */
    public List<NMSEntityReflection> entityList() {

        List<NMSEntityReflection> reflectedEntityList = new ArrayList<>();
        List<Object> entityList;

        try {
            if (ReflectionApi.version.isNewer(ReflectionApi.v1_17)) {
                Iterable<?> entityIterator = Mappings.WORLD_SERVER_ENTITY_ITERABLE_MAPPING.runMethod(this);
                for (Object entity : entityIterator) {
                    reflectedEntityList.add(new NMSEntityReflection(entity));
                }
                return reflectedEntityList;
            } else if (ReflectionApi.version.isNewer(ReflectionApi.v1_16)) {
                entityList = new ArrayList<>(((Map<UUID, Object>) Mappings.WORLD_SERVER_ENTITY_LIST_MAPPING.getField(this)).values());
            } else {
                entityList = (List<Object>) Mappings.WORLD_SERVER_ENTITY_LIST_MAPPING.getField(this);
            }

            for (Object o : entityList) {
                NMSEntityReflection entity = new NMSEntityReflection(o);

                if (NMSPlayerReflection.isInstance(entity)) {
                    reflectedEntityList.add(new NMSPlayerReflection(o));
                } else reflectedEntityList.add(entity);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reflectedEntityList;

    }

    public void addEntity(NMSEntityReflection entity) {

        try {
            Mappings.WORLD_SERVER_ADD_ENTITY_MAPPING.runMethod(this, entity.getNmsEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeEntity(NMSEntityReflection entity) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_17)) {
                this.invokeMethodForNmsObject("removeEntity", new Class[]{NMSEntityReflection.staticClass}, new Object[]{entity.getNmsEntity()});
            } else {
                Class<?> removalReasonClass = ReflectionApi.getNMSClass("world.entity.Entity$RemovalReason");
                Object removalReason = removalReasonClass.getEnumConstants()[1];
                entity.invokeMethodForNmsObject("a", new Class[]{removalReasonClass}, new Object[]{removalReason});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {

        HashMap<String, Object> values = new HashMap<>();
        values.put("type", nmsObject.getClass());
        values.put("worldServer", nmsObject);
        values.put("server", server.toString());
        return "WorldServerReflection" + values;

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.SERVER_LEVEL_PACKAGE_MAPPING, "WorldServer");

    public static NMSWorldServerReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSWorldServerReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSWorldServerReflection");

    }

}