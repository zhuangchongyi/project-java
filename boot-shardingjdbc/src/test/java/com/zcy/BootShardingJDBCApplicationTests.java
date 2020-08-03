package com.zcy;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BootShardingJDBCApplicationTests {

    @Test
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMM");
        Date date = new Date();
        System.out.println(sdf.format(date));
        sdf.applyPattern("MM");
        String format = sdf.format(date);
        System.out.println(format);

        Integer value = Integer.valueOf(format);
        System.out.println(3%4);
        System.out.println(4%4);
        System.out.println(5%4);
        System.out.println(6%4);
        System.out.println(7%4);

    }

}
