package mw;

import java.util.Random;

/**
 * Master-Worker模式
 * 该模式核心思想是系统由两类进行协助工作: Master进程, Worker进程.
 * Master负责接收与分配任务, Worker负责处理任务. 当各个Worker处理完成后,
 * 将结果返回给Master进行归纳与总结.
 * 假设一个场景, 需要计算100个任务, 并对结果求和, Master持有10个子进程
 */
public class MasterWorkerTest {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        // 创建10个工作子进程
        MasterDemo master = new MasterDemo(new WorkerDemo(), 10);
        // 创建100任务
        for (int i = 0; i < 100; i++) {
            TaskDemo task = new TaskDemo();
            task.setId(i);
            task.setName("任务" + i);
            task.setPrice(new Random().nextInt(100000000));
            master.submit(task);
        }

        master.execute();

        while (true) {
            if (master.isComplete()) {
                System.out.println("执行完成,计算结果为:" + master.getResult());
                break;
            }
        }

        long time = System.currentTimeMillis() - startTime;
        System.out.println("time=" + time);
    }
}
