package cn.louyu.utils;

import java.util.UUID;

public class UUIDUtils {
    private UUIDUtils() {
        throw new AssertionError();
    }
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
