package com.ping.wechat.model.dao.readonly;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ping.wechat.model.entity.readonly.Student_HI_RO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created  on 2018/11/28.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */


/**
 * 如果不配置需要对WechatApplication 进行MapperScan注解
 *
 * @link WechatApplication
 */
//@Mapper
@Component("studentMapper_HI_RO")
public interface StudentDAO_HI_RO extends BaseMapper<Student_HI_RO> {
    @Select(Student_HI_RO.QUERY)
    List<Student_HI_RO> findAllStudents();

}

