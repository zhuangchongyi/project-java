package com.zcy.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
/**
 * @Author zhuangchongyi
 * @Description 客户端
 * @Date 2020/8/3 18:08
 */
public class RPCClient<T> {
    public static <T>T getRemoteProxyObj(Class<?> serviceInterface, InetSocketAddress address) {
        // 将本地接口调用转换成jdk动态代理，在动态代理中实现接口远程调用
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, (proxy, method, args) -> {
            Socket socket = null;
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            try {
                // 创建socket客户端，根据地址连接远程服务提供者
                socket = new Socket();
                socket.connect(address);

                // 将远程服务的接口，方法名，参数列表等编码后发送给服务提供者
                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeUTF(serviceInterface.getName());
                out.writeUTF(method.getName());
                out.writeObject(method.getParameterTypes());
                out.writeObject(args);

                // 同步阻塞等待服务器返回响应
                in = new ObjectInputStream(socket.getInputStream());
                return in.readObject();
            } finally {
                if (null != socket) {
                    socket.close();
                }
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            }
        });
    }
}
