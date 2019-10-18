package com.ping.wechat.util;


import com.ping.wechat.util.crypto.DigestUtils;

import java.util.Random;

/**
 * @author ZhangJun
 * @createTime 2018-12-26 16:32
 * @description
 * 前端：md5加密  原密码 + lo0.1l@g9v#
 *
 * 后端：md5加密	 手机号码 + ?xo@0b%l.37$ + 前端传过来的密码
 */
public class PasswordUtil {

	public static final String FRONT_RANDOM = "lo0.1l@g9v#";
	public static final String SERVER_RANDOM = "?xo@0b%l.37$";

	/**
	 * 生成前端加密密码，在远程调用创建用户接口时使用
	 * @param pwd 密码
	 * @return 加密后的密码
	 * @author ZhangJun
	 * @createTime 2018-12-26
	 * @description 生成前端加密密码
	 */
	public static String generateFrontPwd(String pwd){
		pwd = pwd + FRONT_RANDOM;

		return DigestUtils.md5(pwd);
	}

	/**
	 * 生成后端密码
	 * @param phoneNumber 用户名或手机号
	 * @param pwd 密码
	 * @author ZhangJun
	 * @createTime 2018-12-26
	 * @description 生成后端密码
	 */
	public static String generateServerPwd(String phoneNumber,String pwd){
		pwd = phoneNumber+SERVER_RANDOM+pwd;
		return DigestUtils.md5(pwd);
	}

	/**
	 * 生成随机密码
	 * @param length 密码长度
	 * @author ZhangJun
	 * @createTime 2018/8/29
	 * @description 生成随机密码
	 */
	public static String genRandomPwd(int length){
		char[] charAndnum = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for(int i = 0;i<length;i++){
			sb.append(charAndnum[random.nextInt(charAndnum.length-1)]);

		}
		return sb.toString();
	}
}
