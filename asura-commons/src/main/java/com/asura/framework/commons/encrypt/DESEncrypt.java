/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.encrypt;

import com.asura.framework.commons.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

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
 * @date 2016/11/2 15:28
 * @since 1.0
 */
public class DESEncrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESEncrypt.class);

    /**
     * DES加密
     *
     * @param content
     *
     * @return
     */
    public static String DESEncrypt(final String ivString, final String keyString, final String content) {
        try {
            if (Check.isNullOrEmpty(content)) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            DESKeySpec dks = new DESKeySpec(keyString.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));
            return byteArr2HexStr(result);
        } catch (Exception e) {
            LOGGER.error("ENCRYPT ERROR:" + e);
        }
        return null;
    }

    public static String DESDecrypt(final String ivString, final String keyString, final String content) {
        try {
            if (Check.isNullOrEmpty(content)) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            DESKeySpec dks = new DESKeySpec(keyString.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(hexStr2ByteArr(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            LOGGER.error("ENCRYPT ERROR:" + e);
        }
        return null;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB
     *         需要转换的byte数组
     *
     * @return 转换后的字符串
     *
     * @throws Exception
     *         本方法不处理任何异常，所有异常全部抛出
     */
    private static String byteArr2HexStr(final byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn
     *         需要转换的字符串
     *
     * @return 转换后的byte数组
     *
     * @throws Exception
     *         本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    private static byte[] hexStr2ByteArr(final String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
}
