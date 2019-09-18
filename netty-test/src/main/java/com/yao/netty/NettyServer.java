package com.yao.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description: netty服务端
 * <ul>
 *     <li>1.配置服务的NIO线程组</li>
 *     <li>2.绑定端口，同步等待成功</li>
 *     <li>3.等待服务端监听端口关闭</li>
 *     <li>4.优雅退出，释放线程池资源</li>
 * </ul>
 * @Author yao.zou
 * @Date 2019/9/17 0017
 * @Version V1.0
 **/
public class NettyServer {
    public static void main(String[] args){
        int port = 1885;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 责任链 作用类似于Reactor模式中的handler类，主要用于处理网络I/O事件，例如记录日志、对消息进行编码等
                    .childHandler(new ServerChildChannel())
                    .option(ChannelOption.SO_BACKLOG,1024)

                    //套接口发送缓冲区大小 （当write写入的字节大于套接口发送缓冲区大小，进行MSS大小的TCP分段）
                   // .option(ChannelOption.SO_SNDBUF,1024)

                    .option(ChannelOption.SO_REUSEADDR,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);;


            ChannelFuture cf = b.bind(port).sync();
            //进行阻塞，等待链路关闭之后才退出
            cf.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 优雅退出，释放资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
