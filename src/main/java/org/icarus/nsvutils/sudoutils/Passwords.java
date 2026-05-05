package org.icarus.nsvutils.sudoutils;

import java.util.regex.*;

public class Passwords {
    static final Pattern PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*_).{10,}$");
    public static boolean isValidPassword(String pwd) {
        if (pwd == null) return false;
        return PATTERN.matcher(pwd).matches();
    }
}
