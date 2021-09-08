package org.geektimes.project.week10.consumer;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance;
import org.geektimes.project.week10.provider.service.IEchoService;

import static org.apache.dubbo.common.constants.CommonConstants.COMPOSITE_METADATA_STORAGE_TYPE;

/**
 * @author FanJiang
 * @since
 */
public class Application {

    public static void main(String[] args) throws Exception {

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("zookeeper-dubbo-consumer", app -> app.metadata(COMPOSITE_METADATA_STORAGE_TYPE))
                .registry("zookeeper", builder -> builder.address("zookeeper://127.0.0.1:2181")
                        .useAsConfigCenter(true)
                        .useAsMetadataCenter(true))
                .reference("echo", builder -> builder.interfaceClass(IEchoService.class)
                        .loadbalance(ConsistentHashLoadBalance.NAME)
                        .services("zookeeper-dubbo-provider"))
                .start();

        IEchoService echoService = bootstrap.getCache().get(IEchoService.class);

        for (int i = 0; i < 20; i++) {
            System.out.println(echoService.echo("Hello,World" + i));
        }

        bootstrap.stop();
    }
}
