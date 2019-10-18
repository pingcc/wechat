package com.ping.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created  on 2019/2/19.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public class StringUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static String nullStrToEmpty(Object str) {
        return str == null ? "" : (str instanceof String ? (String)str : str.toString());
    }

    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            char c = str.charAt(0);
            return Character.isLetter(c) && !Character.isUpperCase(c) ? (new StringBuilder(str.length())).append(Character.toUpperCase(c)).append(str.substring(1)).toString() : str;
        }
    }

    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", var2);
            }
        } else {
            return str;
        }
    }

    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        } else {
            String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
            Pattern hrefPattern = Pattern.compile(hrefReg, 2);
            Matcher hrefMatcher = hrefPattern.matcher(href);
            return hrefMatcher.matches() ? hrefMatcher.group(1) : href;
        }
    }

    public static String htmlEscapeCharsToString(String source) {
        return isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        } else {
            char[] source = s.toCharArray();

            for(int i = 0; i < source.length; ++i) {
                if (source[i] == 12288) {
                    source[i] = ' ';
                } else if (source[i] >= '！' && source[i] <= '～') {
                    source[i] -= 'ﻠ';
                } else {
                    source[i] = source[i];
                }
            }

            return new String(source);
        }
    }

    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        } else {
            char[] source = s.toCharArray();

            for(int i = 0; i < source.length; ++i) {
                if (source[i] == ' ') {
                    source[i] = 12288;
                } else if (source[i] >= '!' && source[i] <= '~') {
                    source[i] += 'ﻠ';
                } else {
                    source[i] = source[i];
                }
            }

            return new String(source);
        }
    }

    public static String replaceBlanktihuan(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(String... strings) {
        boolean result = true;
        String[] var2 = strings;
        int var3 = strings.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String string = var2[var4];
            if (isNotEmpty(string)) {
                result = false;
                break;
            }
        }

        return result;
    }

    public static boolean isNotEmpty(String... strings) {
        boolean result = true;
        String[] var2 = strings;
        int var3 = strings.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String string = var2[var4];
            if (isEmpty(string)) {
                result = false;
                break;
            }
        }

        return result;
    }

    public static String filterEmpty(String string) {
        return isNotEmpty(string) ? string : "";
    }

    public static String replace(String string, char oldchar, char newchar) {
        char[] chars = string.toCharArray();

        for(int w = 0; w < chars.length; ++w) {
            if (chars[w] == oldchar) {
                chars[w] = newchar;
                break;
            }
        }

        return new String(chars);
    }

    public static String[] split(String string, char ch) {
        ArrayList<String> stringList = new ArrayList();
        char[] chars = string.toCharArray();
        int nextStart = 0;

        for(int w = 0; w < chars.length; ++w) {
            if (ch == chars[w]) {
                stringList.add(new String(chars, nextStart, w - nextStart));
                nextStart = w + 1;
                if (nextStart == chars.length) {
                    stringList.add("");
                }
            }
        }

        if (nextStart < chars.length) {
            stringList.add(new String(chars, nextStart, chars.length - 1 - nextStart + 1));
        }

        return (String[])stringList.toArray(new String[stringList.size()]);
    }

    public static int countLength(String string) {
        int length = 0;
        char[] chars = string.toCharArray();

        for(int w = 0; w < string.length(); ++w) {
            char ch = chars[w];
            if (ch >= 913 && ch <= '￥') {
                ++length;
                ++length;
            } else {
                ++length;
            }
        }

        return length;
    }

    private static char[] getChars(char[] chars, int startIndex) {
        int endIndex = startIndex + 1;
        if (Character.isDigit(chars[startIndex])) {
            while(endIndex < chars.length && Character.isDigit(chars[endIndex])) {
                ++endIndex;
            }
        }

        char[] resultChars = new char[endIndex - startIndex];
        System.arraycopy(chars, startIndex, resultChars, 0, resultChars.length);
        return resultChars;
    }

    public static boolean isAllDigital(char[] chars) {
        boolean result = true;

        for(int w = 0; w < chars.length; ++w) {
            if (!Character.isDigit(chars[w])) {
                result = false;
                break;
            }
        }

        return result;
    }

    public static String removeChar(String string, char ch) {
        StringBuffer sb = new StringBuffer();
        char[] var3 = string.toCharArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char cha = var3[var5];
            if (cha != '-') {
                sb.append(cha);
            }
        }

        return sb.toString();
    }

    public static String removeChar(String string, int index) {
        String result = null;
        char[] chars = string.toCharArray();
        if (index == 0) {
            result = new String(chars, 1, chars.length - 1);
        } else if (index == chars.length - 1) {
            result = new String(chars, 0, chars.length - 1);
        } else {
            result = new String(chars, 0, index) + new String(chars, index + 1, chars.length - index);
        }

        return result;
    }

    public static String removeChar(String string, int index, char ch) {
        String result = null;
        char[] chars = string.toCharArray();
        if (chars.length > 0 && chars[index] == ch) {
            if (index == 0) {
                result = new String(chars, 1, chars.length - 1);
            } else if (index == chars.length - 1) {
                result = new String(chars, 0, chars.length - 1);
            } else {
                result = new String(chars, 0, index) + new String(chars, index + 1, chars.length - index);
            }
        } else {
            result = string;
        }

        return result;
    }

    public static String filterBlank(String string) {
        return "".equals(string) ? null : string;
    }

    public static String toLowerCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex), str.substring(beginIndex, endIndex).toLowerCase(Locale.getDefault()));
    }

    public static String toUpperCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex), str.substring(beginIndex, endIndex).toUpperCase(Locale.getDefault()));
    }

    public static String firstLetterToLowerCase(String str) {
        return toLowerCase(str, 0, 1);
    }

    public static String firstLetterToUpperCase(String str) {
        return toUpperCase(str, 0, 1);
    }

    public static String MD5(String string) {
        String result = null;

        try {
            char[] charArray = string.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for(int i = 0; i < charArray.length; ++i) {
                byteArray[i] = (byte)charArray[i];
            }

            StringBuffer hexValue = new StringBuffer();
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(byteArray);

            for(int i = 0; i < md5Bytes.length; ++i) {
                int val = md5Bytes[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            result = hexValue.toString();
        } catch (Exception var8) {
            LOGGER.error(var8.getMessage(), var8);
        }

        return result;
    }

    public static boolean startsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        } else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(0, newLength, newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        } else {
            return false;
        }
    }

    public static boolean endsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        } else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(sourceLength - newLength, sourceLength, newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        } else {
            return false;
        }
    }

    public static String checkLength(String string, int maxLength, String appendString) {
        if (string.length() > maxLength) {
            string = string.substring(0, maxLength);
            if (appendString != null) {
                string = string + appendString;
            }
        }

        return string;
    }

    public static String checkLength(String string, int maxLength) {
        return checkLength(string, maxLength, "…");
    }
}
