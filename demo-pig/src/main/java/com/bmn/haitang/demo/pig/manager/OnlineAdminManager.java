package com.bmn.haitang.demo.pig.manager;

import com.bmn.haitang.demo.pig.doman.Admin;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/19
 */
public enum OnlineAdminManager {

    INSTANCE;

    private OnlineAdminManager(){}


    private Map<String, Admin> adminMap = new ConcurrentHashMap<>();


    public void online(String sessionId, Admin admin) {
        adminMap.put(sessionId, admin);
    }

    public void offline(String sessionId) {
        adminMap.remove(sessionId);
    }

    public Admin getOnlineAdmin(String sessionId) {
        return adminMap.get(sessionId);
    }
}
