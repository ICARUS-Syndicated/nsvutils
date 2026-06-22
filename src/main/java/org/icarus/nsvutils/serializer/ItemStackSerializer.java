package org.icarus.nsvutils.serializer;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Base64;

import org.icarus.nsvutils.Utils;

public class ItemStackSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack item_stack, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("item", Utils.serializeItemStack(item_stack));
        return json;
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        return Utils.deserializeItemStack(obj.get("data").toString());
    }
}