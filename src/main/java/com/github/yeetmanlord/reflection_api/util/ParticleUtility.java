package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Method;

public class ParticleUtility {

    public static void spawnParticle(Location location, String particle, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
        World.Spigot spigotWorld = location.getWorld().spigot();
        if (ReflectionApi.version.isOlder("1.13")) {
            try {
                Method playEffect = spigotWorld.getClass().getMethod("playEffect", Location.class, Effect.class, int.class, int.class, float.class, float.class, float.class, float.class, int.class, int.class);
                playEffect.invoke(spigotWorld, location, Effect.valueOf(particle), 0, 0, offsetX, offsetY, offsetZ, speed, particleCount, radius);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else {
            World world = location.getWorld();
            try {
                Method spawnParticle = world.getClass().getMethod("spawnParticle", Class.forName("org.bukkit.Particle"), double.class, double.class, double.class, int.class, double.class, double.class, double.class, double.class);
                spawnParticle.invoke(world, Class.forName("org.bukkit.Particle").getField(particle).get(null), location.getX(), location.getY(), location.getZ(), particleCount, offsetX, offsetY, offsetZ, speed);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

}
