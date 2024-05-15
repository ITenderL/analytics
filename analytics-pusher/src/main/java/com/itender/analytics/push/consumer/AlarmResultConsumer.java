package com.itender.analytics.push.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author yuanhewei
 * @date 2024/4/28 11:41
 * @description 告警结果消息消费者
 */
@Component
public class AlarmResultConsumer {

    /**
     * 消费监听
     *
     * @param record
     */
    @KafkaListener(topics = {"topic-test"})
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费Topic：" + record.topic() + " **分区: " + record.partition() + " **值内容: " + record.value());
    }

    /**
     * @param record
     * @Title 指定topic、partition、offset消费
     * @description  同时监听topic1和topic2，监听topic1的0号分区、
     * topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     */
    // @KafkaListener(id = "consumer1", groupId = "felix-group", topicPartitions = {
    //         @TopicPartition(topic = "topic1", partitions = {"0"}),
    //         @TopicPartition(topic = "topic2", partitions = "0",
    //                 partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
    // })
    public void onMessage2(ConsumerRecord<?, ?> record) {
        System.out.println("topic:" + record.topic() + "partition:" + record.partition() + "offset:" + record.offset() + "value:" + record.value());
    }
}
