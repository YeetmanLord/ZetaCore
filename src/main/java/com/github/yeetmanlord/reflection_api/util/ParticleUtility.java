package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Method;

public class ParticleUtility {

    public static void spawnParticle(Location location, ParticleTypes particle, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
        World.Spigot spigotWorld = location.getWorld().spigot();
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_9)) {
            try {
                Method playEffect = World.Spigot.class.getMethod("playEffect", Location.class, Effect.class, int.class, int.class, float.class, float.class, float.class, float.class, int.class, int.class);
                playEffect.invoke(spigotWorld, location, Effect.valueOf(particle.getLegacyName()), 0, 0, offsetX, offsetY, offsetZ, speed, particleCount, radius);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else {
            World world = location.getWorld();
            try {
                Method spawnParticle = world.getClass().getMethod("spawnParticle", Class.forName("org.bukkit.Particle"), double.class, double.class, double.class, int.class, double.class, double.class, double.class, double.class);
                spawnParticle.invoke(world, Class.forName("org.bukkit.Particle").getField(particle.name()).get(null), location.getX(), location.getY(), location.getZ(), particleCount, offsetX, offsetY, offsetZ, speed);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public enum ParticleTypes {
        EXPLOSION_NORMAL("EXPLOSION"),
        EXPLOSION_LARGE,
        EXPLOSION_HUGE,
        FIREWORKS_SPARK,
        WATER_BUBBLE,
        WATER_SPLASH("SPLASH"),
        WATER_WAKE,
        SUSPENDED,
        SUSPENDED_DEPTH("VOID_FOG"),
        CRIT,
        CRIT_MAGIC("MAGIC_CRIT"),
        SMOKE_NORMAL("SMOKE"),
        SMOKE_LARGE("LARGE_SMOKE"),
        SPELL,
        SPELL_INSTANT("INSTANT_SPELL"),
        SPELL_MOB("POTION_SWIRL"),
        SPELL_MOB_AMBIENT("POTION_SWIRL_TRANSPARENT"),
        SPELL_WITCH("WITCH_MAGIC"),
        DRIP_WATER("DRIPWATER"),
        DRIP_LAVA("DRIPLAVA"),
        VILLAGER_ANGRY("VILLAGER_THUNDERCLOUD"),
        VILLAGER_HAPPY("HAPPY_VILLAGER"),
        TOWN_AURA("SMALL_SMOKE"),
        NOTE,
        PORTAL,
        ENCHANTMENT_TABLE("FLYING_GLYPH"),
        FLAME,
        LAVA("LAVA_POP"),
        FOOTSTEP,
        CLOUD,
        REDSTONE,
        SNOWBALL("SNOWBALL_BREAK"),
        SNOW_SHOVEL,
        SLIME,
        HEART,
        BARRIER,
        WATER_DROP,
        ITEM_TAKE,
        MOB_APPEARANCE("MOBSPAWNER_FLAMES"),
        DRAGON_BREATH,
        END_ROD,
        DAMAGE_INDICATOR,
        SWEEP_ATTACK;

        private String legacyName;

        ParticleTypes() {
            this.legacyName = this.name();
        }

        ParticleTypes(String legacyName) {
            this.legacyName = legacyName;
        }

        public String getLegacyName() {
            return legacyName;
        }
    }

}
