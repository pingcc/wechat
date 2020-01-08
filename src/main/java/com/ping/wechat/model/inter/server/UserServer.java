package com.ping.wechat.model.inter.server;

import com.ping.wechat.model.dao.UserDAO_HI;
import com.ping.wechat.model.entity.User_HI;
import com.ping.wechat.model.inter.IUserServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created  on 2019/9/30.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@Component("userServer")
public class UserServer implements IUserServer {
    @Autowired
    private UserDAO_HI userDAO_hi;

    @Override
    public List<User_HI> getUserInfo() {

        return userDAO_hi.selectList(null);
    }
}
