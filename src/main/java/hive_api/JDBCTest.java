package hive_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCTest {
    public static void main(String[] args) throws Exception{
        //加载驱动
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        //创建连接对象
        String url = "jdbc:hive2://192.168.56.100:10000/test";
        Connection connection = DriverManager.getConnection(url);
        //创建sql传输对象
        String sql = "select * from(select *,row_number() over (partition by class order by score desc) as od from classdata) t  where od <=1";
        PreparedStatement ps = connection.prepareStatement(sql);
        //执行获得结果集
        ResultSet rs = ps.executeQuery();
        //遍历结果集
        while(rs.next()){
            System.out.print(rs.getString("id")+" ");
            System.out.print(rs.getString("name")+" ");
            System.out.print(rs.getString("score")+" ");
            System.out.print(rs.getString("class")+" ");
            System.out.println();
        }
        //关闭资源
        rs.close();
        ps.close();
        connection.close();
    }
}
