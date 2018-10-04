package com.huzb.spike.vo;

import com.huzb.spike.domain.Goods;
import com.huzb.spike.domain.User;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/26
 */
public class GoodsDetailVo extends Goods {
    private int spikeStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private User user;

    public int getSpikeStatus() {
        return spikeStatus;
    }

    public void setSpikeStatus(int spikeStatus) {
        this.spikeStatus = spikeStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GoodsDetailVo{" +
                "spikeStatus=" + spikeStatus +
                ", remainSeconds=" + remainSeconds +
                ", goods=" + goods +
                ", user=" + user +
                '}';
    }
}
