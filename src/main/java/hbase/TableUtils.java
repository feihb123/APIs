package hbase;
/**
 * 表的详细信息
 * @author 化十
 *
 */
public class TableUtils {
	//表名
	public static String tableName="data";
	//列族
	public static byte[][] family={
			"info".getBytes(),"score".getBytes()
	};
	//定义stu表中所有字段名称
	public static byte[] id="id".getBytes();
	public static byte[] name="name".getBytes();
	public static byte[] age="age".getBytes();
	public static byte[] gender="gender".getBytes();
	public static byte[] email="email".getBytes();
	public static byte[] Math="Math".getBytes();
	public static byte[] English="English".getBytes();
	public static byte[] China="China".getBytes();
	
	public static byte[][] cols={id,name,age,gender,email,Math,English,China};
	
	
}
