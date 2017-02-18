/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/11/2 15:25
 * @since 1.0
 */
public class AESEncrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESEncrypt.class);

    private static final String AES_TYPE = "AES/ECB/PKCS5Padding";

    /**
     * @param keyStr
     *         密钥
     * @param plainText
     *         加密数据
     *
     * @return String 加密完数据
     *
     * @throws
     * @Description: AES 加密
     */
    public static String AESEncrypt(final String keyStr, final String plainText) throws Exception {
        byte[] encrypt;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
            return new String(Base64.encodeBase64(encrypt));
        } catch (Exception e) {
            LOGGER.error("aes encrypt failure : {}", e);
            throw e;
        }
    }

    /**
     * @param keyStr
     *         密钥
     * @param encryptData
     *         解密数据
     *
     * @return String
     *
     * @throws
     * @Description: 解密
     */
    public static String AESDecrypt(final String keyStr, final String encryptData) throws Exception {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes()));
            return new String(decrypt).trim();
        } catch (Exception e) {
            LOGGER.error("aes decrypt failure : {}", e);
            throw e;
        }
    }

    /**
     * @param key
     *
     * @Description: 封装KEY值
     */
    private static Key generateKey(final String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            LOGGER.error("get aes key failure : {}", e);
            throw e;
        }
    }

}
