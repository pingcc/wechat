package com.ping.wechat.model.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ping.wechat.model.entity.User_HI;

import org.springframework.stereotype.Component;

/**
 * Created  on 2018/11/28.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */


/** 如果不配置需要对WechatApplication 进行MapperScan注解
 * @link WechatApplication
 *
 */
//@Mapper
@Component("userMapper_HI")
public interface UserDAO_HI extends BaseMapper<User_HI> {

}

