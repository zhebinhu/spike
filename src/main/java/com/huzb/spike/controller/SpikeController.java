package com.huzb.spike.controller;

import com.huzb.spike.domain.OrderInfo;
import com.huzb.spike.domain.SpikeOrder;
import com.huzb.spike.domain.User;
import com.huzb.spike.redis.RedisService;
import com.huzb.spike.result.CodeMsg;
import com.huzb.spike.service.GoodsService;
import com.huzb.spike.service.OrderService;
import com.huzb.spike.service.SpikeService;
import com.huzb.spike.service.UserService;
import com.huzb.spike.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
@Controller
@RequestMapping("/spike")
public class SpikeController {

    private static Logger log = LoggerFactory.getLogger(SpikeController.class);
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SpikeService spikeService;

    /**
     * 500
     * 5000*10
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/do_spike")
    public String spike(Model model, User user,
                        @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.SPIKE_OVER.getMsg());
            return "spike_fail";
        }
        //判断是否已经秒杀到了
        SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order!=null){
            model.addAttribute("errmsg",CodeMsg.REPEATE_SPIKE.getMsg());
            return "spike_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = spikeService.spike(user,goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);

        return "order_detail";
    }

}
