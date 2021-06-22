package com.edu.zzh.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Author: zzh
 * @Date: 2021/3/18 10:23
 * @Description:
 */
public class AES {
    public static String getMsisdn(String encrypted,String ASE_KEY) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        //将字符串转化为base64编码的字节数组
        byte[] encryptedBase64Bytes = encrypted.getBytes();
        //将base64编码的字节数组转化为在加密之后的字节数组
        byte[] byteMi = Base64.decodeBase64(encryptedBase64Bytes);
        IvParameterSpec zeroIv = new IvParameterSpec(ASE_KEY.substring(0,16).getBytes());
        SecretKeySpec key = new SecretKeySpec(ASE_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //与加密时不同MODE:Cipher.DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

/*    public static void main(String[] args) throws Exception {
        //String msisdn = getMsisdn("H5Exk7Zlrim1HnRTVylLaA==","7584e3d690f2997555ee2fbfc5d20520");
        //System.out.println(msisdn);
        String key = getRandomString();
        String encrypt = encrypt(key,"123456");
        String decrypt = decrypt(key,encrypt);
        System.out.println(key);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }*/
        private static final String KEY_ALGORITHM = "AES";
        private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

        /**
         * AES加密
         *
         * @param passwd
         *         加密的密钥
         * @param content
         *         需要加密的字符串
         * @return 返回Base64转码后的加密数据
         * @throws Exception
         */
        public static String encrypt(String passwd, String content) throws Exception {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] byteContent = content.getBytes("utf-8");

            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(passwd));

            // 加密
            byte[] result = cipher.doFinal(byteContent);

            //通过Base64转码返回
            return Base64.encodeBase64String(result);
        }

        /**
         * AES解密
         *
         * @param passwd
         *         加密的密钥
         * @param encrypted
         *         已加密的密文
         * @return 返回解密后的数据
         * @throws Exception
         */
        public static String decrypt(String passwd, String encrypted) throws Exception {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(passwd));

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(result, "utf-8");
        }

        /**
         * 生成加密秘钥
         *
         * @return
         */
        private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
            //返回生成指定算法密钥生成器的 KeyGenerator 对象
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            //AES 要求密钥长度为 128
            kg.init(128, random);

            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        }
    //length用户要求产生字符串的长度
    public static String getRandomString(){
        final Integer length = 10;
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
