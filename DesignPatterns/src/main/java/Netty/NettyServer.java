package Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 本例参考文章：
 * 超详细Netty入门，看这篇就够了！ - 阿里云云栖号的文章 - 知乎
 * https://zhuanlan.zhihu.com/p/181239748
 */
public class NettyServer {
    private void bing(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /*启动器类工厂类。ServerBootstrap用于服务端，接受客户端的连接并为接受连接的用户创建Channel通道，这和原生Nio一样，有一个主Channel专门负责监听OP_accept事件*/
            ServerBootstrap serverBootstrap= new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)  // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 设置服务端通道的实现类型
                    .option(ChannelOption.SO_BACKLOG, 128)  //设置服务端用于接收进来的连接，也就是bossGroup线程组中的线程
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    /*下面是重点，设置流水线！*/
                    .childHandler(
                            /**
                             * 这里是重点，每一个注册到selector中的channel都会有一个pipeline，
                             * 这就是为什么ch.pipeline返回的是ChannelPipeline对象了，这个pipeline有点责任链模式的感觉，
                             *  pipeline中加入具体的多个ChannelHandler，即具体的处理类
                             */
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline()
                                            .addLast(new MyServerHandler())
                                            .addLast(new ChannelOutboundHandlerAdapter());
                                }
                            }
                    );
            System.out.println("java技术爱好者的服务端已经准备就绪...");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();


        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
