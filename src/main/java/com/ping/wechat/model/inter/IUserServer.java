package com.ping.wechat.model.inter;

import com.ping.wechat.model.entity.User_HI;

import java.util.List;

/**
 * Created  on 2019/9/30.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public interface IUserServer {

     List<User_HI> getUserInfo();
}
