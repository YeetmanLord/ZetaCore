package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Base64;

public class JsonObjectWrapper extends ColumnWrapper<JsonObject> {
    public JsonObjectWrapper() {
        super(JsonObject.class);
    }

    @Override
    public String serialize(JsonObject object) {
        return Base64.getEncoder().encodeToString(object.toString().getBytes());
    }

    @Override
    public JsonObject deserialize(String string) {
        String decoded = new String(Base64.getDecoder().decode(string));
        return new JsonParser().parse(decoded).getAsJsonObject();
    }
}
