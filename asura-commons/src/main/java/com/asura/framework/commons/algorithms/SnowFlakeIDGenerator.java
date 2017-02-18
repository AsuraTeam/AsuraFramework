/**
 * @FileName: SnowFlakeIDGenerator.java
 * @Package: com.asura.framework.commons.algorithms
 * @author liusq23
 * @created 2016/12/1 下午9:00
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.algorithms;

import com.asura.framework.commons.util.Check;
import com.google.common.base.Joiner;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class SnowFlakeIDGenerator extends AbstractIDGenerator {

    /**
     * 定位时间点 2016-01-01 00:00:00.000
     */
    private long positionTime = 1451577600000L;
    /**
     * 机器id占4位  1111  最大 2^4-1 = 15 最多支持15台id生成机器集群
     */
    private int workerBits = 4;
    /**
     * 序列号占6位 111111 最大 2^6-1 = 63 支持每个机器每毫秒63个订单
     */
    private int sequenceBits = 6;
    /**
     * 序列号掩码
     * 000000000000 000000000000 000000000000 000000000000 0000000000 111111
     */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 机器id最大数量 15
     */
    private long maxWorkerId = -1L ^ (-1L << workerBits);

    //机器id向左移6位
    private long workerIdLeftShift = sequenceBits;

    //时间截向左移6+4=10位
    private long timestampLeftShift = workerBits + workerIdLeftShift;


    private long lastTimestamp = -1L;

    private long sequence = 0;


    public SnowFlakeIDGenerator(String identity, int workerId) {
        super(identity, workerId);
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException(String.format("workId cannot be greater than %d or less than 0", maxWorkerId));
        }
    }


    public synchronized String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        long id = ((timestamp - positionTime) << timestampLeftShift) | (getWorkerId() << workerIdLeftShift) | sequence;
        if (Check.isNullOrEmpty(getIdentity())) {
            return String.valueOf(id);
        }
        return Joiner.on("").join(getIdentity(), id);
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

}
