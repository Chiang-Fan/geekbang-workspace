package org.geektimes.project.week10.provider;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.geektimes.project.week10.provider.service.EchoService;
import org.geektimes.project.week10.provider.service.IEchoService;

import static org.apache.dubbo.common.constants.CommonConstants.COMPOSITE_METADATA_STORAGE_TYPE;

/**
 * @author FanJiang
 * @since
 */
public class Application {

    public static void main(String[] args) {
        DubboBootstrap.getInstance()
                .application("zookeeper-dubbo-provider", app -> app.metadata(COMPOSITE_METADATA_STORAGE_TYPE))
                .registry(builder -> builder.address("127.0.0.1:2181")
                                .protocol("zookeeper")
//                        .parameter(REGISTRY_TYPE_KEY, SERVICE_REGISTRY_TYPE)
                )
                .protocol("dubbo", builder -> builder.port(-1).name("dubbo"))
                .service("echo", builder -> builder.interfaceClass(IEchoService.class).ref(new EchoService())
                        .protocolIds("dubbo,rest"))
                .start()
                .await();
    }
}
