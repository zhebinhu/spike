package com.huzb.spike.dao;

import com.huzb.spike.domain.SpikeGoods;
import com.huzb.spike.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/26
 */
@Mapper
public interface GoodsDao {
    @Select("select g.*,sg.spike_price,sg.stock_count,sg.start_date,sg.end_date from spike_goods sg left join goods g on sg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,sg.spike_price,sg.stock_count,sg.start_date,sg.end_date from spike_goods sg left join goods g on sg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(long goodsId);

    @Update("update spike_goods set stock_count = stock_count - 1 where goods_id=#{goodsId} and stock_count>0")
    int reduceStock(SpikeGoods g);


    @Update("update spike_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(SpikeGoods g);
}
