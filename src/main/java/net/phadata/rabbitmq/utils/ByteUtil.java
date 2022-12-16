package net.phadata.rabbitmq.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author tanwei
 * @desc
 * @time 1/4/21 5:23 PM
 * @since 1.0.0
 */
public class ByteUtil {
    /**
     * 字节数组转16进制字符串
     * @param bytes
     * @return
     */
    public static String byte2HexString(byte[] bytes){
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 对象转byte
     * @param obj obj
     * @return
     */
    public static byte[] writeObj(Object obj) {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(obj);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
