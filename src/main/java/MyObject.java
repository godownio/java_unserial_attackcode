import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class MyObject extends ObjectInputStream {
    public MyObject(InputStream in) throws IOException {
        super(in);
    }

    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        return desc.getName().equals("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl") ? null : super.resolveClass(desc);
    }
}
