package com.ping.wechat.model.entity.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created  on 2018/12/18.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public class User_HI_HO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private Long id;
    private Long userId;
    private String loginName;
    private String phone;
    private String name;
    private String province;
    private String city;
    private String address;
    private int sex;
    @JSONField(format="yyyy-MM-dd")
    private Date registerDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
