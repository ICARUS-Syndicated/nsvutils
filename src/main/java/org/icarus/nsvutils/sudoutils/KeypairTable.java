package org.icarus.nsvutils.sudoutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class KeypairTable {
    public static void keypairTableInit(Connection conn) {
        try {
            PreparedStatement statement = conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS keypair(
                      uuid TINYTEXT NOT NULL,
                      passkey MEDIUMTEXT NOT NULL
                    );""");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while initializing keypair table.");
        }
    }

    public static void addKeypair(Connection conn, UUID uuid, String passkey) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO keypair VALUES(?,?)");
            statement.setString(1, String.valueOf(uuid));
            statement.setString(2, passkey);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while inserting keypair.");
        }
    }

    public static void delKeypair(Connection conn, UUID uuid) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM keypair WHERE uuid=?");
            statement.setString(1, String.valueOf(uuid));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while removing keypair.");
        }
    }

    public static String getPasskeyOf(Connection conn, UUID uuid) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM keypair WHERE uuid=? LIMIT 1");
            statement.setString(1, String.valueOf(uuid));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getString("passkey") : "";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting keypair.");
        }
    }
}
