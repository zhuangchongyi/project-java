package com.zcy.utils;

/**
 * 雪花算法是由 Twitter 公布的分布式主键生成算法，它能够保证不同进程主键的不重复性，以及相同进程主键的有序性。
 * 算法描述：
 * 0 最高位是符号位，始终为0不可用，因为1为负数。
 * 41位的时间序列，精确到毫秒级，41位的长度可以使用69年。时间位还有一个很重要的作用是可以根据时间进行排序。
 * 10位的机器标识，10位的长度最多支持部署1024个节点
 * 12位的计数序列号，序列号即一系列的自增id，可以支持同一节点同一毫秒生成多个ID序号，12位的计数序列号支持每个节点每毫秒产生4096个ID序号
 * 1bit            41bit                          10bit     12bit
 * 0-10111001100111011111011101100001110100101-00 00000000-0000 00000000
 *
 * https://shardingsphere.apache.org/document/current/cn/features/sharding/concept/key-generator/
 */
public class SnowflakeUtil {
    /**
     * 起始时间毫秒值
     */
    private final static long startTimestamp = 1594448397348L;

    /**
     * 每一部分的占用位数
     */
    private final static long workerIdBits = 10L;
    private final static long sequenceBits = 12L;
    /**
     * 每一部分的最大值
     */
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);// 2^(10-1)
    private final static long maxSequence = -1L ^ (-1L << sequenceBits);//2^(12-1)
    /**
     * 每一部分的位移值
     */
    private final static long workerIdShift = sequenceBits;
    private final static long timestampShift = sequenceBits + workerIdBits;

    /**
     * 工作进程id
     */
    private long workerId;
    /**
     * 序列号
     */
    private long sequence = 0;
    /**
     * 上一次时间戳
     */
    private long lastTimestamp = 0;

    /**
     * 创建实体
     * @param workerId  工作进程id
     */
    public SnowflakeUtil(long workerId) {
        // 校验
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %s or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    /**
     * 获取Id
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = getNowTimestamp();
        // 检验时间戳
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %s milliseconds", lastTimestamp - timestamp));
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0L) {
                timestamp = getNextTimestamp();
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return (timestamp - startTimestamp) << timestampShift //时间戳部分
                | (workerId) << workerIdShift // 工作进程id部分
                | (sequence); //序列号
    }

    /**
     * 校验时间戳
     *
     * @return
     */
    private long getNextTimestamp() {
        long timestamp = getNowTimestamp();
        while (timestamp < lastTimestamp) {
            timestamp = getNowTimestamp();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    private long getNowTimestamp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowflakeUtil util = new SnowflakeUtil(1023);
        for (int i = 0; i < 100; i++) {
            long id = util.nextId();
            System.out.println(id);
        }
        String string = Long.toBinaryString(3940662823088136L);
        System.out.println(string);
        System.out.println(string.length());
    }
}
