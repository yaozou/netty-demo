package com.yao.netty;

import com.yao.netty.handler.ServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/9/17 0017
 * @Version V1.0
 **/
public class ServerChildChannel extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        /*pipeline.addLast("ssl",createSSLHandler(channel));*/

        // 设置心跳机制
        pipeline.addFirst("idleStateHandler",new IdleStateHandler(10,0,0));

        // 加解密 (netty自带的MQTT协议解析类)
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // 应用消息处理
        pipeline.addLast(new ServerHandler());
    }

    private ChannelHandler createSSLHandler(SocketChannel channel) throws Exception {
        SslOperatorHandler sslOperatorHandler = new SslOperatorHandler(true,
                "passw0rdsrv","passw0rdsrv",
                "signedserverkeystore.jks" );
        return sslOperatorHandler.createSsl(channel);
    }
}
