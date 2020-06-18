package mw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MasterDemo {
    // 定义一个任务集合
    private ConcurrentLinkedQueue<TaskDemo> workQueue = new ConcurrentLinkedQueue<>();
    // 定义worker 集合
    private Map<String, Thread> workers = new HashMap<>();
    // 定义worker并行执行的结果集
    private ConcurrentHashMap<String, Object> resMap = new ConcurrentHashMap<>();

    /**
     * 接收并分配任务
     * @param workerDemo
     * @param count
     */
    public MasterDemo(WorkerDemo workerDemo, int count) {
        // 每个worker对象都需要持有queue的引用, 用于领任务与提交结果
        workerDemo.setWorkQueue(workQueue);
        workerDemo.setResMap(resMap);
        for (int i = 0; i < count; i++) {
            workers.put("子节点:" + i, new Thread(workerDemo));
        }
    }

    /**
     * 获取执行结果
     *
     * @return
     */
    public int getResult() {
        int result = 0;
        for (Map.Entry<String, Object> entry : resMap.entrySet()) {
            result += Integer.valueOf(entry.getValue().toString());
        }
        return result;
    }

    /**
     * 判断私有的子线程是否执行结束
     *
     * @return
     */
    public boolean isComplete() {
        for (Map.Entry<String, Thread> entry : workers.entrySet()) {
            if (entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 启动所有子任务
     */
    public void execute() {
        for (Map.Entry<String, Thread> entry : workers.entrySet()) {
            entry.getValue().start();
        }
    }

    /**
     * 提交任务
     *
     * @param task
     */
    public void submit(TaskDemo task) {
        workQueue.add(task);
    }
}
