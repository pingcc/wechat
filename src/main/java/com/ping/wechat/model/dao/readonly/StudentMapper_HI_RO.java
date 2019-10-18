package com.ping.wechat.model.dao.readonly;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ping.wechat.model.entity.readonly.Student_HI_RO;

import org.apache.ibatis.annotations.Param;
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
public interface StudentMapper_HI_RO extends BaseMapper<Student_HI_RO> {
    @Select(Student_HI_RO.QUERY)
    List<Student_HI_RO> findAllStudents();

    @Select("SELECT * FROM student_db WHERE id = #{id}")
    Student_HI_RO getIdToStudent(@Param("id") Long id);

    @Select("SELECT * FROM student_db WHERE name = #{name}")
    Student_HI_RO getNameToStudent(@Param("name") String name);

//    @Select("SELECT * FROM user_info")
//    List<User_HI_HO> getUserInfo();
}

