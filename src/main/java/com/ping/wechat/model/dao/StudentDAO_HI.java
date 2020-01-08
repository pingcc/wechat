package com.ping.wechat.model.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ping.wechat.model.entity.Student_HI;
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
@Component("studentDAO_HI")
public interface StudentDAO_HI extends BaseMapper<Student_HI> {


}

