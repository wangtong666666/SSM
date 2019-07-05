package ky.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ky.entity.TSysUser;


public class SessionListener implements HttpSessionListener {
    private static Map<String, HttpSession> sessionOnline = new HashMap<String, HttpSession>();

    public void sessionCreated(HttpSessionEvent se) {
        sessionOnline.put(se.getSession().getId(), se.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        sessionOnline.remove(se.getSession().getId());
    }

    public static Map<String, HttpSession> getSessionOnline() {
        return sessionOnline;
    }

}
