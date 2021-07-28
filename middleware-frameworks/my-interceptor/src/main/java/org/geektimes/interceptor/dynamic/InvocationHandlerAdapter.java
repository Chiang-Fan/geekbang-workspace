package org.geektimes.interceptor.dynamic;

import org.geektimes.interceptor.ChainableInvocationContext;

import javax.interceptor.InvocationContext;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author FanJiang
 * @since
 */
public class InvocationHandlerAdapter implements InvocationHandler {

    private final Class<?> target;

    private final Object[] interceptors;

    public InvocationHandlerAdapter(Class<?> target, Object[] interceptors) {
        this.target = target;
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvocationContext delegateContext = new InvocationHandlerContext(target.newInstance(), method, args);
        ChainableInvocationContext context = new ChainableInvocationContext(delegateContext, interceptors);
        return context.proceed();
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClassLoader(),
                target.getInterfaces(), this);
    }
}
