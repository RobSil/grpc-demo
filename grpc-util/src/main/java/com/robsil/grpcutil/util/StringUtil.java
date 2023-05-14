package com.robsil.grpcutil.util;

public class StringUtil {

    public static void notEmpty(String str, String message) {
        if (str == null) {
            throw new IllegalArgumentException(message);
        }

        if (str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

}
