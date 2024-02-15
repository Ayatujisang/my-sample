package com.licheng.sample.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;

/*
 * @author LiCheng
 * @date  2024-02-15 22:41:30
 *
 * mybatilsPlus的自定义主键生成工具
 */
public class CustomIdGenerator implements IdentifierGenerator  {

    @Override
    public Number nextId(Object entity) {
        return nextId();
    }

    // 开始时间戳（2024-02-15）
    private static final long START_TIMESTAMP = 1708006674731L;

    // 每部分所占位数
    private static final long SEQUENCE_BIT = 12; // 序列号占用位数
    private static final long MACHINE_BIT = 5;   // 机器标识占用位数
    private static final long DATACENTER_BIT = 5;// 数据中心占用位数

    // 每部分的最大值
    private static final long MAX_SEQUENCE_NUM = ~(-1L << SEQUENCE_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    // 每部分向左的位移
    private static final long MACHINE_LEFT_SHIFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT_SHIFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT;

    private static long sequence = 0L;      // 序列号
    private static long lastTimestamp = -1L;// 上次生成ID的时间戳


    /**
     * 这里给出默认值 可自行传入参数影响ID生成
     */
    private static long DATA_CENTER_ID; // 数据中心标识

    @Value("${lc.config.idWork.datacenterId}")
    public void setDatacenterIdD(long datacenterId) {
        DATA_CENTER_ID = datacenterId;
    }

    private static long MACHINE_Id;    // 机器标识

    @Value("${lc.config.idWork.machineId}")
    public void setMachineId(long machineId) {
        MACHINE_Id = machineId;
    }


    /**
     * 构造方法 校验配置文件中配置的参数是否超过限制
     */
    private CustomIdGenerator() {
        if (DATA_CENTER_ID > MAX_DATACENTER_NUM || DATA_CENTER_ID < 0) {
            throw new IllegalArgumentException("Datacenter Id cannot be greater than " + MAX_DATACENTER_NUM + " or less than 0");
        }
        if (MACHINE_Id > MAX_MACHINE_NUM || MACHINE_Id < 0) {
            throw new IllegalArgumentException("Machine Id cannot be greater than " + MAX_MACHINE_NUM + " or less than 0");
        }
    }

    public synchronized static long nextId() {
        long currentTimestamp = getCurrentTimestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            if (sequence == 0) {
                // 当前毫秒的序列号已经用完，等待下一个毫秒
                currentTimestamp = waitNextMillis();
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT)
                | (DATA_CENTER_ID << DATACENTER_LEFT_SHIFT)
                | (MACHINE_Id << MACHINE_LEFT_SHIFT)
                | sequence;
    }

    /**
     * 生成下一个ID
     *
     * @return String类型的ID
     */
    public synchronized String nextIdString() {
        return String.valueOf(nextId());
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    private static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 若当前毫秒的序列号已经用完，则等待下一个毫秒
     *
     * @return 下一个毫秒的时间戳
     */
    private static long waitNextMillis() {
        long currentTimestamp = getCurrentTimestamp();
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = getCurrentTimestamp();
        }
        return currentTimestamp;
    }


}
