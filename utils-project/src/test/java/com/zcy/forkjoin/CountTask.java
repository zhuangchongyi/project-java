package com.zcy.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin线程池
 * 该线程池是jdk7之后引入的一个并行执行任务的框架, 其核心思想也是将任务分割为子任务,
 * 有可能子任务还是很大, 还需要进一步拆解, 最终得到足够小的任务.
 * 将分割出来的子任务放入双端队列中, 然后几个启动线程从双端队列中获取任务执行.
 * 子任务执行的结果放到一个队列里, 另起线程从队列中获取数据, 合并结果
 *
 * ForkJoin线程池使用一个无锁的栈来管理空闲线程, 如果一个工作线程暂时取不到可用的任务, 则可能被挂起.
 * 挂起的线程将被压入由线程池维护的栈中, 待将来有任务可用时, 再从栈中唤醒这些线程
 */
public class CountTask extends RecursiveTask<Long> {
    // 任务分解的阈值
    private static final long THRESHOLD = 10000;
    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean conCompute = (end - start) > THRESHOLD;
        if (conCompute) {
            for (long i = start; i < end; i++) {
                sum += i;
            }
        } else {
            // 拆分100个小任务
            long step = (start + end) / 100;
            List<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = step + pos;
                if (lastOne > end) {
                    lastOne = end;
                }
                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;
                // 将子进程推向线程池
                subTasks.add(subTask);
                subTask.fork();
            }
            for (CountTask subTask : subTasks) {
                // 对结果进行join
                sum += subTask.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        // 累计求和0 ->200000000L
        CountTask countTask = new CountTask(0, 2000000000L);
        ForkJoinTask<Long> result = pool.submit(countTask);
        System.out.println("sum result: " + result.get());
    }
}
