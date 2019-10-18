package com.ping.wechat.model.entity.readonly;


/**
 * Created  on 2018/11/28.
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
//只读类，
public class Student_HI_RO {
    public final static String QUERY="SELECT * FROM student_db";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;//自增id
    private String name;
    private String title;
}
