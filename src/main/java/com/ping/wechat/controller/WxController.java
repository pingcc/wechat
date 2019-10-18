package com.ping.wechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.ping.wechat.common.RequestParamsCommonCheck;
import com.ping.wechat.config.Api;
import com.ping.wechat.config.Constants;
import com.ping.wechat.util.CheckUtil;
import com.ping.wechat.util.HttpClientUtils;
import com.ping.wechat.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  on 2019/2/19.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@RestController
@RequestMapping("/wxService")
public class WxController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(WxController.class);
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(method = RequestMethod.GET, value = "/wx")
    public void wx() {
        System.out.println("开始签名校验");
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (StringUtils.isEmpty(signature)
                    || StringUtils.isEmpty(nonce)) {
                response.getWriter().println("signature或者nonce为空");
                logger.info("signature或者nonce为空");
                return;
            }
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                logger.info("签名校验通过,echostr:" + echostr);
                response.getWriter().println(echostr);
            } else {
                response.getWriter().println("签名校验失败");
                logger.info("签名校验失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //获取签名 access_token。先判断缓存中是否有值，有时直接用缓存的，不需要再次获取，过期先获取，在缓存
    public String getAccessToken(String type, String appId, String secret) {
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", type);
        map.put("appid", appId);
        map.put("secret", secret);

        try {
            String result = HttpClientUtils.doGet(Api.GETACCESSTOKEN, map);

            JSONObject jsonObject = parseObject(result);
            return jsonObject.getString(Constants.ACCESSTOKENKEY);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getWeChatIP")
    public String getWeChatIP() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String accessToken = operations.get(Constants.ACCESSTOKENKEY);
        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);

        try {
            String result = HttpClientUtils.doGet(Api.GETWECHATAPI, map);

            return result;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createMenu")
    public String createMenu(String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            RequestParamsCommonCheck.validateJsonParms(jsonObject,"button");
            String buttonStr=jsonObject.getString("button");
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String accessToken = operations.get(Constants.ACCESSTOKENKEY);
            Map<String, String> map = new HashMap<>();
            map.put("button", buttonStr);
            String result = HttpClientUtils.doPost(Api.CREATEMENU+"?"+Constants.ACCESSTOKENKEY+"="+accessToken, map);
            return result;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }


}
