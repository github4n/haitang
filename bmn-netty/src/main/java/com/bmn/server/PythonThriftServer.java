package com.bmn.server;

import com.bmn.server.codec.Netty4ThriftDecoder;
import com.bmn.server.codec.Netty4ThriftEncoder;
import com.bmn.server.handler.HeartBeatHandler;
import com.bmn.thrift.cs.BmnRequest;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author: zyq
 * @date: 2019/2/15
 */
public class PythonThriftServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 100)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer() {

                @Override
                protected void initChannel(Channel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(10485760,
                        0,      //消息中长度字段偏移的字节数，即长度前面的字节
                        4,     //数据帧的最大长度, 即长度占4个字节
                        0,     //该字段加长度字段等于数据帧的长度
                        4));   //从数据帧中跳过的字节数, 跳过前面的长度，即只保留消息体
                    pipeline.addLast(new LengthFieldPrepender(4));

                    pipeline.addLast(new Netty4ThriftDecoder<>(BmnRequest.class));
                    pipeline.addLast(new Netty4ThriftEncoder());

                    pipeline.addLast(new HeartBeatHandler());
                }
            });

        ChannelFuture f = b.bind(8888).sync();
        f.channel().closeFuture().sync();
    }
}
