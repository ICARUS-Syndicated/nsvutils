package org.icarus.nsvutils.sudoutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordTable {
    public static void recordTableInit(Connection conn) {
        try {
            PreparedStatement statement = conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS entries(
                      time TINYTEXT NOT NULL,
                      uuid TINYTEXT NOT NULL,
                      player_name TINYTEXT NOT NULL,
                      command MEDIUMTEXT NOT NULL
                    );""");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while initializing recording table.");
        }
    }

    public static void addEntry(Connection conn, Entry entry) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO entries VALUES(?,?,?,?)");
            statement.setString(1, entry.time);
            statement.setString(2, String.valueOf(entry.user_uuid));
            statement.setString(3, entry.player_name);
            statement.setString(4, entry.command);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while inserting data.");
        }
    }

    public static String[] searchEntry(Connection conn, UUID uuid, int limit) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM entries WHERE uuid=? LIMIT ?");
            statement.setString(1, String.valueOf(uuid));
            statement.setInt(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<String> entries = new ArrayList<>();
                while (resultSet.next()) {
                    entries.add(resultSet.getString("time") + " " +
                            resultSet.getString("player_name") + " " +
                            resultSet.getString("command")
                    );
                }
                return (String[]) entries.toArray();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting keypair.");
        }
    }
}
