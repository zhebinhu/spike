package com.huzb.spike.dao;

import com.huzb.spike.domain.OrderInfo;
import com.huzb.spike.domain.SpikeOrder;
import com.huzb.spike.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/26
 */
@Mapper
public interface OrderDao {

    @Select("select * from spike_order where user_id=#{userId} and goods_id=#{goodsId}")
    SpikeOrder getSpikeOrderByUserIdGoodsId(@Param("userId") Long userid, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into spike_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int insertSpikeOrder(SpikeOrder spikeOrder);

    @Select("select * from order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")long orderId);


    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from spike_order")
    public void deleteSpikeOrders();
}
