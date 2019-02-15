package com.bmn.server.codec;

import com.bmn.thrift.cs.BmnResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.thrift.TBase;
import org.apache.thrift.TSerializer;

import java.util.List;


public class Netty4ThriftEncoder extends MessageToMessageEncoder<BmnResponse> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BmnResponse msg, List<Object> list) throws Exception {

        if (msg instanceof TBase) {
            TBase<?, ?> obj = (TBase<?, ?>) msg;
            TSerializer serializer = new TSerializer();
            byte[] array = serializer.serialize(obj);
            list.add(Unpooled.wrappedBuffer(array));
        } else {
            list.add(msg);
        }
    }

}
