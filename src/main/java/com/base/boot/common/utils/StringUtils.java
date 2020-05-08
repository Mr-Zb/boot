package com.base.boot.common.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.UUID;

/**
 * @author zbang
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String systemUuid() {
        return String.valueOf(UUID.randomUUID());
    }

    public static String uuid() {
        return systemUuid().replace("-", "");
    }

    public static String shortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            String str = uuid().substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[(x % 62)]);
        }
        return shortBuffer.toString();
    }

    public static Boolean isUserName(String userName) {
        if (isBlank(userName)) {
            return Boolean.valueOf(false);
        }

        if ((length(userName) < 4) || (length(userName) > 18)) {
            return Boolean.valueOf(false);
        }

        if (!userName.matches("^[0-9a-zA-Z\\.\\@\\-\\_]+$")) {
            return Boolean.valueOf(false);
        }

        if (userName.matches("^[0-9]+$")) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }

    public static Boolean isUserPwd(String userPwd) {
        if (isBlank(userPwd)) {
            return Boolean.valueOf(false);
        }

        if ((length(userPwd) < 6) || (length(userPwd) > 20)) {
            return Boolean.valueOf(false);
        }
        if (NumberUtils.isDigits(userPwd)) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }

    public static String replaceSubString(String str) {
        String sub = "******";
        sub += str.substring(str.length() - 6);
        return sub;
    }
}

