package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.Base64;

public class JsonArrayWrapper extends ColumnWrapper<JsonArray> {

    JsonArrayWrapper() {
        super(JsonArray.class);
    }

    @Override
    public String serialize(JsonArray object) {
        return Base64.getEncoder().encodeToString(object.toString().getBytes());
    }

    @Override
    public JsonArray deserialize(String string) {
        String decoded = new String(Base64.getDecoder().decode(string));
        return new JsonParser().parse(decoded).getAsJsonArray();
    }
}
