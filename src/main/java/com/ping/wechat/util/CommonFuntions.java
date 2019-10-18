package com.ping.wechat.util;

import org.apache.commons.lang.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通用方法工具类
 * 
 * @author <a href="mailto:jiangq@hiwitech.com">JiangQiang</a>
 * @version 1.0
 */
public class CommonFuntions {

    /**
     * 判断对象是否为空对象，支持Object,字符串,Collection,Map和数组类型的判断 1、如果传入的参数为null则返回true
     * 2、如果传入的参数为Object类型，并且值为null，则返回true，否则返回false；
     * 3、如果传入的参数为集合类型，如ArrayList，如果该参数值为null或者参数的容量小于等于0，或者为empty，则返回true，否则返回false；
     * 4、如果传入的参数为Map类型，如HashMap，如果该参数值为null或者参数的容量小于等于0，或者为empty，则返回true，否则返回false；
     * 5、如果传入的参数为Arrray类型，如String[]，如果该参数值为null或者参数的长度小于等于0，则返回true，否则返回false；
     * 
     * @param obj
     *            要进行判断的对象，Object，String，Collection，Map，Array中的某个类型。
     * @return boolean 如果参数为空则返回true；否则为false；
     * @throws Exception
     *             如果传入的类型不被支持，则抛出异常。
     */
    public static <T> boolean isEmptyObject(T obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {

            String strObj = (String) obj;
            return strObj == null || StringUtils.isEmpty(strObj) || StringUtils.isBlank(StringUtils.trim(strObj)) ? true
                    : false;

        } else if (obj instanceof Collection<?>) {

            Collection<?> collectionObj = (Collection<?>) obj;
            return collectionObj == null || collectionObj.size() <= 0 || collectionObj.isEmpty() ? true : false;

        } else if (obj instanceof Map<?, ?>) {

            Map<?, ?> mapObje = (Map<?, ?>) obj;
            return mapObje == null || mapObje.size() <= 0 || mapObje.isEmpty() ? true : false;

        } else if (obj.getClass().isArray()) {

            return obj == null || Array.getLength(obj) <= 0 ? true : false;

        } else {
            return obj == null ? true : false;
        }
    }

    /**
     * 判断对象是否不为空。
     * 
     * @param obj
     * @return boolean
     */
    public static <T> boolean isNotEmptyObject(T obj) {
        return !isEmptyObject(obj);
    }

