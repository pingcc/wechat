package com.ping.wechat.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数据验证工具
 * 
 * @author <a href="mailto:">luozhongjie</a>
 * @version 1.0
 * @Name: NumValidatorUtils
 * @description: 数据校验工具类
 * @date:2018年12月19日 下午7:32:51
 */
public class NumValidatorUtils {

  /**
   * 判断数值是否超出规定的阀值
   *
   * @param value
   *          待判断的值
   * @param min
   *          最小阀值
   * @param max
   *          最大阀值
   * @return
   */
  public boolean isCrossing(double value, double max, double min) {
    boolean et = value <= max;

    boolean lt = value >= min;

    return et && lt;
  }

  /**
   * 判断是不是数字
   *
   * @param value
   * @return
   */
  public static boolean isNumeric(String value) {
    if (StringUtils.isBlank(value)) {
      return StringUtils.isNumeric(value);
    } else {
      return false;
    }
  }

  /**
   * 检查长整型数字是否为空。
   */
  public static boolean isNotEmpty(Long value) {
    return value != null && value != 0L;
  }

  /**
   * 检查所有的字符串是否都不为空。
   */
  public boolean areNotEmpty(String... values) {
    boolean result = true;
    for (String value : values) {
      result = result && StringUtils.isNotEmpty(value);
    }

    return result;
  }

  /**
   * 检查所有的字符串数组是否为数字。
   */
  public boolean areIsNumber(Object[]... arrays) {
    boolean result = true;
    for (Object[] array : arrays) {
      result = result && !ArrayUtils.isEmpty(array);
    }
    return result;
  }

  /**
   * 判断输入的值是否是[0-9]数字
   *
   * @param num
   * @return
   */
  public static boolean isNumber(String num) {
    String rule = "^\\d+$";
    Pattern pattern = Pattern.compile(rule);
    Matcher m = pattern.matcher(num);
    return m.matches();
  }

