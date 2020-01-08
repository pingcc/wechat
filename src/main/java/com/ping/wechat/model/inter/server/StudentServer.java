package com.ping.wechat.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.model.dao.StudentDAO_HI;
import com.ping.wechat.model.dao.readonly.StudentDAO_HI_RO;
import com.ping.wechat.model.entity.Student_HI;
import com.ping.wechat.model.entity.readonly.Student_HI_RO;
import com.ping.wechat.model.inter.IStudentServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


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
    private StudentDAO_HI_RO studentDao_hi_ro;

    @Autowired
    private StudentDAO_HI studentDao_hi;
    @Override
    public Student_HI_RO getIdToStudentParams(JSONObject queryParamJSON) {
        long id = queryParamJSON.getLong("id");
        List<Student_HI> list= studentDao_hi.selectList(null);
        return studentDao_hi_ro.selectById(id);

    }

    @Override
    public  Student_HI_RO getIdToStudentBody(Student_HI studentHI) {
        HashMap map = new HashMap<String,String>();
        map.put("name",studentHI.getName());
        List<Student_HI_RO> list = studentDao_hi_ro.selectByMap(map);
        return list.get(0);

    }


}
