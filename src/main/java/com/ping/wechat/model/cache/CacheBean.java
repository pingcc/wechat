package com.ping.wechat.model.cache;

import java.io.Serializable;

/**
 * Created  on 2019/2/25.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 * 注：当redis缓存对象时需要对 对象序列化
 * example
 */
public  class CacheBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int uid;
    private String userName;
    private String passWord;
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public CacheBean(int uid, String userName, String passWord, int salary) {
        super();
        this.uid = uid;
        this.userName = userName;
        this.passWord = passWord;
    }
    public CacheBean() {
        super();
        // TODO Auto-generated constructor stub
    }



}
