package org.icarus.nsvutils.sudoutils;

import java.util.UUID;

public class Entry {
    public String time;
    public UUID user_uuid;
    public String player_name;
    public String command;

    public Entry(String time, UUID user_uuid, String player_name, String command) {
        this.time = time;
        this.user_uuid = user_uuid;
        this.player_name = player_name;
        this.command = command;
    }

    public String toString() {
        return this.time + " " +
                this.player_name + " " +
                this.command;
    }
}
