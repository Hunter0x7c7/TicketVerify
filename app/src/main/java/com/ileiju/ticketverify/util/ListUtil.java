package com.ileiju.ticketverify.util;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public class ListUtil {

    /**
     * 判断是否为空
     *
     * @param list 判断的集合
     * @return true为空，false为非空
     */
    public static boolean isEmpty(List list) {
        boolean isEmpty = true;
        if (list != null) {
            if (list.size() > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param list 判断的集合
     * @return true为空，false为非空
     */
    public static boolean isEmpty(Map list) {
        boolean isEmpty = true;
        if (list != null) {
            if (list.size() > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param list 判断的集合
     * @return true为空，false为非空
     */
    public static boolean isEmpty(Set list) {
        boolean isEmpty = true;
        if (list != null) {
            if (list.size() > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(Object[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            if (obj.length > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(int[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            if (obj.length > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(byte[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            if (obj.length > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(JSONArray obj) {
        boolean isEmpty = true;
        if (obj != null) {
            if (obj.length() > 0) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }
}
