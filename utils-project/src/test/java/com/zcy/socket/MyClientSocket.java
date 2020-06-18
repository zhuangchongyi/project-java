package com.zcy.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 6666);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            while (true) {
                DataInputStream in = new DataInputStream(client.getInputStream());
                String read = in.readUTF();
                System.out.println("Server: " + read);
                OutputStream outputStream = client.getOutputStream();
                if ("exit".equals(read)) {
                    break;
                }
                System.out.print("I say: ");
                String str = new Scanner(System.in).next();
                if ("exit".equals(str)) {
                    break;
                }
                DataOutputStream out = new DataOutputStream(outputStream);
                out.writeUTF(str);
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
