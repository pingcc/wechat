package com.ping.wechat.config;

/**
 * Created  on 2019/2/26.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public interface Api {
    String GETACCESSTOKEN="https://api.weixin.qq.com/cgi-bin/token";//获取accesstoken api
    String GETWECHATAPI="https://api.weixin.qq.com/cgi-bin/getcallbackip";//获取w微信服务器ip
    String CREATEMENU="https://api.weixin.qq.com/cgi-bin/menu/create";//创建自定义菜单
}
