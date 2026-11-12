package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {
    public static String generateRandomStringWithLength(int length) {
        return RandomStringUtils.secure().nextAlphanumeric(length);
    }
}
