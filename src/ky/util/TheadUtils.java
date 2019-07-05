package ky.util;

import ky.entity.TSysUser;

public class TheadUtils {
    private static ThreadLocal<TSysUser> history = new ThreadLocal<TSysUser>();
    private static ThreadLocal<String> ipthread = new ThreadLocal<String>();

    public static void clear() {
        history.remove();
    }

    public static void put(TSysUser user) {
        if (history.get() == null) {
            history.set(user);
        }
    }

    public static TSysUser get() {
        return history.get();
    }

    public static void ipPut(String ip) {
        if (ipthread.get() == null) {
            ipthread.set(ip);
        }
    }

    public static String ipGet() {
        return ipthread.get();
    }
}
