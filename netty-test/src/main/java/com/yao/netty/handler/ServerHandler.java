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
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String response = "registered completely";
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String response = "read completely";
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date()+" server receive msg:"+msg);

        String response = "ok";
        ctx.channel().writeAndFlush(response);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(new Date()+" server channelRead0 receive msg:"+msg);
    }
}
