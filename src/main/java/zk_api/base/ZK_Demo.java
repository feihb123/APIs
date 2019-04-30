package zk_api.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.junit.Before;
import org.junit.Test;


/**
 * zk客户端操作的API 增删改查节点
 * @author 化十
 *
 */

public class ZK_Demo {
	
	
	static ZooKeeper zk = null;
	/*
	 * 初始化方法，自动执行
	 * 用来对zk对象初始化
	 * 参数：1指定要连接的zk服务器Ip/port(2181)
	 * 		2 超时时间
	 * 		3 要指定的监听
	 */

	@Before
	public void init() throws Exception{
		zk = new ZooKeeper("192.168.56.100:2181", 2000 , null);
	}
	/**
	 * 查询子节点
	 */
	@Test
	public void getChild() throws Exception{
		//只显示节点名称 不带路径
		 List<String> children = zk.getChildren("/test", false);
		 for(String c:children){
			 System.out.println(c);
		 }
		 zk.close();
	}
	
	@Test
	public void getData() throws Exception{
		//watch 是否添加监听   state 指定元数据
		byte[] data = zk.getData("/test", false, null);
		String string = new String(data);
		System.out.println(string);
		zk.close();
	}
	
	@Test
	public void create() throws Exception{
	
		zk.create("/test1", "data".getBytes(), 
				Ids.OPEN_ACL_UNSAFE, //开放权限
				CreateMode.PERSISTENT);//普通节点
		System.out.println("节点创建成功！");
		zk.close();
	}
	@Test
	public void update() throws Exception{

		zk.setData("/test","new".getBytes(),-1);//普通节点
		System.out.println("节点修改成功！");
		zk.close();
	}
	public List<String> getChild(String path)throws  Exception{
		List<String> list = new ArrayList<>();
		List<String> children = zk.getChildren(path,null);
		 for(String child:children){
			 child = path + "/" + child;
			 System.out.println(child);
			 list.add(child);
		 }
		 return list;
	}

	public void rmr(String path) throws Exception{
		//删除 /test节点
		List<String> child = getChild(path);
		if(child.size() != 0){
			for (String str:child){
				rmr(str);
			}
		}
		zk.delete(path,-1);
		System.out.println("delete:"+path);
	}

	public static void main(String[] args) throws  Exception{
		ZK_Demo zk_demo = new ZK_Demo();
		zk_demo.init();
		zk_demo.rmr("/test");
	}
	
	
}
