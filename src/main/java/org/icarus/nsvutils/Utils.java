package org.icarus.nsvutils;

import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class Utils {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }

    public static String getDate() {
        return String.valueOf(LocalDate.now());
    }

    public static String serializeItemStack(ItemStack item_stack){
        return Base64.getEncoder().encodeToString(item_stack.serializeAsBytes());
    }
    public static ItemStack deserializeItemStack(String base64code){
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(base64code));
    }
}
