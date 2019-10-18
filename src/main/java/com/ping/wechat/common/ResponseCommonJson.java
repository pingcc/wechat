package com.ping.wechat.common;

import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.config.Constants;

/**
 * Created  on 2018/12/18.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public class ResponseCommonJson {


    private static JSONObject resultJSONObj(JSONObject jsonObject,String msg, Object obj) {
        jsonObject.put("msg", msg);
        jsonObject.put("data", obj);
        return jsonObject;
    }
    public static JSONObject returnErrorResult(String msg, Object obj){
        JSONObject json = new JSONObject();
        json.put("status", Constants.ERROR_STATUS);
        return resultJSONObj(json,msg,obj);
    }
    public static JSONObject returnSuccessResult(String msg, Object obj){
        JSONObject json = new JSONObject();
        json.put("status", Constants.SUCCESS_STATUS);
        return resultJSONObj(json,msg,obj);
    }

}