    /**
     * 判断�?��对象num是否是个数字对象
     * 
     * @param num
     *            Object
     * @return 如果num是个数字类对�?返回true，否则返回false�?
     */
    public static boolean isNumber(Object num) {
        // return isNumber(num.getClass());
        if (num instanceof Number) {
            return true;
        }
        if (num instanceof String) {
            try {
                BigDecimal bigNum = new BigDecimal((String) num);
                bigNum.longValue();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isNumber2(Object test) {
        try {
            new BigDecimal(test.toString()).longValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNumber(Class<?> clazz) {
        if (Number.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    public static boolean isTime(int sqlType) {
        return Types.TIME == sqlType || Types.TIMESTAMP == sqlType;
    }

    public static boolean isDate(int sqlType) {
        return Types.DATE == sqlType;
    }

    public static String trimBlank(String source) {
        return StringUtils.trim(source);
    }

    // -------------------------------------------------------------
    public static Class<?> getJavaClassInner(String type) {
        if (type.equals("String"))
            return String.class;
        if (type.equals("Short"))
            return Short.class;
        if (type.equals("Integer"))
            return Integer.class;
        if (type.equals("Long"))
            return Long.class;
        if (type.equals("Double"))
            return Double.class;
        if (type.equals("Float"))
            return Float.class;
        if (type.equals("Byte"))
            return Byte.class;
        if ((type.equals("Char")) || (type.equals("Character")))
            return Character.class;
        if (type.equals("Boolean"))
            return Boolean.class;
        if (type.equals("Date"))
            return java.sql.Date.class;
        if (type.equals("Time"))
            return Time.class;
        if (type.equals("DateTime"))
            return Timestamp.class;

        if (type.equals("Object"))
            return Object.class;

        if (type.equals("short"))
            return short.class;
        if (type.equals("int"))
            return int.class;
        if (type.equals("long"))
            return long.class;
        if (type.equals("double"))
            return double.class;
        if (type.equals("float"))
            return float.class;
        if (type.equals("byte"))
            return byte.class;
        if (type.equals("char"))
            return char.class;
        if (type.equals("boolean"))
            return boolean.class;
        try {
            return loadClass(type);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Class<?> getJavaClass(String type) {
        int index = type.indexOf("[]");
        if (index < 0) {
            return getJavaClassInner(type);
        }
        String arrayString = "[";
        String baseType = type.substring(0, index);
        while ((index = type.indexOf("[]", index + 2)) >= 0) {
            arrayString = arrayString + "[";
        }
        Class<?> baseClass = getJavaClassInner(baseType);
        try {
            String baseName = "";
            if (!baseClass.isPrimitive()) {
                return loadClass(arrayString + "L" + baseClass.getName() + ";");
            }
            if (baseClass.equals(Boolean.class) || baseClass.equals(boolean.class))
                baseName = "Z";
            else if (baseClass.equals(Byte.class) || baseClass.equals(byte.class))
                baseName = "B";
            else if (baseClass.equals(Character.class) || baseClass.equals(char.class))
                baseName = "C";
            else if (baseClass.equals(Double.class) || baseClass.equals(double.class))
                baseName = "D";
            else if (baseClass.equals(Float.class) || baseClass.equals(float.class))
                baseName = "F";
            else if (baseClass.equals(Integer.class) || baseClass.equals(int.class))
                baseName = "I";
            else if (baseClass.equals(Long.class) || baseClass.equals(long.class))
                baseName = "J";
            else if (baseClass.equals(Short.class) || baseClass.equals(short.class)) {
                baseName = "S";
            }
            return loadClass(arrayString + baseName);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> result = null;
        try {
            result = Thread.currentThread().getContextClassLoader().loadClass(name);
        } catch (ClassNotFoundException ex) {
        }
        if (result == null) {
            result = Class.forName(name);
        }
        return result;
    }

    /**
     * 判断是否为小写字符。
     * 
     * @param chr
     *            char 参数
     * @return boolean 如果是小写返回true，否则返回false；
     */
    public static boolean isLow(char chr) {
        if (chr > 90 || chr < 65) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为大写字符。
     * 
     * @param chr
     *            char 参数
     * @return boolean 如果是大写返回true，否则返回false；
     */
    public static boolean isUpper(char chr) {
        if (chr <= 90 && chr >= 65) {
            return true;
        }
        return false;
    }

    public static String trimBeginUnblank(String utfStr) {
        return utfStr.charAt(0) == 65279 ? utfStr.substring(1) : utfStr;
    }

    public static StringBuilder headToBuilder() {
        StringBuilder sb = new StringBuilder();
        byte[] hand = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
        try {
            sb.append(new String(hand, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            sb = new StringBuilder();
        }
        return sb;
    }

    public static boolean checkIpAddress(String ipAddress) {
        char a = ipAddress.charAt(0);
        if (a == 65279) {
            ipAddress = ipAddress.substring(1);
        }
        Pattern pattern = Pattern.compile(
                "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public static boolean isZeroNumer(Object obj) {
        return isEmptyObject(obj) || (isNumber(obj) && new BigDecimal(obj.toString()).longValue() <= 0) ? true : false;
    }

    public static boolean isNotZeroNumer(Object obj) {
        return !isZeroNumer(obj);
    }

    public static Long test(String num) {
        Long rtn = null;
        try {
            rtn = Long.parseLong(num);
        } catch (Exception e) {
            rtn = -100l;
        }
        return rtn;
    }

    public static String writerExceptionStackTrace(Throwable tabl) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tabl.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
