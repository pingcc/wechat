package com.ping.wechat.util;


import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.Parameters;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * URL请求工具类
 * @author ZhangJun
 * @createTime 2019-04-03 10:26
 * @description 主要用于获取GET请求格式字符串的请求参数
 *
 * 如果URL请求参数为：qrCodeType=STORE_ACTIVITY_CODE&companyId=2&guiderId=10000&storeId=100608
 * RequestUtil.getParameter("qrCodeType") 返回 STORE_ACTIVITY_CODE
 */
public class RequestUtil {

	private String queryParamString;

	public RequestUtil(String queryParamString){
		this.queryParamString = queryParamString;
	}

	/**
	 * 获取请求参数值
	 * @param name 参数名
	 * @return 参数值
	 * @author ZhangJun
	 * @createTime 2019-04-03
	 * @description 获取请求参数值
	 */
	public Integer getParameterIntValue(String name){
		String value = getParameter(name);
		if(StringUtils.isBlank(value)){
			return null;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 获取请求参数值
	 * @param name 参数名
	 * @param defaultValue 默认值
	 * @return 参数值
	 * @author ZhangJun
	 * @createTime 2019-04-03
	 * @description 获取请求参数值
	 */
	public String getParameter(String name,String defaultValue){
		String value = getParameter(name);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}

	/**
	 * 获取请求参数值
	 * @param name 参数名
	 * @return 参数值
	 * @author ZhangJun
	 * @createTime 2019-04-03
	 * @description 获取请求参数值
	 */
	public String getParameter(String name){
		Map<String, String[]> parameters = getParameters();
		String[] value = parameters.get(name);
		if (value == null) {
			return null;
		}
		return value[0];
	}

	/**
	 * 获取请求参数字符串中的值
	 * @return {@link Map<String,String[]>}
	 * @author ZhangJun
	 * @createTime 2019-04-03
	 * @description 获取请求参数字符串中的值
	 */
	public Map<String,String[]> getParameters(){
		Map<String,String[]> parameters = new HashMap<>();
		Parameters paramParser = new Parameters();
		MessageBytes queryMB = MessageBytes.newInstance();
		queryMB.setString(queryParamString);
		paramParser.setQuery(queryMB);
		paramParser.handleQueryParameters();
		Enumeration<String> dispParamNames = paramParser.getParameterNames();
		while (dispParamNames.hasMoreElements()) {
			String dispParamName = dispParamNames.nextElement();
			String[] dispParamValues = paramParser.getParameterValues(dispParamName);
			String[] originalValues = parameters.get(dispParamName);
			if (originalValues == null) {
				parameters.put(dispParamName, dispParamValues);
				continue;
			}
			parameters.put(dispParamName, mergeValues(dispParamValues, originalValues));
		}
		return parameters;
	}

	/**
	 * 合并值
	 * @author ZhangJun
	 * @createTime 2019-04-03
	 * @description 合并值
	 */
	private String[] mergeValues(String[] values1, String[] values2) {

		ArrayList<Object> results = new ArrayList<>();

		if (values1 == null) {
			// Skip - nothing to merge
		} else {
			for (String value : values1) {
				results.add(value);
			}
		}

		if (values2 == null) {
			// Skip - nothing to merge
		} else {
			for (String value : values2) {
				results.add(value);
			}
		}

		String values[] = new String[results.size()];
		return results.toArray(values);
	}
}
