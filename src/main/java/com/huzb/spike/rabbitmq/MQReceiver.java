package com.huzb.spike.rabbitmq;

import com.huzb.spike.domain.SpikeOrder;
import com.huzb.spike.domain.User;
import com.huzb.spike.redis.RedisService;
import com.huzb.spike.service.GoodsService;
import com.huzb.spike.service.OrderService;
import com.huzb.spike.service.SpikeService;
import com.huzb.spike.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SpikeService spikeService;

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message) {
//        logger.info("receive message:" + message);
//    }

    @RabbitListener(queues = MQConfig.SPIKE_QUEUE)
    public void receive(String message) {
        SpikeMessage mm = RedisService.stringToBean(message, SpikeMessage.class);
        User user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        spikeService.spike(user, goods);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        logger.info("topic queue1 receive message:" + message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE1)
    public void receiveTopic2(byte[] message) {
        logger.info("header queue2 receive message:" + new String(message));
    }
}
