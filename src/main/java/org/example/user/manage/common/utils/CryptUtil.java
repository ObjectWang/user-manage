package org.example.user.manage.common.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

public class CryptUtil {

    /**
     * 
     */
    private static final String NUMBER = "1234567890abcdef";
    /**
     * 初始化向量
     */
    private static final String IV = "qwertyuiopas2356";
    /**
     * 加密算法
     */
    public static final String ALGORITHM = "SM4";
    /**
     * 加密工作模式：GCM
     * 数据填充模式：PKCS5Padding
     */
    public static final String ALGORITHM_MODEL_GCM_PADDING = "SM4/GCM/NoPadding";
    /**
     * 随机数的长度
     */
    public static final int NONCE_LENGTH = 128;

    static {
        // 添加安全提供者（SM2，SM3，SM4等加密算法，CBC、CFB等加密模式，PKCS7Padding等填充方式，不在Java标准库中，由BouncyCastleProvider实现）
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * base64加密
     *
     * @param plain
     * @return
     */
    public static String base64Encrypt(String plain) {
        return Base64.getEncoder().encodeToString(plain.getBytes());
    }

    /**
     * base64解密
     *
     * @param secret
     * @return
     */
    public static String base64Decrypt(String secret) {
        return new String(Base64.getDecoder().decode(secret));
    }

    /**
     * sm3加密
     *
     * @param plainString
     * @return
     */
    public static String sm3Encrypt(String plainString) {
        String cipherString = null;
        try {
            // 创建SM3Digest对象
            SM3Digest sm3Digest = new SM3Digest();
            // 初始化SM3计算
            sm3Digest.update(plainString.getBytes(StandardCharsets.UTF_8), 0, plainString.length());
            // 创建输出缓冲区
            byte[] cipherBytes = new byte[sm3Digest.getDigestSize()];
            // 计算SM3摘要
            sm3Digest.doFinal(cipherBytes, 0);
            // 输出16进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : cipherBytes) {
                sb.append(String.format("%02x", b));
            }
            cipherString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherString;
    }


    /**
     * 使用传输的密钥和初始化向量进行加密
     *
     * @param plainText 需要加密的内容
     * @return 加密之后的内容
     * @throws Exception 加密过程中可能发生的异常
     */
    public static String encryptWithGCM(String plainText, String key, String iv) throws Exception {
        return encryptWithGCM(plainText, key.getBytes(), iv.getBytes());
    }

    /**
     * 使用传输的密钥和初始化向量进行加密
     *
     * @param plainText 需要加密的内容
     * @param key       密钥
     * @param iv        初始化向量
     * @return 加密之后的内容
     * @throws Exception 加密过程中可能发生的异常
     */
    public static String decryptWithGCM(String plainText, String key, String iv) throws Exception {
        return decryptWithGCM(plainText, key.getBytes(), iv.getBytes());
    }

    /**
     * 使用配置的密钥和初始化向量进行加密
     *
     * @param plainText 需要加密的内容
     * @return 加密之后的内容
     * @throws Exception 加密过程中可能发生的异常
     */
    public static String sm4EncryptWithGCM(String plainText) throws Exception {
        return encryptWithGCM(plainText, NUMBER.getBytes(), IV.getBytes());
    }

    /**
     * 使用配置的密钥和初始化向量进行解密
     *
     * @param cipherText 需要解密的内容
     * @return 解密之后的内容
     * @throws Exception 解密过程中可能发生的异常
     */
    public static String sm4DecryptWithGCM(String cipherText) throws Exception {
        return decryptWithGCM(cipherText, NUMBER.getBytes(), IV.getBytes());
    }

    /**
     * 使用SM4-GCM模式加密
     *
     * @param plainText 需要加密的内容
     * @param keyBytes  密钥字节数组
     * @param ivBytes   初始化向量字节数组
     * @return 加密之后的内容
     * @throws Exception 加密过程中可能发生的异常
     */
    public static String encryptWithGCM(String plainText, byte[] keyBytes, byte[] ivBytes) throws Exception {
        SecretKeySpec sm4Key = new SecretKeySpec(keyBytes, ALGORITHM);
        GCMParameterSpec ivSpec = new GCMParameterSpec(NONCE_LENGTH, ivBytes);

        Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL_GCM_PADDING, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, sm4Key, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 使用SM4-GCM模式解密
     *
     * @param cipherText 需要解密的内容
     * @param keyBytes   密钥字节数组
     * @param ivBytes    初始化向量字节数据
     * @return 解密之后内容
     * @throws Exception 解密过程中可能发生的异常
     */
    public static String decryptWithGCM(String cipherText, byte[] keyBytes, byte[] ivBytes) throws Exception {
        SecretKeySpec sm4Key = new SecretKeySpec(keyBytes, ALGORITHM);
        GCMParameterSpec ivSpec = new GCMParameterSpec(NONCE_LENGTH, ivBytes);

        Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL_GCM_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, sm4Key, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 使用SecureRandom生成指定长度的密钥或IV
     *
     * @param length 密钥或IV的长度（字节数）
     * @return 生成的随机字节数组
     */
    public static byte[] generateKey(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    /**
     * 生成指定长度的初始化向量（IV）
     *
     * @param length IV的长度（字节数）
     * @return 生成的随机字节数组
     */
    public static byte[] generateIV(int length) {
        // IV的生成方式与密钥相同，使用SecureRandom
        return generateKey(length);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("使用配置的密钥和初始化向量");
        String plainText = "aa123123";
        System.out.println("base64:" + base64Encrypt(plainText));
        String cipherText = sm4EncryptWithGCM(plainText);
        System.out.println("加密后：" + cipherText);

        String sm3 = sm3Encrypt(plainText);
        System.out.println("sm3 = " + sm3);

        String decryptedText = sm4DecryptWithGCM(cipherText);
        System.out.println("解密后：" + decryptedText);
    }

}
