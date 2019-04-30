package hbase.testHbase;


import hbase.HBaseUtils;
import hbase.TableUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class TestMain {
	public static void main(String[] args) throws Exception {
		String [][] data=TestData.data;
		
		//遍历data数组
		for(int i=0;i<data.length;i++){
			//data[i]表示获取得到的每一条数据->将每个字段封装到数组中
			String[] value=data[i];
			//一个value表示一个put对象
			byte[] row =Bytes.toBytes(value[0]+value[1]);
			Put put=new Put(row);
			//向put对象中添加列及对应的值
			
			for(int j=0;j<8;j++){
				if(j<=4){
				put.addColumn(TableUtils.family[0],
						TableUtils.cols[j], Bytes.toBytes(value[j]));
				}else{
					put.addColumn(TableUtils.family[1], 
							TableUtils.cols[j], Bytes.toBytes(value[j]));
				}
			}
			HBaseUtils.put(put);
		}
		
		
	}
}
