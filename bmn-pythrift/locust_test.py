# -*- coding: utf-8 -*-
'''
 tcp 压力测试脚本

'''
import time
import random
# from socket import socket, AF_INET, SOCK_STREAM
import socket
from locust import Locust, TaskSet, events, task

from com.bmn.thrift.cs.ttypes import BmnRequest,RequestType, BmnResponse
from thrift import TSerialization

import struct

class TcpSocketClient(socket.socket):
    def __init__(self, af_inet, socket_type):
        super(TcpSocketClient, self).__init__(af_inet, socket_type)

    def connect(self, addr):
        start_time = time.time()
        try:
            super(TcpSocketClient, self).connect(addr)
        except Exception as e:
            total_time = int((time.time() - start_time) * 1000)
            events.request_failure.fire(request_type="tcpsocket", name="connect", response_time=total_time, exception=e)
        else:
            total_time = int((time.time() - start_time) * 1000)
            events.request_success.fire(request_type="tcpsocket", name="connect", response_time=total_time,
                                        response_length=0)

    def send(self, msg):
        start_time = time.time()
        try:
            super(TcpSocketClient, self).send(msg)
        except Exception as e:
            total_time = int((time.time() - start_time) * 1000)
            events.request_failure.fire(request_type="tcpsocket", name="send", response_time=total_time, exception=e)
        else:
            total_time = int((time.time() - start_time) * 1000)
            events.request_success.fire(request_type="tcpsocket", name="send", response_time=total_time,
                                        response_length=0)

    def recv(self, bufsize):
        recv_data = ''
        start_time = time.time()
        try:
            recv_data = super(TcpSocketClient, self).recv(bufsize)
        except Exception as e:
            total_time = int((time.time() - start_time) * 1000)
            events.request_failure.fire(request_type="tcpsocket", name="recv", response_time=total_time, exception=e)
        else:
            total_time = int((time.time() - start_time) * 1000)
            events.request_success.fire(request_type="tcpsocket", name="recv", response_time=total_time,
                                        response_length=0)
        return recv_data


class TcpTestUser(TaskSet):
    @task
    def send_data(self):
        request = BmnRequest()
        request.tid = -2
        request.type = RequestType.HEARTBEAT

        # 序列化消息
        reqData = TSerialization.serialize(request)
        # 获取消息长度
        length_data = len(reqData)
        # 将消息长度转成4字节的byte数组
        lenData = struct.pack('>i', length_data)

        # 先发送消息长度
        self.client.send(lenData);
        # 再发送消息体
        self.client.send(reqData);

        print("send heartbeat request success.")




class TcpSocketLocust(Locust):
    """
    This is the abstract Locust class which should be subclassed. It provides an TCP socket client
    that can be used to make TCP socket requests that will be tracked in Locust's statistics.
    """
    host = "localhost"
    port = 8888
    task_set = TcpTestUser

    def __init__(self):
        super(TcpSocketLocust, self).__init__()
        self.client = TcpSocketClient(socket.AF_INET, socket.SOCK_STREAM)
        ADDR = (self.host, self.port)
        self.client.connect(ADDR)





