package zk_api.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;


/**
 * zk客户端操作的API 增删改查节点
 * @author 化十
 *
 */

public class ZK_Demo2 {
	
	
	static ZooKeeper zk = null;


	public void init() throws Exception{
		zk = new ZooKeeper("192.168.56.100:2181", 2000 , null);
	}


	public void lsWatch() throws Exception{
		zk.getChildren("/test", new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//要判断该监听是否为同步
				if(event.getState() == Event.KeeperState.SyncConnected
				&& event.getType() == Event.EventType.NodeChildrenChanged){
					System.out.println("子节点数据发生了变化");
				}else {
					System.out.println("子节点数据没发送变化");
				}
			}
		});
		Thread.sleep(Integer.MAX_VALUE);
	}

	
}
