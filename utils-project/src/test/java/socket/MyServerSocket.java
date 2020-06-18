package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class MyServerSocket {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            serverSocket.setSoTimeout(10000);
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            while (true) {
                System.out.print("I say: ");
                String str = new Scanner(System.in).next();
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(str);
                if ("exit".equals(str)) {
                    break;
                }
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println("client: " + in.readUTF());
            }
            server.close();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("连接超时");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
