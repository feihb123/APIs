package zk_api.base;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 时间查询客户端
 * 1.连接zk服务器
 * 2.获取在线服务器列表
 * 3.从在线服务器列表随机一个访问
 */
public class QueryTimeClient {
    //保存在线服务器列表集合
    List<String> online = new ArrayList<>();
    ZooKeeper zk = null;

    public QueryTimeClient()throws  Exception {
        String serverStr = "192.168.56.200:2181,192.168.56.201:2181,192.168.56.202:2181";
        zk = new ZooKeeper(serverStr,20000,null);
    }

    public void getOnlineServer()throws Exception{
        List<String> list =new ArrayList<String>();
        List<String> children = zk.getChildren("/Server", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //端盘监听是否同步、子节点改变监听
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected &&
                watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                    //子节点发生改变 客户端需要立即更新服务器在线列表
                    try {
                        getOnlineServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        for(String c:children){
            byte[] data = zk.getData("/Server/" + c, null, null);
            String serverInfo = new String(data);
            list.add(serverInfo);
        }
        online = list;
        System.out.println("当前所在服务器列表:"+online);
    }

    /**
     * 发送请求，接收响应
     */
    public void sendRequest()  {
        try {
        Random random = new Random();
        while (true){
            int num = random.nextInt(online.size());
            //服务器信息
            String serverInfo = online.get(num);
            //从serverInfo中解析Ip/port
            String str[] = serverInfo.split(":");
            String ip = str[0];
            int port = Integer.parseInt(str[1]);
            Socket sc = null;

            sc = new Socket(ip,port);
            System.out.println("本次请求服务器为："+serverInfo);
            //获取输出流向服务器发送请求信息
            OutputStream outputStream = sc.getOutputStream();
            outputStream.write("请求当前时间?".getBytes());
            outputStream.flush();

            //获取输入流，读取服务器发送的响应信息
            InputStream inputStream = sc.getInputStream();
            byte[] b = new byte[100];
            int len = inputStream.read(b);
            System.out.println(new String(b,0,len));


            inputStream.close();
            outputStream.close();
            sc.close();
            //客户端每隔2s发送一次请求
            Thread.sleep(10000);
        }
        } catch (Exception e) {
            e.printStackTrace();
            sendRequest();
        }
    }

    public static void main(String[] args) throws  Exception{
        QueryTimeClient client = new QueryTimeClient();
        client.getOnlineServer();
        client.sendRequest();
    }
}
