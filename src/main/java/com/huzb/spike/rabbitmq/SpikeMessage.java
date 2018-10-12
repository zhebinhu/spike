package com.huzb.spike.rabbitmq;

import com.huzb.spike.domain.User;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/10/12
 */
public class SpikeMessage {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    private long goodsId;
}
