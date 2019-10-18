package com.ping.wechat.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.model.dao.readonly.StudentMapper_HI_RO;
import com.ping.wechat.model.entity.Student_HI;
import com.ping.wechat.model.entity.readonly.Student_HI_RO;
import com.ping.wechat.model.inter.IStudentServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created  on 2019/9/30.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@Component("studentServer")
public class StudentServer implements IStudentServer {
    @Autowired
    private StudentMapper_HI_RO studentMapper_hi_ro;


    @Override
    public Student_HI_RO getIdToStudent(Long id) {
        return studentMapper_hi_ro.getIdToStudent(id);
    }

    @Override
    public Student_HI_RO getIdToStudentParams(JSONObject queryParamJSON) {
        long id = queryParamJSON.getLong("id");
        return studentMapper_hi_ro.getIdToStudent(id);

    }

    @Override
    public Student_HI_RO getIdToStudentBody(Student_HI studentHI) {
        return studentMapper_hi_ro.getNameToStudent(studentHI.getName());

    }


}
