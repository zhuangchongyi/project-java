package com.zcy.netty.simple.client;

import com.zcy.netty.simple.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //创建一个线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        //创建客户端的启动助手，完成相关配置
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)  //设置线程组
                .channel(NioSocketChannel.class)  //设置客户端通道的实现类
                .handler(new ChannelInitializer<SocketChannel>() {  //创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new NettyClientHandler());
                    }
                });
        System.out.println("---------------------客户端就绪---------------------");
        //启动客户去连接端服务器，connect时异步的，sync是同步阻塞的
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 9999).sync();
        System.out.println("---------------------客户端启动---------------------");
        //关闭连接
        cf.channel().closeFuture().sync();
    }
}
