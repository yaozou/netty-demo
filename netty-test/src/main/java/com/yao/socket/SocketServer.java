package com.yao.socket;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * IO实现
 */
public class SocketServer {
    public static void main(String[] args) throws Exception{
        int port = 8081;
        ServerSocket server = new ServerSocket(port);
        new Thread(new Runnable() {
            @Override
            public void run(){
                while (true){
                    try{
                        // 阻塞方法获得新链接
                        Socket socket = server.accept();

                        //一个连接一个线程，读取消息
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    byte[] data = new byte[1024];
                                    InputStream inputStream = socket.getInputStream();
                                    while (true){
                                        int len;
                                        // 按字节流读取消息
                                        while ((len = inputStream.read(data)) != -1){
                                            System.out.println(new String(data, 0, len));
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
