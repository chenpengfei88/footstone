package org.footstone.utils;

import java.util.List;

/**
 * Created by chenpengfei on 2017/5/22.
 */
public class ObjectUtils {

    public static boolean isEmpty(String text) {
        if (text == null || text.equals("null") || text.length() == 0)
            return true;
        else
            return false;
    }

    public static String formatText(String text) {
        if (text == null || text.equals("null")) {
            return "";
        }
        return text;
    }

    public static boolean isEquals(String text1, String text2) {
        return (text1 == null) ? (text2 == null) : text1.equals(text2);
    }

    public static <T> boolean isNullZero(List<T> list) {
        return list == null || list.size() == 0 ? true : false;
    }

    public static <T> boolean isGetPositionDataByList(List<T> list, int position) {
        return !isNullZero(list) && list.size() > position ? true : false;
    }




}
