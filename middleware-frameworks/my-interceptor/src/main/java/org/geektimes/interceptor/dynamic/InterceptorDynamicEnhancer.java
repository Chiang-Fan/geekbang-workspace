package org.geektimes.interceptor.dynamic;

import org.geektimes.interceptor.InterceptorEnhancer;

/**
 * @author FanJiang
 * @since
 */
public class InterceptorDynamicEnhancer implements InterceptorEnhancer {

    public Object enhance(Class<?> target, Object... interceptors) {
        return new InvocationHandlerAdapter(target, interceptors).getProxy();
    }

    @Override
    public <T> T enhance(T source, Class<? super T> target, Object... interceptors) {
        return new InvocationHandlerAdapter(target, interceptors).getProxy();
    }
}
