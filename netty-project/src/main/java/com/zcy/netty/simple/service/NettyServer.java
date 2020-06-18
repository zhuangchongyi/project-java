package com.zcy.netty.simple.service;

import com.zcy.netty.simple.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: zhuangchongyi
 * @Description: nrtty服务类
 * @Date: 2019/9/17 18:02
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建线程池，建立连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //建立线程池，处理io
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        //创建服务器启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //配置启动助手
        serverBootstrap.group(bossGroup, workGroup)  //设置连个线程池
                .channel(NioServerSocketChannel.class)  //使用服务器端的通道实现
                .option(ChannelOption.SO_BACKLOG,128)   //设置线程队列中等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)   //保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { //创建通道初始化的对象
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("---------------服务启动准备就绪---------------");
        ChannelFuture cf = serverBootstrap.bind(9999).sync();//绑定端口，异步
        System.out.println("---------------服务启动,等待连接---------------");
        //关闭管道，关闭通道组
        cf.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();

    }
}
