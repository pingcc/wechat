package com.ping.wechat.util.qrutil;


import com.ping.wechat.util.qrutil.annotation.ReqType;

/**
 * 
 * @author sfli.sir
 * 
 */
@ReqType("shorturlCreate")
public class ShortUrlCreate extends WeixinReqParam {

	private String action = "long2short";
	
	private String long_url;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLong_url() {
		return long_url;
	}

	public void setLong_url(String long_url) {
		this.long_url = long_url;
	}
	
}
