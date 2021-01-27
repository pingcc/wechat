package com.ping.wechat.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @Author Andy
 * @Date 2021/1/27
 */
public class JasyptUtil {
    /**
     * Jasypt生成加密结果
     *
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待加密值
     * @return
     */
    public static String encryptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(password));
        return encryptOr.encrypt(value);
    }

    /**
     * 解密
     *
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待解密密文
     * @return
     */
    public static String decyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(password));
        String result = encryptOr.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptOr(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    public static void main(String[] args) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        // application.properties, jasypt.encryptor.password
        encryptor.setPassword("astar@123");
        // encrypt root
        System.out.println(encryptor.encrypt(
            "jdbc:mysql://47.107.105.131:3306/sw_base_boot?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=UTC"));
        System.out.println(encryptor.encrypt(
            "jdbc:mysql://47.107.105.131:3306/sw_manage_boot?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=UTC"));
        System.out.println(encryptor.encrypt("root"));
        System.out.println(encryptor.encrypt("astar123554"));
        System.out.println(encryptor.encrypt("47.107.105.131"));
        System.out.println(encryptor.encrypt("123554"));
        //        System.out.println(encryptor.encrypt("root"));
        //        System.out.println(encryptor.encrypt("123554"));
        // decrypt, the result is root
   /*     System.out.println(encryptor.decrypt(
            "lzPf+Oxavi70usXWsLKOJGeXelE1uge8vLwsgWiSSkfpftZPNiSmVK5xVk8qozNRwMfaax3zfocABNNhQB0hQqt+vyjYOaGdMzmP07xuHmDoWapuZZMhWQa6d6w4OVX3Z/JMnm0V2NGkYkScOj0IpdU+fEwSxhbAThIh/wR/HddyZSGxEV9TXZet27Pegw+gM6I9hzFvBY4dsXQqS1q5JmWUWVg4IrclQS4UpOoREIgrfJjfqy3v5HSAMT1b2ayGTwytc+sKcMj9XP8tnf2dtw=="));
        System.out.println(encryptor.decrypt("vyyJZDny2DKH91TMycXsng=="));
        System.out.println(encryptor.decrypt("c6Q5EEP1lZi3YdW3h62gpA=="));*/

        // 加密
        //        System.out.println(encryptPwd("panther", "root"));
        // 解密
        //        System.out.println(decyptPwd("panther", "GfP4qfnrJeqMvzN1nOemIQ=="));
    }

}
