package com.zcy.rpc;

public class ServiceProducerImpl implements ServiceProducer {
    @Override
    public String sendDate(String data) {
        return "I am service producer!!!, the data is " + data;
    }
}
