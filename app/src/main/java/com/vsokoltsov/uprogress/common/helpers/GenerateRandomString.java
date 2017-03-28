package com.vsokoltsov.uprogress.common.helpers;

import java.util.Random;

/**
 * Created by vsokoltsov on 28.03.17.
 */

public class GenerateRandomString {
    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }

}
