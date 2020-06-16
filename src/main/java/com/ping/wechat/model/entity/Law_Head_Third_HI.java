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
@TableName("law_head_third")
public class Law_Head_Third_HI {

    private Long id;//自增id
    private Integer secondHeadId;//二级id
    private String content;

    @Column(name = "second_head_id", nullable = false, length = 20)
    public Integer getSecondHeadId() {
        return secondHeadId;
    }

    public void setSecondHeadId(Integer secondHeadId) {
        this.secondHeadId = secondHeadId;
    }



    @Id
    @Column(name = "id", nullable = false, length = 11)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="content", nullable=true, length=0)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
