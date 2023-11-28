package com.twojin.wooritheseday.user.util;

public class PartnerUtils {

    public static String  createPartnerQueueCode() {

        String randomCd = StringUtils.createRandomString();
        return randomCd;
    }

}
