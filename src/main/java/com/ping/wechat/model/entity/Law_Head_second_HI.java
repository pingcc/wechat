package com.ping.wechat.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author CPing
 * @date 2020/5/24 13:55
 */
@Entity
@TableName("law_head_second1")
public class Law_Head_second_HI {

    private Integer id;//自增id
    private Integer headId;//自增id
    private String title;

    @Column(name = "head_id", nullable = false, length = 11)

    public Integer getHeadId() {
        return headId;
    }

    public void setHeadId(Integer headId) {
        this.headId = headId;
    }

    @Id
    @Column(name = "id", nullable = false, length = 11)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="title", nullable=true, length=0)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
