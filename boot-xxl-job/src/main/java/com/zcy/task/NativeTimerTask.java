package com.zcy.task;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Timer缺陷：
 * Timer底层是使用单线程来处理多个Timer任务，这意味着所有任务实际上都是串行执行，前一个任务的延迟会影响到之后的任务的执行。
 * 由于单线程的缘故，一旦某个定时任务在运行时，产生未处理的异常，那么不仅当前这个线程会停止，所有的定时任务都会停止。
 * Timer任务执行是依赖于系统绝对时间，系统时间变化会导致执行计划的变更。
 * 由于上述缺陷，尽量不要使用Timer， idea中也会明确提示，使用ScheduledThreadPoolExecutor替代Timer 。
 * ScheduledExecutorService使用
 * ScheduledExecutorService对于Timer的缺陷进行了修补，首先ScheduledExecutorService内部实现是ScheduledThreadPool线程池，可以支持多个任务并发执行。
 * 对于某一个线程执行的任务出现异常，也会处理，不会影响其他线程任务的执行，另外ScheduledExecutorService是基于时间间隔的延迟，执行不会由于系统时间的改变发生变化。
 * 当然，ScheduledExecutorService也有自己的局限性：只能根据任务的延迟来进行调度，无法满足基于绝对时间和日历调度的需求。
 */
public class NativeTimerTask {
    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String name = Thread.currentThread().getName();
                    System.out.println("run timerTask，thread name=" + name);
                    TimeUnit.SECONDS.sleep(2);
                    if (System.currentTimeMillis() - time > 3000) {
                        throw new RuntimeException("突然就出错了");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                try {
                    String name = Thread.currentThread().getName();
                    System.out.println("run timerTask2，thread name=" + name);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

//        Timer timer = new Timer();
//        timer.schedule(timerTask, 0, 1000);
//        timer.schedule(timerTask2, 0, 1000);
        ScheduledExecutorService poolExecutor = new ScheduledThreadPoolExecutor(10);
        poolExecutor.schedule(timerTask, 0, TimeUnit.SECONDS);
        poolExecutor.schedule(timerTask2, 0, TimeUnit.SECONDS);
    }
}
