package com.huzb.spike.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/10/7
 */
@Component
public class MQReceiver {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message) {
//        logger.info("receive message:" + message);
//    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        logger.info("topic queue1 receive message:" + message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE1)
    public void receiveTopic2(byte[] message) {
        logger.info("header queue2 receive message:" + new String(message));
    }
}
