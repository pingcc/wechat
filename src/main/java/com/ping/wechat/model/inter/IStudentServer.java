package com.ping.wechat.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.model.entity.Student_HI;

/**
 * Created  on 2019/9/30.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public interface IStudentServer {

     Student_HI getIdToStudentParams(JSONObject queryParamJSON);
     Student_HI getIdToStudentBody(Student_HI studentHI);
}
