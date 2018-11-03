package cn.louyu.utils;

public class TextUtils {
    private TextUtils() {
        throw new AssertionError();
    }
    public static boolean isNullOrEmpty(String txt){
        return txt==null||txt.length()<=0;
    }
}
