package com.zcy.demo.file;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class FileTest2 {
    public static void main(String[] args) throws IOException {

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();
        in.connect(out);
        Runnable outThread = () -> produceData(out);
        Runnable inThread = () -> consumeData(in);

        new Thread(outThread).start();
        new Thread(inThread).start();
    }

    private static void produceData(PipedOutputStream out) {
        try {
            for (int i = 0; i < 30; i++) {
                out.write(i);
                out.flush();
                System.out.println("write:" + i);
                Thread.sleep(1000);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void consumeData(PipedInputStream in) {
        try {
            int num = -1;
            while ((num = in.read()) != -1) {
                System.out.println("read:" + num);
            }
            System.out.println("num=" +num);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
