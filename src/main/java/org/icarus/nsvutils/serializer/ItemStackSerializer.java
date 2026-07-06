package org.icarus.nsvutils.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.bukkit.inventory.ItemStack;
import org.icarus.nsvutils.Utils;

@SuppressWarnings("unused")
public class ItemStackSerializer {
    public static JsonElement serialize(ItemStack itemStack) {
        JsonObject json = new JsonObject();
        json.addProperty("item", Utils.serializeItemStack(itemStack));
        return json;
    }

    public static ItemStack deserialize(JsonElement json) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        return Utils.deserializeItemStack(obj.get("data").toString());
    }
}