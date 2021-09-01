package org.geektimes.event.distributed.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.geektimes.event.distributed.DistributedEventObject;
import org.geektimes.event.reactive.stream.ListenerSubscriberAdapter;
import org.geektimes.reactive.streams.SimplePublisher;

import java.nio.charset.StandardCharsets;
import java.util.EventObject;

/**
 * @author FanJiang
 * @since
 */
public class ZookeeperEventPublisher {

    private final SimplePublisher<EventObject> simplePublisher;

    private final CuratorFramework client;

    private final String topic;

    public ZookeeperEventPublisher(String uri, String topic) {
        this.simplePublisher = new SimplePublisher();
        this.topic = topic;
        this.client = CuratorFrameworkFactory.newClient(uri,
                new RetryNTimes(10, 5000));
        client.start();
        this.initBuildInSub();
    }

    private void initBuildInSub() {
        // 初始化内建观察者
        // 用于观察该类的 publish 方法。
        // 当内建观察者监听到 publish 方法被执行时，触发内建观察者的观察行为
        // 该行为会向 zk 中的某个节点写入数据。
        simplePublisher.subscribe(new ListenerSubscriberAdapter((event -> {
            String path = "/" +  topic;
            Stat stat = client.checkExists().forPath(path);
            if (stat == null) {
                client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path, "data".getBytes(StandardCharsets.UTF_8));
            }
            client.setData().forPath(path, event.getSource().toString().getBytes(StandardCharsets.UTF_8));
        })));
    }

    public void publish(Object value) {
        simplePublisher.publish(new DistributedEventObject(value));
    }

    public static void main(String[] args) throws Exception {
        ZookeeperEventPublisher subscriber = new ZookeeperEventPublisher("127.0.0.1:2181", "test1");
        subscriber.publish("我是 Zookeeper !!!");
        Thread.sleep(1000);
        subscriber.publish("我是 Zookeeper !!!2");
        Thread.sleep(1000);
        subscriber.publish("我是 Zookeeper !!!3");
        Thread.sleep(1000);
        subscriber.publish("我是 Zookeeper !!!4");
    }
}
