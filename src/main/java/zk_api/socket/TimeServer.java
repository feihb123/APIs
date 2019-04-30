package zk_api.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer {
    public static void main(String[] args)throws Exception {
        //服务器端监听端口号
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("绑定端口号："+port);
        //服务器端创建对应客户端socket
        Socket socket = serverSocket.accept();
        //获取输入流，读取客户端请求信息
        InputStream in = socket.getInputStream();
        byte[] b = new byte[100];
        int num = in.read(b);
        System.out.println("客户端请求信息为："+new String(b,0,num));
        //获取输出流，用来向客户端发送信息
        OutputStream out = socket.getOutputStream();
        out.write(new Date().toString().getBytes());
        out.flush();
    }
}
