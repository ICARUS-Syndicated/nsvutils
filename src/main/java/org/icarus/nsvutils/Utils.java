package org.icarus.nsvutils;

import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@SuppressWarnings("unused")
public class Utils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }

    public static String getDate() {
        return String.valueOf(LocalDate.now());
    }

    public static String serializeItemStack(ItemStack itemStack) {
        return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes());
    }

    public static ItemStack deserializeItemStack(String encoded) {
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(encoded));
    }
}
