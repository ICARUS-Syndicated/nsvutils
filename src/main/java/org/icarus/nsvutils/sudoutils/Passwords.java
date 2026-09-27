package org.icarus.nsvutils.sudoutils;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Passwords {
    static final Pattern PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*_).{10,}$");

    public static boolean isValidPassword(String pwd) {
        return pwd != null && PATTERN.matcher(pwd).matches();
    }
}
