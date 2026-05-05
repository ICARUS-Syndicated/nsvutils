package org.icarus.nsvutils.serializer;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.DoubleChestInventory;

import java.lang.reflect.Type;
import java.util.Base64;

public class DoubleChestInventorySerializer implements JsonSerializer<DoubleChestInventory>, JsonDeserializer<DoubleChestInventory> {
    @Override
    public JsonElement serialize(DoubleChestInventory inventory, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        ItemStack[] contents = inventory.getStorageContents();
        for(int i = 0; i < contents.length; i++) {
            ItemStack st = contents[i];
            if (st != null) {
                json.addProperty("slot" + i, Base64.getEncoder().encodeToString(st.serializeAsBytes()));
            }
        }
        return json;
    }


    @Override
    public DoubleChestInventory deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // TODO Deserializer
        return null;
    }
}
