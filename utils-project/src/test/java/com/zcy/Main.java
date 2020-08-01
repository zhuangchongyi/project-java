package com.zcy;

import java.net.URLDecoder;

public class Main {
    public static void main(String[] args) {
        long i = 44282785815745616L;
        String string = Long.toString(i, 36);
        System.out.println(string);
        String str = "C40XPL0XAA8";
        System.out.println(str);
        long sl = Long.parseLong(str, 36);
        System.out.println(sl);


        System.out.println(URLDecoder.decode("%2B8"));
    }


}
