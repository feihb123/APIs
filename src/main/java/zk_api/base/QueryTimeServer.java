package zk_api.base;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class QueryTimeServer {
    ZooKeeper zk = null;
    public QueryTimeServer() throws Exception {
        //要连接的服务器列表
        String serverStr = "192.168.56.200:2181,192.168.56.201:2181,192.168.56.202:2181";
        zk = new ZooKeeper(serverStr,20000,null);
    }
    //时间查询服务器向zk服务器注册ip:port 信息
    public void registerServerInfo(String ip,String port) throws Exception {

        //首先要判断父节点是否存在
        Stat exists = zk.exists("/Server",false);
        //如果父节点不存在新建父节点
        if(exists == null){
            zk.create("/Server",null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("父节点创建成功");
        }

        //创建子节点
        String child = zk.create("/Server/server",(ip+":"+port).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("服务器向zk注册成功，注册节点为"+child);
    }

    public static void main(String[] args)throws Exception {
        QueryTimeServer server = new QueryTimeServer();
        //执行注册方法
        server.registerServerInfo(args[0],args[1]);
        int port = Integer.parseInt(args[1]);
        QueryTimeService service = new QueryTimeService(port);
        service.start();

        //Thread.sleep(20000);
    }
}
