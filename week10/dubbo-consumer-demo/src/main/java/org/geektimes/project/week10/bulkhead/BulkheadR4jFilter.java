package org.geektimes.project.week10.bulkhead;

import io.github.resilience4j.bulkhead.Bulkhead;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.*;

import java.lang.reflect.Method;
import java.util.concurrent.*;

import static java.lang.String.format;

/**
 * @author FanJiang
 * @since
 */
@Activate(
        group = {"consumer", "provider"}
)
public class BulkheadR4jFilter implements Filter, Filter.Listener {

    private final ConcurrentMap<org.geektimes.project.week10.bulkhead.Bulkhead, ExecutorService> executorsCache = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<org.geektimes.project.week10.bulkhead.Bulkhead, Semaphore> semaphoresCache = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(BulkheadR4jFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("consumer-filter");
        String methodName = RpcContext.getContext().getMethodName();
        Class<?>[] parameterTypes = RpcContext.getContext().getParameterTypes();
        org.geektimes.project.week10.bulkhead.Bulkhead bulkhead = null;
        try {
            Method method = invoker.getInterface().getDeclaredMethod(methodName, parameterTypes);
            boolean annotationPresent = method.isAnnotationPresent(org.geektimes.project.week10.bulkhead.Bulkhead.class);
            if (annotationPresent) {
                bulkhead = method.getDeclaredAnnotation(org.geektimes.project.week10.bulkhead.Bulkhead.class);
            }
            if (bulkhead == null) {
                return invoker.invoke(invocation);
            }
            return executeBulkhead(invoker, invocation, bulkhead);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }


    private Result executeBulkhead(Invoker<?> context, Invocation invocation, org.geektimes.project.week10.bulkhead.Bulkhead bulkhead) throws Exception {

        Bulkhead bulkheadExec = BulkheadFactory.newBulkhead(context.getInterface().getName(), bulkhead);
        return bulkheadExec.executeSupplier(() -> context.invoke(invocation));
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {

    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }

}
