package zk_api.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class QueryTimeService extends Thread {

    private int port;
    public QueryTimeService(int port){
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("服务器绑定端口号："+port);
            while(true){
                Socket sc = server.accept();

                InputStream in = sc.getInputStream();
                byte[] b = new byte[100];
                int num = in.read(b);
                OutputStream outputStream = sc.getOutputStream();
                if("请求当前时间?".equals(new String(b,0,num))){
                    outputStream.write(new Date().toString().getBytes());
                    outputStream.flush();
                }else {
                    outputStream.write("未知请求，请重新发送".getBytes());
                    outputStream.flush();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        QueryTimeService service = new QueryTimeService(port);


        //启动线程
        service.start();
    }
}
