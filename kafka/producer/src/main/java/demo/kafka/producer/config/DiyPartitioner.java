package demo.kafka.producer.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * DiyPartitioner
 *
 * @author Wenzhou
 * @since 2023/3/28 11:07
 */
public class DiyPartitioner implements Partitioner {
    /**
     * 分区策略核心方法
     *
     * @param s       String
     * @param o       Object
     * @param bytes   byte[]
     * @param o1      Object
     * @param bytes1  byte[]
     * @param cluster Cluster
     * @return int
     */
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
