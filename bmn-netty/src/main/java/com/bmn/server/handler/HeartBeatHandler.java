package com.bmn.server.handler;

import com.bmn.thrift.ErrorCode;
import com.bmn.thrift.cs.BmnRequest;
import com.bmn.thrift.cs.BmnResponse;
import com.bmn.thrift.cs.RequestType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;



@Log4j2
public class HeartBeatHandler extends SimpleChannelInboundHandler<BmnRequest> {

    private static final long FORCE_RECONNECT_TIMEOUT = 5 * 60 * 1000L;
    private static final long FORCE_CLOSE_TIMEOUT = 10 * 60 * 1000L;

    public static final AttributeKey<Long> CONNECTION_ESTABLISH_TIMESTAMP =
        AttributeKey.valueOf("connection_establish_timestamp");

    public static BmnResponse createResponse(BmnRequest request, ErrorCode code) {
        BmnResponse response = new BmnResponse();
        long tId = (request == null ? -1 : request.getTid());
        response.setTid(tId);
        response.setErrorCode(code);
        return response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BmnRequest request) throws Exception {

        if (request.getType() == RequestType.HEARTBEAT) {
            BmnResponse response = createResponse(request, ErrorCode.OK);
            if (null == ctx.channel().attr(CONNECTION_ESTABLISH_TIMESTAMP).get()) {
                ctx.channel().attr(CONNECTION_ESTABLISH_TIMESTAMP).set(System.currentTimeMillis());
            } else {
                long timestamp = ctx.channel().attr(CONNECTION_ESTABLISH_TIMESTAMP).get();
                if (System.currentTimeMillis() - timestamp > FORCE_RECONNECT_TIMEOUT) {
                    response.setErrorCode(ErrorCode.FORCE_RECONNECT);
                }
                if (System.currentTimeMillis() - timestamp > FORCE_CLOSE_TIMEOUT) {
                    response.setErrorCode(ErrorCode.FORCE_CLOSE);
                }
            }

            ChannelFuture future = ctx.writeAndFlush(response);
            if (response.getErrorCode() == ErrorCode.FORCE_CLOSE) {
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        log.info("force close channel for exceed force close timeout {}.",
                            FORCE_CLOSE_TIMEOUT);
                        Channel channel = future.channel();
                        // TODO enable this function after client is ready.
                        channel.close();
                    }
                });
            } else {
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (future.isSuccess()) {
                            log.info("server received heart beat request success.");
                            return;
                        }
                        Channel channel = future.channel();
                        Throwable t = future.cause();

                        if (t instanceof ClosedChannelException) {
                            log.warn(
                                "Failed to write heart beat response, request {} channel {} closed",
                                request, channel);
                        } else {
                            log.warn("Failed to write heart beat to channel {}, request {}",
                                channel, request);
                        }
                        channel.close();
                    }
                });
            }
        } else {
            ctx.fireChannelRead(request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // io异常不下发。避免重复处理
        if (cause instanceof IOException) {
            log.error("channel {} error: {}", ctx.channel(), cause.getMessage());
            return;
        }
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel: {} closed.", ctx.channel());
        super.channelInactive(ctx);
    }
}
