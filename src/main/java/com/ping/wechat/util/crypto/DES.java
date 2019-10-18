//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ping.wechat.util.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {
    private static final Logger LOGGER = LoggerFactory.getLogger(DES.class);
    private static final byte[] KEY = "7;9Ku7;:84VG*B78".getBytes();
    private static final String ALGORITHM = "DES";
    private static final byte[] IV = "sHjrydLq".getBytes();
    private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";
    private int code = 0;

    public DES() {
    }

    public DES(int code) {
        this.code = code;
    }

    public String encrypt(String source) {
        byte[] retByte = null;
        DESKeySpec dks = null;

        try {
            dks = new DESKeySpec(KEY);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            IvParameterSpec spec = new IvParameterSpec(IV);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(1, securekey, spec);
            retByte = cipher.doFinal(source.getBytes());
            String result = "";
            if (this.code == 0) {
                result = new String(retByte, "ISO-8859-1");
            } else if (this.code == 1) {
                result = Base64.encodeToString(retByte, false);
            } else {
                result = new String(retByte);
            }

            return result;
        } catch (Exception var9) {
            LOGGER.error(var9.getMessage(), var9);
            return null;
        }
    }

    public String decrypt(String encrypted) {
        byte[] retByte = null;
        DESKeySpec dks = null;

        try {
            dks = new DESKeySpec(KEY);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            IvParameterSpec spec = new IvParameterSpec(IV);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(2, securekey, spec);
            if (this.code == 0) {
                retByte = encrypted.getBytes("ISO-8859-1");
            } else if (this.code == 1) {
                retByte = Base64.decode(encrypted);
            } else {
                retByte = encrypted.getBytes();
            }

            retByte = cipher.doFinal(retByte);
            return new String(retByte, "utf-8");
        } catch (Exception var8) {
            LOGGER.error(var8.getMessage(), var8);
            return null;
        }
    }
}
