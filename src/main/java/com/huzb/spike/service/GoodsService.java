package com.huzb.spike.service;

import com.huzb.spike.dao.GoodsDao;
import com.huzb.spike.domain.Goods;
import com.huzb.spike.domain.SpikeGoods;
import com.huzb.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/26
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goods) {
        SpikeGoods g = new SpikeGoods();
        g.setGoodsId(goods.getId());
        goodsDao.reduceStock(g);
    }
}
