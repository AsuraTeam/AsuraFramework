package com.asura.framework.conf.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

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
 * @date 2016/8/17 16:21
 * @since 1.0
 */
public class SerializableSerializer {

    public static Object deserialize(byte[] bytes) throws DataMarshallingException {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = inputStream.readObject();
            return object;
        } catch (ClassNotFoundException e) {
            throw new DataMarshallingException("Unable to find object class.", e);
        } catch (IOException e) {
            try {
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                throw new DataMarshallingException(e1);
            }
        }
    }

    public static byte[] serialize(Object serializable) throws DataMarshallingException {
        try {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
            stream.writeObject(serializable);
            stream.close();
            return byteArrayOS.toByteArray();
        } catch (IOException e) {
            try {
                return ((String)serializable).getBytes("UTF-8");
            } catch (UnsupportedEncodingException e1) {
                throw new DataMarshallingException(e1);
            }
        }
    }
}
