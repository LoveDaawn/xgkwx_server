package com.yuxi.xgkwx.socket.websocket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebSocketInitialization implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(WebSocketInitialization.class);

    @Resource
    private WebSocketChannelInitializer websocketChannelInitializer;

    @Value("${websocket.port}")
    private Integer port;

    public void init() throws InterruptedException {

        //bossGroup连接线程组，主要负责接受客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workerGroup工作线程组，主要负责网络IO读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //启动辅助类
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)       //bootstrap绑定两个线程组
            .channel(NioServerSocketChannel.class)              //设置通道为NioChannel
            .handler(new LoggingHandler(LogLevel.INFO))         //可以对入站\出站事件进行日志记录，从而方便我们进行问题排查。
            .childHandler(websocketChannelInitializer);         //设置自定义的通道初始化器，用于入站操作

            //启动服务器,本质是Java程序发起系统调用，然后内核底层起了一个处于监听状态的服务，生成一个文件描述符FD
            ChannelFuture channelFuture = server.bind(port).sync();
            //异步
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        try {
            init();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }
}
