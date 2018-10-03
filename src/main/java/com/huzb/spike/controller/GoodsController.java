package com.huzb.spike.controller;

import com.huzb.spike.domain.User;
import com.huzb.spike.redis.GoodsKey;
import com.huzb.spike.redis.RedisService;
import com.huzb.spike.service.GoodsService;
import com.huzb.spike.service.UserService;
import com.huzb.spike.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * QPS:514
     * 5000*10
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //尝试从缓存取
        String html = redisService.get(GoodsKey.goodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.goodsList, "", html);
        }

        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user,
                           @PathVariable("goodsId") long goodsId) {
        //尝试从缓存取
        String html = redisService.get(GoodsKey.goodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("user", user);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int spikeStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {
            //秒杀还没开始
            spikeStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {
            //秒杀已结束
            spikeStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            spikeStatus = 1;
            remainSeconds = 0;

        }
        model.addAttribute("spikeStatus", spikeStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.goodsDetail, "" + goodsId, html);
        }

        return html;
    }

}
