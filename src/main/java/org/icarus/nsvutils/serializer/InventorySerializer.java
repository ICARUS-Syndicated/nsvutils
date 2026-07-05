package org.icarus.nsvutils.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Map;

public class InventorySerializer implements JsonSerializer<Inventory>, JsonDeserializer<Inventory> {
    @Override
    public JsonElement serialize(Inventory inventory, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject root = new JsonObject();
        root.addProperty("type", inventory.getType().name());

        if (inventory.getType() == InventoryType.CHEST) {
            root.addProperty("size", inventory.getSize());
        }

        JsonObject slots = new JsonObject();
        ItemStack[] contents = inventory.getStorageContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack stack = contents[i];
            if (stack != null && !stack.getType().isAir()) {
                slots.addProperty(String.valueOf(i), Base64.getEncoder().encodeToString(stack.serializeAsBytes()));
            }
        }

        root.add("slots", slots);
        return root;
    }


    @Override
    public Inventory deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();
        Inventory inventory;

        InventoryType invType = InventoryType.valueOf(root.get("type").getAsString());
        if (invType == InventoryType.CHEST) {
            int size = root.get("size").getAsInt();
            inventory = Bukkit.createInventory(null, size);
        } else {
            inventory = Bukkit.createInventory(null, invType);
        }

        JsonObject slots = root.getAsJsonObject("slots");
        for (Map.Entry<String, JsonElement> entry : slots.entrySet()) {
            inventory.setItem(Integer.parseInt(entry.getKey()), ItemStack.deserializeBytes(Base64.getDecoder()
                    .decode(entry.getValue().getAsString())));
        }

        return inventory;
    }
}
