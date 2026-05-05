package org.icarus.nsvutils.serializer;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Base64;

public class ItemStackSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack item_stack, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("item", Base64.getEncoder().encodeToString(item_stack.serializeAsBytes()));
        return json;
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(obj.get("data").toString()));
    }
}