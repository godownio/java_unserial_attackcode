//package org.example;
//
//import sun.misc.ProxyGenerator;
//
//import java.io.FileOutputStream;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Proxy;
//import java.util.function.BiFunction;
//
//public class AProxyTest {
//    public static void main(String[] args) throws Exception
//    {
//        Ainterface a = new AImpl();
//        Binterface b = new BImpl();
//
//        Binterface aproxy = (Binterface) Proxy.newProxyInstance(Ainterface.class.getClassLoader(),
//                new Class[]{Ainterface.class, Binterface.class},
//                new AdynamicProxy(a,b));
////        byte[] classFile = ProxyGenerator.generateProxyClass("org.example.AProxyExtract", a.getClass().getInterfaces());
////        try(FileOutputStream fos = new FileOutputStream("E:\\CODE_COLLECT\\Idea_java_ProTest\\Test\\AProxyExtract.class")) {
////            fos.write(classFile);
////            fos.flush();
////            System.out.println("代理类class文件写入成功");
////        } catch (Exception e) {
////            System.out.println("写文件错误");
////        }
//        aproxy.add();
//        aproxy.display();
//        aproxy.delete();
//    }
//}
