# coding=UTF-8
from com.bmn.thrift.cs.ttypes import BmnRequest,RequestType, BmnResponse
from thrift import TSerialization
import socket
import struct

from com.bmn.thrift.ttypes import ErrorCode


def handleResponse(buffer):
    data = "".join(buffer)

    # 先解压4个字节获取消息长度
    size=struct.unpack('>i', data[:4])[0]

    if(size <= 0):
        print("response size error %d" % size)
        return False

    # 计算消息体最后索引
    endLen = size + 4;
    print(endLen)

    resp = BmnResponse();
    try:
        # 反序列化，从第4个字节开始
        resp = TSerialization.deserialize(resp, data[4:endLen])
    except Exception:
        print("deserialize response error")
        return False

    if resp.errorCode == ErrorCode.OK:
        print("receive heartbeat response success: %s" % resp)
        return True
    else:
        print("receive heartbeat response fail")
        return False


request = BmnRequest()
request.tid = -2
request.type = RequestType.HEARTBEAT

try:
    transport = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    transport.connect(('localhost', 8888))

    # 序列化消息
    reqData = TSerialization.serialize(request)
    # 获取消息长度
    length_data = len(reqData)
    # 将消息长度转成4字节的byte数组
    lenData = struct.pack('>i', length_data)

    # 先发送消息长度
    transport.send(lenData);
    # 再发送消息体
    transport.send(reqData);

    print("send heartbeat request success.")

    BUF_SIZE = 1024

    # list链表
    buffer = []
    while True:
        if not transport:
            print("read data and server close!")
            break;
        # 阻塞式接收，返回的是字符串
        data = transport.recv(BUF_SIZE)
        if not data:
            print("receive data finished!")
            break;

        buffer.append(data)

        if(handleResponse(buffer)):
            break;

    transport.close()

    print("heartbeat success.")

except Exception:
    print("error")




