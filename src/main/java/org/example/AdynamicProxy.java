package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AdynamicProxy implements InvocationHandler {
    private Ainterface a;
    private Binterface b;

    public AdynamicProxy(Ainterface a,Binterface b)
    {
        this.a = a;
        this.b = b;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        if (Ainterface.class.isAssignableFrom(method.getDeclaringClass())) {
            result = method.invoke(a, args);
        } else if (Binterface.class.isAssignableFrom(method.getDeclaringClass())) {
            result = method.invoke(b, args);
        } else {
            throw new IllegalArgumentException("Unsupported interface");
        }
        String methodName = method.getName();
        System.out.println("调用了" + methodName);
        return result;
    }
}
