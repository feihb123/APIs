package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 向HBase数据库指定表插入、获取数据的工具类
 * @author 化十
 *
 */
public class HBaseUtils {
	static Configuration conf = null;
	static Connection conn = null;
	//获取表名描述其  <-- 表名
	TableName tbName = null;
	//获取表连接（实现数据增删改查） <--表名描述器
	static Table table = null;
	static Admin admin = null;
	static{
		try {
			//获取HBase配置信息
			conf=HBaseConfiguration.create();
			//指定zookeeper所在节点
			conf.set("hbase.zookeeper.quorum", "hadoop");
			//获取连接对象
			conn=ConnectionFactory.createConnection(conf);
			//获取表名描述其  <-- 表名
			TableName tbName=TableName.valueOf("gsod");
			//获取表连接（实现数据增删改查） <--表名描述器
			table=conn.getTable(tbName);
			admin = conn.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建表
	 */
	public static void createTable()throws Exception{
		//HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("gsod"));
		tableDescriptor.addFamily(new HColumnDescriptor("info"));
		tableDescriptor.addFamily(new HColumnDescriptor("weather"));
		admin.createTable(tableDescriptor);
	}

	/**
	 * 从HBase数据库中指定表查询数据
	 */
	public static void show() throws Exception{		
		//全表扫描的方式获取数据  ==创建Scan对象
		Scan scan =new Scan();
		//查询数据，返回结果集
		ResultScanner rs=table.getScanner(scan);
		//遍历结果集，获取每条数据并输出
		//从HBase中获取得到的数据都是字节数组
		for(Result value:rs){
			//获取行键
			byte[] row=value.getRow();
			//在HBase中，提供了一个Bytes类
			//用来将从Hbase中得到的数据转变为对应Java类型
			String rowKey=Bytes.toString(row);
			System.out.println(rowKey);
				
			byte[] name=value.getValue("name".getBytes(), "".getBytes());
			String StrName=Bytes.toString(name);
			System.out.println(StrName);
			
			
			
		}
		//关闭资源
		rs.close();
		table.close();
		conn.close();
		
	}

	/**
	 * 向HBase数据库指定表插入数据
	 * @throws Exception 
	 */
	public static void put(Put put) throws Exception{
		//表连接对象执行上传数据
		table.put(put);

	}

	/**
	 * 插入数据
	 */
	public static void putData()throws Exception{
		byte[] row11 = Bytes.toBytes("11");
		byte[] row20 = Bytes.toBytes("20");
		byte[] row32 = Bytes.toBytes("32");
		byte[] name = Bytes.toBytes("name");
		byte[] profile = Bytes.toBytes("profile");
		byte[] score = Bytes.toBytes("score");
		byte[] alias = Bytes.toBytes("alias");
		byte[] age = Bytes.toBytes("age");
		byte[] gender = Bytes.toBytes("gender");
		byte[] xclass = Bytes.toBytes("class");
		byte[] math = Bytes.toBytes("math");
		byte[] lang = Bytes.toBytes("lang");
		byte[] eng = Bytes.toBytes("eng");

		byte[] John = Bytes.toBytes("John");
		byte[] age20 = Bytes.toBytes("20");
		byte[] class1 = Bytes.toBytes("class1");
		byte[] s100 = Bytes.toBytes("100");
		byte[] s95 = Bytes.toBytes("95");

		byte[] Marry = Bytes.toBytes("Marry");
		byte[] MY = Bytes.toBytes("MY");
		byte[] female = Bytes.toBytes("female");
		byte[] class2 = Bytes.toBytes("class2");
		byte[] s93 = Bytes.toBytes("93");
		byte[] s90 = Bytes.toBytes("90");

		byte[] Peter = Bytes.toBytes("Peter");
		byte[] class3 = Bytes.toBytes("class3");
		byte[] s87 = Bytes.toBytes("87");
		byte[] s85 = Bytes.toBytes("85");

		Put put = new Put(row11);
		put.addColumn(name,null,John);
		put.addColumn(profile,age,age20);
		put.addColumn(profile,xclass,class1);
		put.addColumn(score,math,s100);
		put.addColumn(score,lang,s95);

		Put put2 = new Put(row20);
		put2.addColumn(name,null,Marry);
		put2.addColumn(name,alias,MY);
		put2.addColumn(profile,gender,female);
		put2.addColumn(profile,xclass,class2);
		put2.addColumn(score,math,s93);
		put2.addColumn(score,eng,s90);

		Put put3 = new Put(row32);
		put3.addColumn(name,null,Peter);
		put3.addColumn(profile,xclass,class3);
		put3.addColumn(score,math,s87);
		put3.addColumn(score,lang,s85);

		table.put(put);
		table.put(put2);
		table.put(put3);


		table.close();
		conn.close();
	}
	/**
	 * 插入数据
	 */
	public static void putGsod(String[][] data)throws Exception{
		byte[] stn = Bytes.toBytes("stn");
		byte[] wban = Bytes.toBytes("wban");
		byte[] yearmoda = Bytes.toBytes("yearmoda");
		byte[] temp = Bytes.toBytes("temp");
		byte[] stp = Bytes.toBytes("stp");
		byte[] visib = Bytes.toBytes("visib");
		byte[] wdsp = Bytes.toBytes("wdsp");
		byte[] maxspd = Bytes.toBytes("mxspd");
		ArrayList<byte[]> list = new ArrayList();
		list.add(stn);
		list.add(wban);
		list.add(yearmoda);
		list.add(temp);
		list.add(stp);
		list.add(visib);
		list.add(wdsp);
		list.add(maxspd);

		for (int i = 0;i<data.length;i++){
			String row = data[i][0]+data[i][1]+data[i][2];
			Put put = new Put(row.getBytes());

			for(int j = 3;j < 8;j++){
				put.addColumn("weather".getBytes(),list.get(j),data[i][j].getBytes());
			}
			table.put(put);
		}

		table.close();
		conn.close();
	}

	/**
	 * 按行键查询信息
	 */
	public static void getOne(String rowkey) throws Exception{

		Get get = new Get(rowkey.getBytes());
		if(!get.isCheckExistenceOnly()){
			Result result = table.get(get);
			for (Cell cell : result.rawCells()){
				String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
				String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
				if(colName.equals("")){
					System.out.println(value);
				}
				if(colName.equals("alias")){
					System.out.println(value);
				}
				if(colName.equals("age")){
					System.out.println(value);
				}
				if (colName.equals("gender")){
					System.out.println(value);
				}
				if (colName.equals("class")){
					System.out.println(value);
				}
				if (colName.equals("math")){
					System.out.println(value);
				}
				if (colName.equals("lang")){
					System.out.println(value);
				}
				if (colName.equals("eng")){
					System.out.println(value);
				}
			}
		}


	}

	/**
	 *范围查询
	 */
	public static void selectRange(String from,String to) throws Exception{
		Scan scan =new Scan(from.getBytes(),to.getBytes());
		ResultScanner rs=table.getScanner(scan);

		for(Result value:rs){
			byte[] row=value.getRow();
			System.out.println(Bytes.toString(row));
			getOne(Bytes.toString(row));
			System.out.println("------------------");
		}
		//关闭资源
		rs.close();
		table.close();
		conn.close();

	}
}
