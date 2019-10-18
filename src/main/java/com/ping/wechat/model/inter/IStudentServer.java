package com.ping.wechat.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.model.entity.Student_HI;
import com.ping.wechat.model.entity.readonly.Student_HI_RO;

/**
 * Created  on 2019/9/30.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public interface IStudentServer {

     Student_HI_RO getIdToStudentParams(JSONObject queryParamJSON);
     Student_HI_RO getIdToStudent(Long id);
     Student_HI_RO getIdToStudentBody(Student_HI studentHI);
}
