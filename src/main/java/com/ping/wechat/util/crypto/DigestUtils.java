package com.ping.wechat.util.crypto;
import java.security.MessageDigest;
/**
 * Created  on 2019/9/29.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
public class DigestUtils {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private DigestUtils() {
        throw new AssertionError();
    }

    public static String md5(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(str.getBytes());
                return new String(encodeHex(messageDigest.digest()));
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    protected static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int var4 = 0; i < l; ++i) {
            out[var4++] = DIGITS_LOWER[(240 & data[i]) >>> 4];
            out[var4++] = DIGITS_LOWER[15 & data[i]];
        }

        return out;
    }
}

