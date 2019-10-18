//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ping.wechat.util;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class SToolUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SToolUtils.class);
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String COUNT = "count";
    public static final String DATA = "data";
    private static Logger logger = LoggerFactory.getLogger(SToolUtils.class);
    public static final String EMAIL_VALIDATE = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String HTTP_VALIDATE = "^http:\\/\\/([\\w-]+(\\.[\\w-]+)+(\\/[\\w-     .\\/\\?%&=一-龥]*)?)?$";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public SToolUtils() {
    }

    public static boolean generateFile(String fullPath, String fileName, StringBuffer content) {
        boolean successFlag = false;
        File file = new File(fullPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fullPath + File.separator + fileName), "UTF-8");
            out.write(content.toString());
            out.flush();
            out.close();
            successFlag = true;
        } catch (IOException var6) {
            LOGGER.error(var6.getMessage(), var6);
            successFlag = false;
        }

        return successFlag;
    }

    public static Long dateDiffSeconds(Date startTime, String endTimeStr, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format);

        try {
            Date endTime = sd.parse(endTimeStr);
            LOGGER.info(endTime + "");
            return dateDiffSeconds(startTime, endTime);
        } catch (ParseException var5) {
            LOGGER.error(var5.getMessage(), var5);
            return 0L;
        }
    }

    public static Long dateDiffSeconds(Date startTime, Date endTime) {
        long diff = endTime.getTime() - startTime.getTime();
        return diff;
    }

    public static Long dateDiffSeconds(String startTime, String endTime, String format, String str) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 86400000L;
        long nh = 3600000L;
        long nm = 60000L;
        long ns = 1000L;
        long day = 0L;
        long hour = 0L;
        long min = 0L;
        long sec = 0L;

        try {
            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;
            hour = diff % nd / nh + day * 24L;
            min = diff % nd % nh / nm + day * 24L * 60L;
            sec = diff % nd % nh % nm / ns;
            LOGGER.info("时间相差：" + day + "天" + (hour - day * 24L) + "小时" + (min - day * 24L * 60L) + "分钟" + sec + "秒。");
            LOGGER.info("hour=" + hour + ",min=" + min);
            return str.equalsIgnoreCase("h") ? hour : min;
        } catch (ParseException var24) {
            LOGGER.error(var24.getMessage(), var24);
            return str.equalsIgnoreCase("h") ? hour : min;
        }
    }

    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1D) {
            random += 0.1D;
        }

        for(int i = 0; i < length; ++i) {
            num *= 10;
        }

        return (int)(random * (double)num);
    }

    public static String randomString(int length) {
        String val = "";
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val = val + (char)(random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val = val + String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }

    public static boolean validateURl(String urlAddress) {
        boolean validateResult = false;
        if (null == urlAddress) {
            validateResult = false;
            return validateResult;
        } else {
            Pattern regex = Pattern.compile("^http:\\/\\/([\\w-]+(\\.[\\w-]+)+(\\/[\\w-     .\\/\\?%&=一-龥]*)?)?$");
            Matcher matcher = regex.matcher(urlAddress);
            validateResult = matcher.matches();
            return validateResult;
        }
    }

    public static boolean validateEmail(String emailAddress) {
        boolean validateResult = false;
        if (null == emailAddress) {
            validateResult = false;
            return validateResult;
        } else {
            Pattern regex = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
            Matcher matcher = regex.matcher(emailAddress);
            validateResult = matcher.matches();
            if (!validateResult) {
                throw new RuntimeException("'" + emailAddress + "' email address pattern is wrong, please update like 'test.hell@mail.com'");
            } else {
                return validateResult;
            }
        }
    }

    public static String object2String(Object object) {
        String returnValue = null;
        if (null != object) {
            returnValue = object.toString();
        } else {
            returnValue = "";
        }

        return returnValue;
    }

    public static Timestamp date2Timestamp(Date date) {
        Timestamp returnVlaue = null;
        if (null != date) {
            long dateTime = date.getTime();
            returnVlaue = new Timestamp(dateTime);
            return returnVlaue;
        } else {
            throw new RuntimeException("The date object is null, can not convert to java.sql.Timestamp type......");
        }
    }

    public static java.sql.Date string2SQLDate(String object) {
        return string2SQLDate(object, "yyyy-MM-dd");
    }

    public static java.sql.Date string2SQLDate(String object, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d = null;

        try {
            d = format.parse(object);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }

    public static Date timestamp2Date(Timestamp timestamp) {
        Date date = timestamp2Date(timestamp, "yyyy-MM-dd hh:mm:ss");
        return date;
    }

    public static Date timestamp2Date(Timestamp timestamp, String dateFormat) {
        if (null == dateFormat || "".equals(dateFormat.trim())) {
            dateFormat = "yyyy-MM-dd hh:mm:ss";
        }

        if (null != timestamp) {
            SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
            Date date = new Date(timestamp.getTime());

            try {
                date = string2DateTime(sf.format(date));
            } catch (ParseException var5) {
                logger.error("com.base.adf.utils.timestamp2Date-->SToolUtils", var5.getMessage(), "is execution");
            }

            return date;
        } else {
            return null;
        }
    }

    public static Date previousDate(int days, Date targetDate) {
        Date d = previousDate(days, targetDate, "yyyy-MM-dd hh:mm:ss");
        return d;
    }

    public static Date previousDate(int days, Date targetDate, String dateFormat) {
        if (null == dateFormat || "".equals(dateFormat.trim())) {
            dateFormat = "yyyy-MM-dd hh:mm:ss";
        }

        Date d = null;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Calendar ca = Calendar.getInstance();
        ca.setTime(targetDate);
        ca.add(5, days);

        try {
            d = df.parse(df.format(ca.getTime()).toString());
        } catch (ParseException var7) {
            logger.error("com.base.adf.utils.previousDate-->SToolUtils", var7.getMessage(), "is execution");
        }

        return d;
    }

    public static Date string2DateTime(String object) throws ParseException {
        Date uDate = string2DateTime(object, "yyyy-MM-dd hh:mm:ss");
        return uDate;
    }

    public static Date string2DateTime(String object, String dateFormat) throws ParseException {
        if (null == dateFormat || "".equals(dateFormat.trim())) {
            dateFormat = "yyyy-MM-dd hh:mm:ss";
        }

        if (null != object && !"".equals(object.trim())) {
            SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
            new Date();
            Date uDate = sf.parse(object);
            return uDate;
        } else {
            return new Date();
        }
    }

    public static Date addDate(int number, int type, Date targetDate) {
        Date d = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(targetDate);
        if (type == 5) {
            ca.add(5, number);
        } else if (type == 10) {
            ca.add(10, number);
        } else if (type == 12) {
            ca.add(12, number);
        }

        try {
            d = df.parse(df.format(ca.getTime()).toString());
        } catch (ParseException var7) {
            logger.error("com.base.adf.utils.previousDate-->SToolUtils", var7.getMessage(), "is execution");
        }

        return d;
    }

    public static boolean validateDateFormatY_M_D(String dateValue) {
        String datePattern = "^(?:[0-9])";
        Pattern p = Pattern.compile(datePattern);
        Matcher m = p.matcher(dateValue);
        boolean b = m.matches();
        return b;
    }

    public static boolean validateNumer(String value, int targetLength) {
        Pattern p = Pattern.compile("\\d{" + targetLength + "}");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        return str;
    }

    public static XMLGregorianCalendar date2XMLDatetime(Date date) {
        if (null == date) {
            throw new RuntimeException("com.base.adf.utils.SToolUtils-->SToolUtils====> the date object is null, can not convert any type");
        } else {
            GregorianCalendar nowGregorianCalendar = new GregorianCalendar();
            nowGregorianCalendar.setTime(date);
            XMLGregorianCalendar xmlDatetime = null;

            try {
                xmlDatetime = DatatypeFactory.newInstance().newXMLGregorianCalendar(nowGregorianCalendar);
            } catch (Exception var4) {
                LOGGER.error(var4.getMessage(), var4);
                logger.error("com.base.adf.utils.date2XMLDatetime-->SToolUtils", var4.getMessage(), "is execution");
            }

            return xmlDatetime;
        }
    }

    public static boolean dateFormat(String dateValue) {
        LOGGER.info(dateValue);
        return false;
    }

    public static int object2Int(Object intValue) {
        if (null == intValue) {
            return 0;
        } else {
            boolean var1 = false;

            int resultValue;
            try {
                resultValue = (int)Double.parseDouble(object2String(intValue));
            } catch (NumberFormatException var3) {
                logger.error("Error", "com.base.adf.common.utils.SToolUtils-->object2Int====>", var3.getMessage());
                resultValue = 0;
            }

            return resultValue;
        }
    }

    public static InputStream string2InputStream(String value) {
        InputStream inputStream = new ByteArrayInputStream(value.getBytes());
        return inputStream;
    }

    public static String inputStream2String(InputStream input) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];

        int n;
        try {
            while((n = input.read(b)) != -1) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException var4) {
            LOGGER.error(var4.getMessage(), var4);
        }

        return out.toString();
    }

    public static JSONObject convertResultJSONObj(String resultStatus, String resultMessage, long resultCount, Object obj) {
        JSONObject json = new JSONObject();
        json.put("status", resultStatus);
        json.put("msg", resultMessage);
        json.put("count", resultCount);
        json.put("data", obj);
        return json;
    }

    public static Map<String, Object> fastJsonObj2Map(JSONObject jsonObject) {
        Map<String, Object> accountmap = new HashMap();
        Iterator var2 = jsonObject.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            Object value = jsonObject.get(key);
            accountmap.put(key, value);
        }

        return accountmap;
    }

    public static String remove_AndNextChar2UpperCase(String param) {
        if (param != null && !"".equals(param.trim())) {
            param = param.toLowerCase();
            if ('_' == param.charAt(0)) {
                param = param.substring(1);
            }

            if ('_' == param.charAt(param.length() - 1)) {
                param = param.substring(0, param.length() - 1);
            }

            StringBuilder sb = new StringBuilder(param);
            Matcher mc = Pattern.compile("_").matcher(param);
            int var3 = 0;

            while(mc.find()) {
                int position = mc.end() - var3++;
                sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String firstChar2UpperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char)(ch[0] - 32);
        }

        return new String(ch);
    }

    public static String firstChar2LowerCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char)(ch[0] + 32);
        }

        return new String(ch);
    }

    public static Object packageObject(Object str) {
        return isNumber(str) ? str : "'" + str + "'";
    }

    public static boolean isNumber(Object str) {
        String telNum = "^(\\(\\d{3,4}\\)|(\\d{3,4}-)|\\d{3,4}—)?\\d{7,8}$";
        String reg = "^[0-9]+(\\.[0-9]+)?$";
        String target = null;
        if (null != str) {
            target = str.toString();
            return target.matches(telNum) ? false : target.matches(reg);
        } else {
            return false;
        }
    }

    public static <E> List<E> iterator2List(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List)iterable;
        } else {
            ArrayList<E> list = new ArrayList();
            if (iterable != null) {
                Iterator var2 = iterable.iterator();

                while(var2.hasNext()) {
                    E e = (E) var2.next();
                    list.add(e);
                }
            }

            return list;
        }
    }
}
