package com.zcy.mw;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerDemo implements Runnable {
    // 定义一个任务集合
    private ConcurrentLinkedQueue<TaskDemo> workQueue = new ConcurrentLinkedQueue<>();
    // 定义worker并行执行的结果集
    private ConcurrentHashMap<String, Object> resMap = new ConcurrentHashMap<>();

    public ConcurrentLinkedQueue<TaskDemo> getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(ConcurrentLinkedQueue<TaskDemo> workQueue) {
        this.workQueue = workQueue;
    }

    public ConcurrentHashMap<String, Object> getResMap() {
        return resMap;
    }

    public void setResMap(ConcurrentHashMap<String, Object> resMap) {
        this.resMap = resMap;
    }

    @Override
    public void run() {
        while (true) {
            TaskDemo task = this.workQueue.poll();
            // 判断所有任务是否执行完毕
            if (task == null) {
                break;
            }
            // 模拟处理任务,并返回结果
            int result = task.getPrice();
            this.resMap.put(task.getName(), result);
            System.out.println("任务执行完毕, 当前线程:" + Thread.currentThread().getName());
        }
    }
}
