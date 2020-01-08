package com.ping.wechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.common.RequestParamsCommonCheck;
import com.ping.wechat.common.ResponseCommonJson;
import com.ping.wechat.model.entity.Student_HI;
import com.ping.wechat.model.inter.IStudentServer;
import com.ping.wechat.model.inter.IUserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created  on 2018/9/21.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */


@RestController
@RequestMapping("/customerService")
public class CustomerService extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public IStudentServer studentServer;
    @Autowired
    public IUserServer userServer;


    @GetMapping(value = "/getStudent/{id}")//等价于/get_students/4
    public String getIdToStudent1(@PathVariable("id") Long id) {
        return ResponseCommonJson.returnSuccessResult("操作成功",
                studentServer.getIdToStudentParams(new JSONObject().fluentPut("id",id))).toString();
    }

    @GetMapping(value = "/getStudent")//等价于/get_students?id=4
    public String getIdToStudent(@RequestParam("id") Long id) {
        try {
            return ResponseCommonJson.returnSuccessResult("操作成功",
                    studentServer.getIdToStudentParams(new JSONObject().fluentPut("id",id))).toString();
        } catch (Exception var) {
            return ResponseCommonJson.returnErrorResult(var.getMessage(),
                    null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postBodyStudent")
    public String getIdToStudentBody(@RequestBody Student_HI studentHI) {
        try {
            RequestParamsCommonCheck.validateEntityParams(studentHI, "name");
            return ResponseCommonJson.returnSuccessResult("操作成功",
                    studentServer.getIdToStudentBody(studentHI)).toString();
        } catch (Exception var) {
            return ResponseCommonJson.returnErrorResult(var.getMessage(),
                    null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postParamsStudent")
//等价于/get_students?id=4
    public String getIdToStudentParams(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = this.parseObject(params);
            RequestParamsCommonCheck.validateJsonParms(queryParamJSON, "id");
            return ResponseCommonJson.returnSuccessResult("操作成功",
                    studentServer.getIdToStudentParams(queryParamJSON)).toString();
        } catch (Exception var) {
            return ResponseCommonJson.returnErrorResult(var.getMessage(),
                    null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserInfo")//等价于/get_students?id=4
    public String getUserInfo() {
        try {
            return ResponseCommonJson.returnSuccessResult("操作成功",
                    userServer.getUserInfo()).toString();
        } catch (Exception var) {
            return ResponseCommonJson.returnErrorResult(var.getMessage(),
                    null).toString();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserInfo1")//等价于/get_students?id=4
    public void getUserInfo1() {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            Map<String, String> map = new HashMap<>();
            map.put("status", "success");
            writer.write(map.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void getAccessNum() {
        logger.info("服务器启动!");
    }

    @PreDestroy
    public void serverStop() {
        logger.info("服务器关闭!");
    }
}



