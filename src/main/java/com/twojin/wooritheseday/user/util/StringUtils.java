package com.twojin.wooritheseday.user.util;

import java.util.Random;
import java.util.UUID;

public class StringUtils {

    public static String createRandomString() {
        int randomStrLen = 16;
        Random random = new Random();
        StringBuilder ramdomBuf = new StringBuilder();

        for (int i = 0; i < randomStrLen; i++) {
            if (random.nextBoolean()) {

                // 26 : a-z 알파뱃 개수
                // 97 : letter 'a' 아스키 코드
                // (int) (random.nextInt(26))+97 : 랜덤 소문자 아스키 코드
                ramdomBuf.append((char) ((int) (random.nextInt(26)) + 97));
            } else {
                ramdomBuf.append(random.nextInt(10));
            }
        }

        String randomStr = ramdomBuf.toString();
        return randomStr;
    }

    public static String createRandomUUid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
