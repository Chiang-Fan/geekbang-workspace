package org.geektimes.interceptor.dynamic;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.util.concurrent.Future;

/**
 * @author FanJiang
 * @since
 */
public interface IEchoService {

    @Timeout
    void echo(String message);

    @Asynchronous
    Future<Void> echo(Object message) ;

    @Retry(maxRetries = 3,
            delay = 0, maxDuration = 0, jitter = 0,
            retryOn = UnsupportedOperationException.class)
    @Fallback(fallbackMethod = "fallback")
    String echo(Long value) ;

    String fallback(Long value) ;
}
