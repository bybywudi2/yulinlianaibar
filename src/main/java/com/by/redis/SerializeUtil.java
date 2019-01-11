package com.by.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null)
            return null;
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List unserialize(List<byte[]> bytes) {
        if (bytes == null)
            return null;
        ByteArrayInputStream bais = null;
        List list = new ArrayList();
        try {
            for(int i=0;i<bytes.size();i++){
                bais = new ByteArrayInputStream(bytes.get(i));
                ObjectInputStream ois = new ObjectInputStream(bais);
                list.add(ois.readObject());
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}