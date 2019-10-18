package com.ping.wechat.task;


import com.ping.wechat.config.Constants;
import com.ping.wechat.controller.WxController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created  on 2019/2/27.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@Component
public class WeChaTask {
    Logger logger = LoggerFactory.getLogger(WeChaTask.class);
    @Autowired
    private WxController WxController;

    @Autowired
    private RedisTemplate redisTemplate;

    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 7000*1000 )
    public void getWeixinAccessToken(){

        try {

            String accessToken = WxController.
                    getAccessToken(Constants.GRANT_TYPE, Constants.APPID,Constants.SECRET);
            logger.info("获取到的微信accessToken为"+accessToken);

            ValueOperations<String, String> operations = redisTemplate.opsForValue();

            operations.set(Constants.ACCESSTOKENKEY,accessToken);//更新缓存或者将token写入缓存中

        } catch (Exception e) {
            logger.error("获取微信accessToken出错，信息如下",e.getMessage());
            e.printStackTrace();

        }

    }

}
