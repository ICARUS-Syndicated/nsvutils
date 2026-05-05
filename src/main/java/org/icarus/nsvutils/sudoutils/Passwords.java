package org.icarus.nsvutils.sudoutils;

import java.util.regex.*;

public class Passwords {
    public static boolean isValidPassword(String pwd) {
        Pattern password = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*_).{10,}$");
        if (pwd == null) return false;
        return password.matcher(pwd).matches();
    }
}
