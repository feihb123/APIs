package zk_api.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class TimeClient  {
    public static void main(String[] args)throws Exception {
        int port = Integer.parseInt(args[0]);
        //生成客户端socket
        Random random = new Random();
        while (true) {
            Socket socket = new Socket("127.0.0.1",port);
            int index = random.nextInt(10);
            //获取输出流，用来发送请求
            OutputStream out = socket.getOutputStream();
            out.write(("请求当前时间?").getBytes());
            out.flush();
            //获取输入流，读取服务器响应
            InputStream in = socket.getInputStream();
            byte[] b = new byte[100];
            int num=in.read(b);
            //while ((num = in.read(b)) == 10) ;
            //{
                System.out.print("当前时间:" + new String(b, 0, num));
            //}
            Thread.sleep(5000);
        }


    }

}
