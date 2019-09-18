package com.yao.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/9/17 0017
 * @Version V1.0
 **/
public class SslTwoWayServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        /*pipeline.addLast("ssl",createSSLHandler(channel));*/
    }

    private ChannelHandler createSSLHandler(SocketChannel channel) throws Exception {
        SslOperatorHandler sslOperatorHandler = new SslOperatorHandler(true,
                "passw0rdsrv","passw0rdsrv",
                "signedserverkeystore.jks" );
        return sslOperatorHandler.createSsl(channel);
    }
}
