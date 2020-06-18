package com.zcy.netty.simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Scanner;
import java.util.logging.Logger;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static Logger log = Logger.getLogger(NettyClientHandler.class.getName());


    /**
     * 通道就绪时间
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.name() + "已连接...");
        ctx.writeAndFlush(Unpooled.copiedBuffer("在吗?", CharsetUtil.UTF_8));
    }

    /**
     * 读取发送数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(ctx.name() + "发来消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.print("输入对话：");
        String text = new Scanner(System.in).nextLine();
        ctx.writeAndFlush(Unpooled.copiedBuffer(text, CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("已发送,等待回复");
        ctx.writeAndFlush(Unpooled.copiedBuffer("", CharsetUtil.UTF_8));
    }
}
