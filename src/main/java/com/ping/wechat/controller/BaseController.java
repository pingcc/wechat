package com.ping.wechat.controller;


import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created  on 2018/12/18.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public class BaseController {
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpServletResponse response;

    protected JSONObject parseObject(String params) {
      JSONObject jsonObject=JSONObject.parseObject(params);
      return jsonObject;
    }


}
