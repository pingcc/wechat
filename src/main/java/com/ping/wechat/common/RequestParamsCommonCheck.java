package com.ping.wechat.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created  on 2018/12/18.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public  class RequestParamsCommonCheck {
    private static final Logger log = LoggerFactory.getLogger(RequestParamsCommonCheck.class);

    public static void validateJsonParms(JSONObject jsonObject, String... keys) throws IllegalArgumentException {
        if (keys != null && keys.length != 0) {
            if (jsonObject == null) {
                throw new IllegalArgumentException("缺少参数 " + keys[0]);
            } else {
                String[] var2 = keys;
                int var3 = keys.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String key = var2[var4];
                    if (StringUtils.isBlank(jsonObject.getString(key))) {
                        throw new IllegalArgumentException("缺少参数 " + key);
                    }
                }

            }
        }
    }

    public static void validateJsonArrayParms(JSONArray jsonArray, String... keys) throws IllegalArgumentException {
        if (keys != null && keys.length != 0) {
            if (jsonArray == null) {
                throw new IllegalArgumentException("缺少参数 " + keys[0]);
            } else {
                for(int i = 0; i < jsonArray.size(); ++i) {
                    String[] var3 = keys;
                    int var4 = keys.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        String key = var3[var5];
                        if (StringUtils.isBlank(jsonArray.getJSONObject(i).getString(key))) {
                            throw new IllegalArgumentException("缺少参数 " + key);
                        }
                    }
                }

            }
        }
    }

    public static void validateJsonArrayParms(boolean flag, JSONArray jsonArray, String... keys) throws IllegalArgumentException {
        if (keys != null && keys.length != 0) {
            if (jsonArray == null) {
                if (flag) {
                    throw new IllegalArgumentException("缺少参数 " + keys[0]);
                }
            } else {
                for(int i = 0; i < jsonArray.size(); ++i) {
                    String[] var4 = keys;
                    int var5 = keys.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        String key = var4[var6];
                        if (StringUtils.isBlank(jsonArray.getJSONObject(i).getString(key))) {
                            throw new IllegalArgumentException("缺少参数 " + key);
                        }
                    }
                }

            }
        }
    }

    public static void validateEntityFields(Object entity, String... fields) {
        if (fields != null && fields.length != 0) {
            if (entity == null) {
                throw new IllegalArgumentException("缺少参数 " + fields);
            } else {
                String[] var2 = fields;
                int var3 = fields.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String field = var2[var4];

                    try {
                        Field f = entity.getClass().getDeclaredField(field);
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }

                        Object obj = f.get(entity);
                        if (obj == null || obj instanceof String && "".equals(obj)) {
                            throw new IllegalArgumentException("缺少参数 " + field);
                        }
                    } catch (IllegalAccessException | NoSuchFieldException var8) {
                        log.warn("field {} in {} not existed", field, entity.getClass().getName());
                    }
                }

            }
        }
    }

    public static void validateEntityParams(Object entity, String... params) {
        String jsonString = JSONObject.toJSONString(entity);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        validateJsonParms(jsonObject,params);
    }
    public static void validateEntityArrayParams(Object entityArray, String... params) {
        String jsonString = JSONObject.toJSONString(entityArray);
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        validateJsonArrayParms(jsonArray,params);
    }
}
