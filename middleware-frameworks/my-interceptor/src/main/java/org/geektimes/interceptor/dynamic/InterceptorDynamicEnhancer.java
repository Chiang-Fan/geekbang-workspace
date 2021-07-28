package org.geektimes.interceptor.dynamic;

import java.lang.reflect.Proxy;

/**
 * @author FanJiang
 * @since
 */
public class InterceptorDynamicEnhancer {

    public Object enhance(Class<?> target, Object... interceptors) {
        return new InvocationHandlerAdapter(target, interceptors).getProxy();
    }
}
