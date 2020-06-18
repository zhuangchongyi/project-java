package com.zcy.netty.simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @Author: zhuangchongyi
 * @Description: 服务器业务处理块
 * @Date: 2019/9/17 18:30
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger log = Logger.getLogger(NettyServerHandler.class.getName());

    /**
     * 读取数据事件
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(ctx.name() + "发来的消息：" + buf.toString(CharsetUtil.UTF_8));
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

    /**
     * 异常发生
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
