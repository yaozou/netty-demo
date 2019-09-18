package com.yao.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/9/18 0018
 * @Version V1.0
 **/
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-------connected successfully--------");
        ctx.channel().writeAndFlush("Hi,Server!I'm client");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(new Date()+" client receive msg:"+msg);
    }
}
