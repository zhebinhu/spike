package com.huzb.spike.service;

import com.huzb.spike.domain.Goods;
import com.huzb.spike.domain.OrderInfo;
import com.huzb.spike.domain.User;
import com.huzb.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/26
 */
@Service
public class SpikeService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo spike(User user, GoodsVo goods) {
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);
    }
}
