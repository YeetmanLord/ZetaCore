package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONObject;

import java.util.Base64;

public class LocationWrapper extends ColumnWrapper<Location> {
    public LocationWrapper() {
        super(Location.class);
    }

    @Override
    public String serialize(Location object) {
        JsonObject loc = new JsonObject();
        loc.addProperty("world", object.getWorld().getName());
        loc.addProperty("x", object.getX());
        loc.addProperty("y", object.getY());
        loc.addProperty("z", object.getZ());
        loc.addProperty("yaw", object.getYaw());
        loc.addProperty("pitch", object.getPitch());
        return Base64.getEncoder().encodeToString(loc.toString().getBytes());
    }

    @Override
    public Location deserialize(String string) {
        String decoded = new String(Base64.getDecoder().decode(string));
        JsonObject loc = new JsonParser().parse(decoded).getAsJsonObject();
        return new Location(Bukkit.getWorld(loc.get("world").getAsString()), loc.get("x").getAsDouble(), loc.get("y").getAsDouble(), loc.get("z").getAsDouble(), loc.get("yaw").getAsFloat(), loc.get("pitch").getAsFloat());
    }
}
