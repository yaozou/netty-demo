package com.yao.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO实现
 */
public class SelectorServer {
    public static void main(String[] args) throws Exception{
        final int port = 8081;
        // 1、轮询是否有新连接
        final Selector server = Selector.open();
        // 2、轮询连接是否有数据可读
        final Selector client = Selector.open();

        new Thread(new Runnable() {
            @Override
            public void run() {
               try{
                   ServerSocketChannel channel = ServerSocketChannel.open();
                   channel.socket().bind(new InetSocketAddress(port));
                   channel.configureBlocking(false);
                   channel.register(server, SelectionKey.OP_ACCEPT);

                   while (true){
                       // 监测是否有新的连接，阻塞的时间为1ms
                       if (server.select(1)>0){
                           Set<SelectionKey> set = server.selectedKeys();
                           Iterator<SelectionKey> keys = set.iterator();
                            while (keys.hasNext()){
                                SelectionKey key = keys.next();
                                if (key.isAcceptable()){
                                    try {
                                        // 每一个新连接注册到client
                                        SocketChannel clientChannel = ((ServerSocketChannel)key.channel()).accept();
                                        clientChannel.configureBlocking(false);
                                        clientChannel.register(client,SelectionKey.OP_READ);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                       }
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (true){
                        if (client.select(1)>0){
                            Set<SelectionKey> set = server.selectedKeys();
                            Iterator<SelectionKey> keys = set.iterator();
                            while (keys.hasNext()){
                                SelectionKey key = keys.next();
                                if (key.isReadable()){
                                    try {
                                        SocketChannel clientChannel = (SocketChannel) key.channel();
                                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                        // 读取数据以块为单位批量读取
                                        clientChannel.read(byteBuffer);
                                        byteBuffer.flip();
                                        System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer)
                                                .toString());
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }finally {
                                        keys.remove();
                                        key.interestOps(SelectionKey.OP_READ);
                                    }
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
