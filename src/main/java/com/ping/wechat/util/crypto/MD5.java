package com.ping.wechat.util.crypto;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MD5 {
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;
    private static final String DES = "DES";
    private static final String MD5 = "MD5";
    private static final String KEY = "opeddsaead323353484591dadbc382a18340bf83414536";

    public MD5() {
    }

    public static void main(String[] arg) throws Exception {
        String myinfo = "我的测试信息";
        System.out.println(myinfo);
        System.out.println(md5Encode(myinfo));
        String s = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZW1vIiwiaWF0IjoxNTA4ODI5NTIzLCJzdWIiOiJ7XCJiaXJ0aERheVwiOlwiMjQ3NjgwMDAwMDAwXCIsXCJlbWFpbEFkZHJlc3NcIjpcIlwiLFwiZW5jcnlwYXRlZFBhc3N3b3JkXCI6XCI5NkU3OTIxODk2NUVCNzJDOTJBNTQ5REQ1QTMzMDExMlwiLFwibGV2ZWxcIjpcIuazqOWGjOS8muWRmFwiLFwibWVtYmVySWRcIjoyNzIsXCJtbWJOYW1lXCI6XCI3OGJ2RWJsNzdvTngwXCIsXCJtbWJUeXBlXCI6XCJSRUdJU1RFUkVEXCIsXCJtb2JpbGVOdW1iZXJcIjpcIjE4MDc4MTQ1NzkxXCIsXCJzZXhcIjpcIk1hbGVcIixcInRpY2tldFRpbWVcIjpcIjVcIn0iLCJleHAiOjE1MDg4Mjk1ODh9.eEL_ihCHzDd1jfc3B3du2pw5yYBQ4TGinyAzLwKsP_c";
        System.out.println("==========================================");
        byte[] value = compress(s.getBytes());
        System.out.println(new String(value));
        System.out.println(value);
        System.out.println(new String(decompress(value)));
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + MD5(s));
        System.out.println("MD5后再加密：" + KL(MD5(s)));
        System.out.println("解密为MD5后的：" + JM(KL(MD5(s))));
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)("0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | "0123456789ABCDEF".indexOf(hexChars[pos + 1]));
        }

        return result;
    }

    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            String hex = Integer.toHexString(b[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            hexString.append(hex.toUpperCase());
        }

        return hexString.toString();
    }

    public static boolean validPassword(String password, String passwordInDb) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] pwdInDb = hexStringToByte(passwordInDb);
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt);
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        return Arrays.equals(digest, digestInDb);
    }

    public static String getEncryptedPwd(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        MessageDigest md = null;
        md = MessageDigest.getInstance("MD5");
        md.update(salt);
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        byte[] pwd = new byte[digest.length + SALT_LENGTH];
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        return byteToHexString(pwd);
    }

    public static String MD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var8) {
            System.out.println(var8.toString());
            var8.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0; i < charArray.length; ++i) {
            byteArray[i] = (byte)charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for(int i = 0; i < md5Bytes.length; ++i) {
            int val = md5Bytes[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static String md5Encode(String input) {
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            alg.update(input.getBytes());
            byte[] digesta = alg.digest();
            return byte2Hex(digesta);
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
            String s = new String("a");
            System.out.println("原始：" + s);
            System.out.println("MD5后：" + MD5(s));
            System.out.println("MD5后再加密：" + KL(MD5(s)));
            System.out.println("解密为MD5后的：" + JM(KL(MD5(s))));
            return null;
        }
    }

    public static String byte2Hex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        String str = "";

        for(int i = 0; i < paramArrayOfByte.length; ++i) {
            str = Integer.toHexString(paramArrayOfByte[i] & 255);
            if (str.length() == 1) {
                localStringBuffer.append("0");
            }

            localStringBuffer.append(str);
        }

        return localStringBuffer.toString().toUpperCase();
    }

    public static String KL(String inStr) {
        char[] a = inStr.toCharArray();

        for(int i = 0; i < a.length; ++i) {
            a[i] = (char)(a[i] ^ 116);
        }

        String s = new String(a);
        return s;
    }

    public static String JM(String inStr) {
        char[] a = inStr.toCharArray();

        for(int i = 0; i < a.length; ++i) {
            a[i] = (char)(a[i] ^ 116);
        }

        String k = new String(a);
        return k;
    }

    public static String md5Encrypt(String data) {
        String resultString = null;

        try {
            resultString = new String(data);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception var3) {
            ;
        }

        return resultString;
    }

    private static String byte2hexString(byte[] bytes) {
        StringBuffer bf = new StringBuffer(bytes.length * 2);

        for(int i = 0; i < bytes.length; ++i) {
            if ((bytes[i] & 255) < 16) {
                bf.append("T0");
            }

            bf.append(Long.toString((long)(bytes[i] & 255), 16));
        }

        return bf.toString();
    }

    public static String desEncrypt(String data, String key) throws Exception {
        if (key == null) {
            key = "opeddsaead323353484591dadbc382a18340bf83414536";
        }

        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = (new BASE64Encoder()).encode(bt);
        return strs;
    }

    public static String desDecrypt(String data, String key) throws IOException, Exception {
        if (data == null) {
            return null;
        } else {
            if (key == null) {
                key = "opeddsaead323353484591dadbc382a18340bf83414536";
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        }
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, securekey, sr);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, sr);
        return cipher.doFinal(data);
    }

    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

        try {
            byte[] buf = new byte[1024];

            while(!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }

            output = bos.toByteArray();
        } catch (Exception var14) {
            output = data;
            var14.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        compresser.end();
        return output;
    }

    public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);

        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];
        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);

        try {
            byte[] buf = new byte[1024];

            while(!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }

            output = o.toByteArray();
        } catch (Exception var14) {
            output = data;
            var14.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        decompresser.end();
        return output;
    }

    public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);

        try {
            int i = 1024;
            byte[] buf = new byte[i];

            while((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return o.toByteArray();
    }
}
