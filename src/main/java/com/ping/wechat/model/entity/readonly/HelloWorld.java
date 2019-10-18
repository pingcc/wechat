package com.ping.wechat.model.entity.readonly;

import java.util.Map;

/**
 * Created  on 2018/9/20.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public class HelloWorld {
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    private String msg;
    private int uid;
    private String name;
    private Map<String, String> map;

    public void printName(String name) {
        uid++;
        System.out.println("Hi: " + name + uid);
    }

}
