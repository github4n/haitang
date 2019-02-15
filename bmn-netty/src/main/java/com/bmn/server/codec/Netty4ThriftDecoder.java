package com.bmn.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;

import java.util.List;

public class Netty4ThriftDecoder<T extends TBase<?, ?>>  extends MessageToMessageDecoder<ByteBuf> {

    private Class<T> clazz;

    public Netty4ThriftDecoder(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        final byte[] array;
        final int offset;
        final int length = byteBuf.readableBytes();

        if (byteBuf.hasArray()) {
            array = byteBuf.array();
            offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            array = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
            offset = 0;
        }

        T obj = clazz.newInstance();
        TDeserializer deserializer = new TDeserializer();
        deserializer.deserialize(obj, array, offset, length);
        list.add(obj);
    }
}
