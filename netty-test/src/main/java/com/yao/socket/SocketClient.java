package com.yao.socket;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class SocketClient {
    public static void main(String[] args) throws Exception{
        int port = 8081;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("127.0.0.1",port);
                    while (true){
                        try{
                            OutputStream outputStream = socket.getOutputStream();
                            outputStream.write((new Date() + ": hello world").getBytes());
                            outputStream.flush();
                            Thread.sleep(1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
