package com.ping;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.ping.wechat.model.dao")
// 如果不配置需要对所有的map添加mapper注解
public class WechatApplication {
    private final static Logger logger = LoggerFactory.getLogger(WechatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
        logger.info("WeChatApplication is success!");


    }

}
