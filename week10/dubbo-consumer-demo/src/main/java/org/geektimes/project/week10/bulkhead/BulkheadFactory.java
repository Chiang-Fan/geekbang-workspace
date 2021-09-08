package org.geektimes.project.week10.bulkhead;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;

import java.time.Duration;

/**
 * @author FanJiang
 * @since
 */
public class BulkheadFactory {

    public static Bulkhead newBulkhead(String bulkheadName) {
        // Create a custom configuration for a Bulkhead
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(100)
                .maxWaitDuration(Duration.ofMillis(1))
                .build();
        // Create a BulkheadRegistry with a custom global configuration
        BulkheadRegistry bulkheadRegistry =
                BulkheadRegistry.of(config);
        return Bulkhead.of(bulkheadName, config);
    }


    public static Bulkhead newBulkhead(String name, org.geektimes.project.week10.bulkhead.Bulkhead bulkhead) {
        // Create a custom configuration for a Bulkhead
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(bulkhead.value())
                .maxWaitDuration(Duration.ofMillis(1))
                .build();
        return Bulkhead.of(name, config);
    }
}
