package com.yao.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Description: netty客户端
 * @Author yao.zou
 * @Date 2019/9/17 0017
 * @Version V1.0
 **/
public class NettyClient {
    public static void main(String[] args){
        EventLoopGroup group = new NioEventLoopGroup();
        String host = "localhost";
        int port = 1885;
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ClientChildChannel());

            // Start the connection attempt.
            ChannelFuture ch = b.connect(host, port).sync();

            ch.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
