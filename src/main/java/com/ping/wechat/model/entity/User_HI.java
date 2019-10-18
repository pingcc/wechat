package com.ping.wechat.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created  on 2018/11/28.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@Entity
@TableName("users")
public class User_HI {
    private long userId;//自增id
    private int sex;
    private String name;
    private String loginName;
    private String phone;
    private String province;
    private String city;
    private String address;
    @JSONField(format="yyyy-MM-dd")
    private Date registerDate;
    @Id
    @GeneratedValue
    @Column(name="user_id", nullable=false, length=11)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    @Column(name="sex", nullable=false, length=11)
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Column(name="name", nullable=false, length=40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="login_name", nullable=false, length=40)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    @Column(name="phone", nullable=false, length=11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name="province", nullable=false, length=20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    @Column(name="city", nullable=false, length=40)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="address", nullable=false, length=40)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name="register_date", nullable=false, length=0)
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }


}
