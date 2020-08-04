package com.zcy.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RPCTest {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                ServiceCenter serviceServer = new ServiceCenter(8888);
                serviceServer.register(ServiceProducer.class, ServiceProducerImpl.class);
                serviceServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        ServiceProducer serviceProducer = RPCClient.getRemoteProxyObj(ServiceProducer.class, new InetSocketAddress("localhost", 8888));
        System.out.println(serviceProducer.sendDate("hello rpc"));
    }
}
