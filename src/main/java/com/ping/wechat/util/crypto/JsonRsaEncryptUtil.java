package com.ping.wechat.util.crypto;


import com.ping.wechat.util.PasswordUtil;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created  on 2019/8/14.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
public class JsonRsaEncryptUtil {
    //rsa公钥
    public static final String PUBLICKEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCM1lHzX1I8NMGSLm+hO+1cWSlIJNepCtyo3WLnIjE7pUbetM6v7ltngW6RC46mptuRDLe98/5D26TLheLwnBK+m/wIj4BFj+W9MG2u8RC7o/0naT8SCYFxHf1hSkG6tK4xczATv8OjZOPO8tG88YJ9Viy+BRT6dGzh960qeAa4YwIDAQAB";

    //rsa私钥
    public static final String PRIVATEKEY ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIzWUfNfUjw0wZIub6E77VxZKUgk16kK3KjdYuciMTulRt60zq/uW2eBbpELjqam25EMt73z/kPbpMuF4vCcEr6b/AiPgEWP5b0wba7xELuj/SdpPxIJgXEd/WFKQbq0rjFzMBO/w6Nk487y0bzxgn1WLL4FFPp0bOH3rSp4BrhjAgMBAAECgYBkhKby88qmy+SIZ/omcrYjnkN3iUwfC3CYWYI4g0/uSTU+yH9oU9ALHPcEMOJ8kUTOzuvpeFa2qfKBjCqAHnTFZPbYaB7CZ5UrjxywfJN2yojHyCiSzx6mydqYKlKjZYaLnWaiojy5GhAG/Sdb4168Sii5/74tirycJ99rHm0SUQJBAMgQsW5QhU3WuhWD7FU7NfAdUbPQ4neR4YXUGGIrssmw96Fxu6EOIgj7c4IOC2Fg/2ThV2iza8or6VqtI/gC2PkCQQC0Nnz7LPIqHz0jrfV0/g2pwzhdkfwluI7D43XE8IEI43awvtH3XJEqjot54Ww6GOEaZZEAkatARKTlPwOpES87AkEAmd5c2B8Np0QSciG4TgTwAvBJuiZZRuTnsmnhJv+8zepRSdWTHNclzgq2V7w9fHOBeTDmbRwxNph3LMok88Yc4QJBAK6HbrcbKlSc+GtLmEJ7oOPeSwJj1Zz6hlk0OuRGP9FJ6bL2uwYjQfvDHVUmedgyLB+SacHxUbbmRAQ46OSd3e0CQQCNXvJNyvWpu4A24Zck9sXFJjuESVG0cYp585cWsmJaPUj6/m7l19+8GiGN9ny00tWSsZrRLPcl05xu7p1a/NUa";



    /**
     * @param pwd 密码
     * @createTime 2019-08-17
     * @description 生成后端提现密码
     */
    public static String generateServerPwd(String pwd){
        pwd = PasswordUtil.SERVER_RANDOM+pwd;
        return DigestUtils.md5(pwd);
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