  /**
   * 判断输入的值是否是[0-9]整数或小数
   * 
   * @param num
   * @return
   */
  public static boolean isNumberAndDecimal(String num) {
    Pattern pattern = Pattern.compile("^\\d+$|^\\d+\\.\\d+$|-\\d+$");
    Matcher isNum = pattern.matcher(num);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

  /**
   * 判断输入的参数是否是符合格式化规则
   * 
   * @param right
   * @param rule
   * @return
   */
  public static boolean isFormatRight(String right, String rule) {
    Pattern pattern = Pattern.compile(rule);
    Matcher m = pattern.matcher(right);

    return m.matches();
  }

  /**
   * 去除字符串中的空格、回车、换行符、制表符
   * 
   * @param str
   * @return
   */
  public static String replaceBlank(String str) {
    String dest = "";
    if (str != null) {
      Pattern p = Pattern.compile("\\s*|\t|\r|\n");
      Matcher m = p.matcher(str);
      dest = m.replaceAll("");
    }
    return dest;
  }

  /**
   * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
   * 
   * @param s
   * @return
   */
  public static String removeAllBlank(String s) {
    String result = "";
    if (null != s && !"".equals(s)) {
      result = s.replaceAll("[　*| *| *|//s*]*", "");
    }
    return result;
  }

  /**
   * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
   * 
   * @param s
   * @return
   */
  public static String trim(String s) {
    String result = "";
    if (null != s && !"".equals(s)) {
      result = s.replaceAll("^[　*| *| *|//s*]*", "").replaceAll(
          "[　*| *| *|//s*]*$", "");
    }
    return result;
  }

  /**
   * 判读是否输入英文字母或者数字
   * 
   * @param num
   * @return
   */
  public static boolean isEnglishAndNumber(String num) {
    Pattern pattern = Pattern.compile("^[0-9a-zA_Z]+$");
    Matcher isNum = pattern.matcher(num);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }
  
  /**
   * 检查字符是否为url
   * 
   * @param pInput
   *            要检查的字符串
   * @return boolean
   */
  public static boolean isUrl(String pInput) {
      if (pInput == null) {
          return false;
      }
      String regEx = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";
      Pattern p = Pattern.compile(regEx);
      Matcher matcher = p.matcher(pInput);
      return matcher.find();
  }

  /**
   * 查看字符中是否为手机号
   * 
   * @param str
   *            原字符
   * @return boolean
   */
  public static boolean isPhone(String str) {
      boolean res = false;
      if (StringUtils.isNotBlank(str)) {
          String mobileRegex = "^(13|14|15|17|18)\\d{9}$";
          Pattern regex = Pattern.compile(mobileRegex);
          Matcher matcher = regex.matcher(str);
          res = matcher.find();
      }
      return res;
  }

  /**
   * 是否为QQ号
   * 
   * @param qq
   * @return
   */
  public static boolean isQQ(String qq) {
      boolean result = false;
      if (StringUtils.isNotBlank(qq)) {
          String qqRegex = "^[1-9][0-9]{4,14}$";
          Pattern regex = Pattern.compile(qqRegex);
          Matcher matcher = regex.matcher(qq);
          result = matcher.find();
      }
      return result;
  }

  /**
   * 是否为邮箱
   * 
   * @param email
   * @return
   */
  public static boolean isEmail(String email) {
      boolean result = false;
      if (StringUtils.isNotBlank(email)) {
          String qqRegex = "^[A-Za-z0-9][\\w+|\\-\\.]{2,}@(?:[a-z0-9](?:[-]?[a-z0-9]+)*\\.){1,3}(?:com|org|edu|net|gov|cn|hk|tw|jp|de|uk|fr|mo|eu|sg|kr)$";
          Pattern regex = Pattern.compile(qqRegex);
          Matcher matcher = regex.matcher(email);
          result = matcher.find();
      }
      return result;
  }

  /**
   * 过滤特殊字符
   * 
   * @param iputString
   *            原字符
   * @return 过滤后的字符
   */
  public static String stringFilter(String iputString) {
      // 清除掉所有特殊字符
      String regEx = "[`^&|'<>/?‘”“’]";
      if (iputString == null) {
          return "";
      }
      Pattern pattern = Pattern.compile(regEx);
      Matcher matcher = pattern.matcher(iputString);
      return matcher.replaceAll("").trim();
  }

  /**
   * 字符过滤器
   * 
   * @param iputString
   *            原字符
   * @param regEx
   *            正则表达式，过滤规则
   * @return
   */
  public static String stringFilter(String iputString, String regEx) {
      if (iputString == null) {
          return "";
      }
      Pattern pattern = Pattern.compile(regEx);
      Matcher matcher = pattern.matcher(iputString);
      return matcher.replaceAll("").trim();
  }

  /**
   * 字符过滤器
   * 
   * @param iputString
   *            原字符
   * @param regEx
   *            正则表达式，过滤规则
   * @param replaceString
   *            替换成的字符
   * @return
   */
  public static String stringFilter(String iputString, String regEx, String replaceString) {
      if (iputString == null) {
          return "";
      }
      Pattern pattern = Pattern.compile(regEx);
      Matcher matcher = pattern.matcher(iputString);
      return matcher.replaceAll(replaceString).trim();
  }

  /**
   * 去左空格
   * 
   * @param str
   *            原字符
   * @return
   */
  public static String leftTrim(String str) {
      if (str == null || str.equals("")) {
          return str;
      } else {
          return str.replaceAll("^[\40]+", "");
      }
  }

  /**
   * 去右空格
   * 
   * @param str
   *            原字符
   * @return
   */
  public static String rightTrim(String str) {
      if (str == null || str.equals("")) {
          return str;
      } else {
          return str.replaceAll("[\40]+$", "");
      }
  }

  /**
   * mysql 特殊字符转移过滤
   * 
   * @param str
   *            原字符
   * @return
   */
  public static String mysqlReplace(String str) {
      if (str != null) {
          str = str.replace("\\", "\\\\\\").replace("'", "\\'").replace("%", "\\%").replace("_", "\\_%");
      }
      return str;
  }


  /**
   * 功能：判断字符串是否为日期格式
   * 
   * @param str
   * @return
   */
  public static boolean isDate(String strDate) {
      Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
      Matcher m = pattern.matcher(strDate);
      if (m.matches()) {
          return true;
      } else {
          return false;
      }
  }

  /**
   * 手机号脱敏
   * 
   * @param mobile
   * @return
   */
  public static String mobileConceal(String mobile) {
      if (CommonFuntions.isEmptyObject(mobile)) {
          return "";
      }
      return org.apache.commons.lang.StringUtils.left(mobile, 3).concat(
              org.apache.commons.lang.StringUtils.removeStart(org.apache.commons.lang.StringUtils.leftPad(
                      org.apache.commons.lang.StringUtils.right(mobile, 3),
                      org.apache.commons.lang.StringUtils.length(mobile), "*"), "***"));
  }

  /**
   * 字符串脱敏
   * 
   * @param str
   *            脱敏字符串
   * @param beginIndex
   *            从第几个脱敏
   * @param retainNum
   *            尾上保留字符数
   * @return
   */
  public static String strConceal(String str, int beginIndex, int retainNum) {
      if (CommonFuntions.isEmptyObject(str)) {
          return "";
      }
      int strLength = str.length();
      if (strLength <= (beginIndex + retainNum)) {
          retainNum = 0;
      }
      String newStr = str.substring(beginIndex-1, str.length() - retainNum);
      String desStr = "";
      if (newStr.length() > 0) {
          for (int i = 0; i < newStr.length(); i++) {
              desStr += "*";
          }
      }
      return str.substring(0, beginIndex - 1) + desStr + str.substring(str.length() - retainNum, str.length());
  }

  /**
   * 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
   * 
   * @param value
   * @return
   */
  public static int getLength(String value) {
      int valueLength = 0;
      String chinese = "[\u0391-\uFFE5]";
      for (int i = 0; i < value.length(); i++) {
          // 获取一个字符
          String temp = value.substring(i, i + 1);
          // 判断是否为中文字符
          if (temp.matches(chinese)) {
              // 中文字符长度为2
              valueLength += 2;
          } else {
              // 其他字符长度为1
              valueLength += 1;
          }
      }
      return valueLength;
  }
}
