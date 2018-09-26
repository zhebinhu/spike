package com.huzb.spike.service;

import com.huzb.spike.dao.GoodsDao;
import com.huzb.spike.dao.OrderDao;
import com.huzb.spike.domain.OrderInfo;
import com.huzb.spike.domain.SpikeOrder;
import com.huzb.spike.domain.User;
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

    public SpikeOrder getSpikeOrderByUserIdGoodsId(Long userid, long goodsId) {
        return orderDao.getSpikeOrderByUserIdGoodsId(userid, goodsId);
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
        return orderInfo;
    }
}
