package com.ping.wechat.controller;

import com.ping.wechat.common.ResponseCommonJson;
import com.ping.wechat.model.inter.ILawHeadThirdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CPing
 * @date 2020/5/24 14:03
 */
@RestController
@RequestMapping("/lawHeadThirdService")
public class LawHeadThirdService extends BaseController {
    @Autowired
    public ILawHeadThirdServer headThirdServer;


    @RequestMapping(method = RequestMethod.GET, value = "/saveOrUpdateLawHeadThirdIno")//等价于/get_students?id=4
    public String saveOrUpdateLawHeadThirdIno() {
        try {
            headThirdServer.saveOrUpdateLawHeadThirdIno();
            return ResponseCommonJson.returnSuccessResult("操作成功",
                    null).toString();
        } catch (Exception var) {
            return ResponseCommonJson.returnErrorResult(var.getMessage(),
                    null).toString();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateLawHeadInfo")//等价于/get_students?id=4
    public String updateLawHeadInfo() {
        try {
            headThirdServer.updateLawHeadInfo();
            return ResponseCommonJson.returnSuccessResult("操作成功",
                    null).toString();
        } catch (Exception var) {
            return ResponseCommonJson.returnErrorResult(var.getMessage(),
                    null).toString();
        }

    }

}
