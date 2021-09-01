package org.geektimes.event.distributed.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.geektimes.event.EventListener;
import org.geektimes.event.distributed.DistributedEventObject;
import org.geektimes.event.reactive.stream.ListenerSubscriberAdapter;
import org.geektimes.reactive.streams.SimplePublisher;

import java.nio.charset.StandardCharsets;
import java.util.EventObject;

/**
 * @author FanJiang
 * @since
 */
public class ZookeeperEventSubscriber {

    private final SimplePublisher<EventObject> simplePublisher;

    private final CuratorFramework client;

    private final String topic;

    public ZookeeperEventSubscriber(String uri, String topic) {
        this.simplePublisher = new SimplePublisher();
        this.client = CuratorFrameworkFactory.newClient(uri,
                new RetryNTimes(10, 5000));
        this.topic = topic;
        client.start();
        this.initBuildInPub();
    }

    private void initBuildInPub() {
        // 初始化内建生产者
        // 该生产者会在初始化节点监听 zk 某节点的数据，
        // 当有数据变动时，将最新的数据通过 simplePublisher.publish 发布给本地是 publisher
        String path = "/" +  topic;
        try {
            Stat stat = client.checkExists().forPath(path);
            if (stat == null) {
                throw new IllegalArgumentException("Topic 不存在");
            }
            NodeCache nodeCache = new NodeCache(client, path);
            nodeCache.start();
            nodeCache.getListenable().addListener(() -> {
                simplePublisher.publish(new DistributedEventObject(new String(nodeCache.getCurrentData().getData(), StandardCharsets.UTF_8)));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSubscriber(EventListener eventListener) {
        simplePublisher.subscribe(new ListenerSubscriberAdapter(eventListener));
    }

    public static void main(String[] args) throws Exception {
        ZookeeperEventSubscriber subscriber = new ZookeeperEventSubscriber("127.0.0.1:2181", "test1");
        subscriber.onSubscriber((eventObject) -> System.out.println(eventObject.getSource()));

        Thread.sleep(Long.MAX_VALUE);
    }
}
