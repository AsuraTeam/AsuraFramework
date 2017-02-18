/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
 * @date 2016/11/2 11:37
 * @since 1.0
 */
public class MD5Encrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Encrypt.class);

    /**
     * MD5加密
     *
     * @param input
     *         需要加密的字符串
     *
     * @return 加密后的字符串
     */
    public static String MD5Encrypt(final String input) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("ENCRYPT ERROR:" + e);
            return null;
        } catch (Exception e) {
            LOGGER.error("ENCRYPT ERROR:" + e);
            return null;
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
