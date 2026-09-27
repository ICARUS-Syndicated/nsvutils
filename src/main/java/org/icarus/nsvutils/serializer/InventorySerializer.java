package org.icarus.nsvutils.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.icarus.nsvutils.Utils;

import java.util.Map;

@SuppressWarnings("unused")
public class InventorySerializer {
    public static JsonElement serialize(Inventory inventory) {
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
                slots.addProperty(String.valueOf(i), Utils.serializeItemStack(stack));
            }
        }

        root.add("slots", slots);
        return root;
    }


    public static Inventory deserialize(JsonElement json) throws JsonParseException {
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
            inventory.setItem(Integer.parseInt(entry.getKey()), Utils.deserializeItemStack(entry.getValue()
                    .getAsString()));
        }

        return inventory;
    }
}
