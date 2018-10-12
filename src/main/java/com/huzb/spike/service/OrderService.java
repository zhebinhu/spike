package com.huzb.spike.service;

import com.huzb.spike.dao.GoodsDao;
import com.huzb.spike.dao.OrderDao;
import com.huzb.spike.domain.OrderInfo;
import com.huzb.spike.domain.SpikeOrder;
import com.huzb.spike.domain.User;
import com.huzb.spike.redis.OrderKey;
import com.huzb.spike.redis.RedisService;
import com.huzb.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/26
 */
@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public SpikeOrder getSpikeOrderByUserIdGoodsId(Long userid, long goodsId) {
        SpikeOrder spikeOrder = redisService.get(OrderKey.getSpikeOrderByUidGid, "" + userid + "_" + goodsId, SpikeOrder.class);
        if (spikeOrder != null) {
            return spikeOrder;
        }
        spikeOrder = orderDao.getSpikeOrderByUserIdGoodsId(userid, goodsId);
        if (spikeOrder != null) {
            redisService.set(OrderKey.getSpikeOrderByUidGid, "" + userid + "_" + goodsId, spikeOrder);
        }
        return spikeOrder;
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrid(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsPrice(goods.getSpikePrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        SpikeOrder spikeOrder = new SpikeOrder();
        spikeOrder.setGoodsId(goods.getId());
        spikeOrder.setUserId(user.getId());
        spikeOrder.setOrderId(orderId);
        orderDao.insertSpikeOrder(spikeOrder);
        redisService.set(OrderKey.getSpikeOrderByUidGid, "" + user.getId() + "_" + goods.getId(), spikeOrder);
        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteSpikeOrders();
    }
}
